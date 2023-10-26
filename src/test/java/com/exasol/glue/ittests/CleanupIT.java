package com.exasol.glue.ittests;

import static com.exasol.matcher.ResultSetStructureMatcher.table;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.*;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkException;
import org.apache.spark.api.java.function.*;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.exasol.dbbuilder.dialects.Table;
import com.exasol.glue.connection.ExasolConnectionException;

@Tag("integration")
@Testcontainers
class CleanupIT extends BaseIntegrationTestSetup { // For this test suite, we start Spark session for each test unit to
                                                   // force the job end call.
    private final int MAX_ALLOWED_SPARK_TASK_FAILURES = 3;

    private static Table table;

    private Dataset<Row> loadTable() {
        return loadTable(table.getFullyQualifiedName());
    }

    private final SparkConf conf = new SparkConf() //
            .setMaster("local[*," + this.MAX_ALLOWED_SPARK_TASK_FAILURES + "]") //
            .setAppName("CleanupTests") //
            .set("spark.ui.enabled", "false") //
            .set("spark.driver.host", "localhost");

    @BeforeAll
    static void setup() {
        table = schema.createTableBuilder("table_cleanup") //
                .column("c1", "CHAR") //
                .build() //
                .bulkInsert(Stream.of("1", "2", "3").map(n -> List.of(n)));
        spark.stop();
    }

    @BeforeEach
    void beforeEach() {
        spark = SparkSessionProvider.getSparkSession(this.conf);
    }

    @AfterEach
    void afterEach() {
        TaskFailureStateCounter.clear();
    }

    private void assertThatBucketIsEmpty() {
        spark.stop();
        assertThat(isBucketEmpty(DEFAULT_BUCKET_NAME), equalTo(true));
    }

    @Test
    void testSourceSuccessJobEndCleanup() {
        final Dataset<String> df = loadTable() //
                .map((MapFunction<Row, String>) row -> row.getString(0), Encoders.STRING());
        assertThat(df.collectAsList(), contains("1", "2", "3"));
        assertThatBucketIsEmpty();
    }

    @Test
    void testSourceSingleMapTaskFailureJobEndCleanup() {
        final Dataset<Integer> df = loadTable() //
                .map((MapFunction<Row, Integer>) row -> {
                    final int value = Integer.valueOf(row.getString(0));
                    synchronized (TaskFailureStateCounter.class) {
                        if ((value == 1) && (TaskFailureStateCounter.totalTaskFailures == 0)) {
                            TaskFailureStateCounter.totalTaskFailures += 1;
                            throw new RuntimeException("Intentionally fails current task.");
                        }
                    }
                    return value;
                }, Encoders.INT());

        assertThat(df.collectAsList(), contains(1, 2, 3));
        assertThatBucketIsEmpty();
    }

    @Test
    void testSourceMultiStageMapWithCacheFailureJobEndCleanup() {
        final Dataset<Row> cachedDF = loadTable() //
                .filter((FilterFunction<Row>) row -> {
                    final int value = Integer.valueOf(row.getString(0));
                    synchronized (TaskFailureStateCounter.class) {
                        if (((value == 1) || (value == 3)) && (TaskFailureStateCounter.totalTaskFailures < 2)) {
                            TaskFailureStateCounter.totalTaskFailures += 1;
                            throw new RuntimeException("Intentionally fails currect task for value '" + value + "'.");
                        }
                    }
                    return value == 3;
                }) //
                .cache();

        long size = cachedDF.count();
        assertThat(size, equalTo(1L));
        // Should stay the same size = cachedDF.count();
        size = cachedDF.count();
        assertThat(size, equalTo(1L));
        assertThatBucketIsEmpty();
    }

    @Test
    void testSourceMapReduceFailureJobEndCleanup() {
        final Dataset<String> df = loadTable() //
                .map((MapFunction<Row, Long>) row -> {
                    final int value = Integer.valueOf(row.getString(0));
                    return value * 1L;
                }, Encoders.LONG()) //
                .groupByKey((MapFunction<Long, String>) v -> (v % 2) == 0 ? "even" : "odd", Encoders.STRING()) //
                .mapGroups((MapGroupsFunction<String, Long, String>) (key, values) -> {
                    synchronized (TaskFailureStateCounter.class) {
                        if (key.equals("even") && (TaskFailureStateCounter.totalTaskFailures == 0)) {
                            TaskFailureStateCounter.totalTaskFailures += 1;
                            throw new RuntimeException("Intentionally fails reduce task with 'even' key.");
                        }
                    }
                    final List<Long> longs = StreamSupport
                            .stream(Spliterators.spliteratorUnknownSize(values, Spliterator.ORDERED), false)
                            .collect(Collectors.toList());
                    return key + ": " + longs.toString();
                }, Encoders.STRING());

        assertThat(df.collectAsList(), containsInAnyOrder("even: [2]", "odd: [1, 3]"));
        assertThatBucketIsEmpty();
    }

    @Test
    void testSourceJobAlwaysFailsJobEndCleanup() {
        final Dataset<Integer> df = loadTable() //
                .map((MapFunction<Row, Integer>) row -> {
                    throw new RuntimeException("Intentionally fails all tasks.");
                }, Encoders.INT());

        final SparkException exception = assertThrows(SparkException.class, () -> df.collectAsList());
        assertThat(exception.getMessage(), containsString("Intentionally fails all tasks."));
        assertThatBucketIsEmpty();
    }

    @Test
    void testSinkSuccessJobEndCleanup() throws SQLException {
        final Table table = schema.createTableBuilder("table_cleanup_save") //
                .column("c_str", "VARCHAR(3)") //
                .column("c_int", "DECIMAL(9,0)") //
                .column("c_double", "DOUBLE") //
                .column("c_bool", "BOOLEAN") //
                .build();
        getSampleDataset() //
                .write() //
                .mode("append") //
                .format("exasol") //
                .options(getDefaultOptions()) //
                .option("table", table.getFullyQualifiedName()) //
                .save();
        final String query = "SELECT * FROM " + table.getFullyQualifiedName() + " ORDER BY \"c_int\" ASC";
        try (final ResultSet result = connection.createStatement().executeQuery(query)) {
            assertThat(result, table().row("str", 10, 3.14, true).row("abc", 20, 2.72, false).matches());
        }
        assertThatBucketIsEmpty();
    }

    @Test
    void testSinkJobAlwaysFailsJobEndCleanup() {
        final DataFrameWriter<Row> df = getSampleDataset() //
                .write() //
                .mode("append") //
                .format("exasol") //
                .options(getDefaultOptions()) //
                .option("table", "non_existent_table");
        final Exception exception = assertThrows(ExasolConnectionException.class, () -> df.save());
        assertThat(exception.getMessage(), startsWith("E-EGC-24: Failure running the import"));
        assertThatBucketIsEmpty();
    }

    private static class TaskFailureStateCounter {
        private static int totalTaskFailures = 0;

        public static synchronized void clear() {
            totalTaskFailures = 0;
        }
    }

    private Dataset<Row> getSampleDataset() {
        final StructType schema = new StructType() //
                .add("c_str", DataTypes.StringType, false) //
                .add("c_int", DataTypes.IntegerType, false) //
                .add("c_double", DataTypes.DoubleType, false) //
                .add("c_bool", DataTypes.BooleanType, false);
        return spark.createDataFrame(List.of( //
                RowFactory.create("str", 10, 3.14, true), //
                RowFactory.create("abc", 20, 2.72, false) //
        ), schema);
    }

}
