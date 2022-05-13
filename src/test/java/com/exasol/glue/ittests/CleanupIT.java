package com.exasol.glue.ittests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.exasol.dbbuilder.dialects.Table;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkException;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.api.java.function.MapGroupsFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Testcontainers;

import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

@Tag("integration")
@Testcontainers
class CleanupIT extends BaseIntegrationTestSetup { // For this test suite, we start Spark session for each test unit to
                                                   // force the job end call.
    private int MAX_ALLOWED_SPARK_TASK_FAILURES = 3;

    private static Table table;

    private Dataset<Row> loadTable() {
        return loadTable(table.getFullyQualifiedName());
    }

    private final SparkConf conf = new SparkConf() //
            .setMaster("local[*," + MAX_ALLOWED_SPARK_TASK_FAILURES + "]") //
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
        spark = SparkSessionProvider.getSparkSession(conf);
    }

    @AfterEach
    void afterEach() {
        spark.stop();
        TaskFailureStateCounter.clear();
        assertThat(validateEmptyBucket(), equalTo(true));
    }

    private boolean validateEmptyBucket() {
        final List<S3Object> objects = s3Client
                .listObjects(ListObjectsRequest.builder().bucket(DEFAULT_BUCKET_NAME).build()).contents();
        return objects.isEmpty();
    }

    @Test
    void testDataSourceSuccessJobEndCleanup() {
        final Dataset<String> df = loadTable() //
                .map((MapFunction<Row, String>) row -> row.getString(0), Encoders.STRING());
        assertThat(df.collectAsList(), contains("1", "2", "3"));
    }

    @Test
    void testSingleMapTaskFailureJobEndCleanup() {
        final Dataset<Integer> df = loadTable() //
                .map((MapFunction<Row, Integer>) row -> {
                    final int value = Integer.valueOf(row.getString(0));
                    synchronized (TaskFailureStateCounter.class) {
                        if (value == 1 && TaskFailureStateCounter.totalTaskFailures == 0) {
                            TaskFailureStateCounter.totalTaskFailures += 1;
                            throw new RuntimeException("Intentionally fails current task.");
                        }
                    }
                    return value;
                }, Encoders.INT());

        assertThat(df.collectAsList(), contains(1, 2, 3));
    }

    @Test
    void testMultiStageMapWithCacheFailureJobEndCleanup() {
        final Dataset<Row> cachedDF = loadTable() //
                .filter((FilterFunction<Row>) row -> {
                    final int value = Integer.valueOf(row.getString(0));
                    synchronized (TaskFailureStateCounter.class) {
                        if ((value == 1 || value == 3) && TaskFailureStateCounter.totalTaskFailures < 2) {
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
    }

    @Test
    void testMapReduceFailureJobEndCleanup() {
        final Dataset<String> df = loadTable() //
                .map((MapFunction<Row, Long>) row -> {
                    final int value = Integer.valueOf(row.getString(0));
                    return value * 1L;
                }, Encoders.LONG()) //
                .groupByKey((MapFunction<Long, String>) v -> (v % 2) == 0 ? "even" : "odd", Encoders.STRING()) //
                .mapGroups((MapGroupsFunction<String, Long, String>) (key, values) -> {
                    synchronized (TaskFailureStateCounter.class) {
                        if (key.equals("even") && TaskFailureStateCounter.totalTaskFailures == 0) {
                            TaskFailureStateCounter.totalTaskFailures += 1;
                            throw new RuntimeException("Intentionally fails reduce task with 'even' key.");
                        }
                    }
                    List<Long> longs = StreamSupport
                            .stream(Spliterators.spliteratorUnknownSize(values, Spliterator.ORDERED), false)
                            .collect(Collectors.toList());
                    return key + ": " + longs.toString();
                }, Encoders.STRING());

        assertThat(df.collectAsList(), containsInAnyOrder("even: [2]", "odd: [1, 3]"));
    }

    @Test
    void testJobAlwaysFailsJobEndCleanup() {
        final Dataset<Integer> df = loadTable() //
                .map((MapFunction<Row, Integer>) row -> {
                    throw new RuntimeException("Intentionally fails all tasks.");
                }, Encoders.INT());

        final SparkException exception = assertThrows(SparkException.class, () -> df.collectAsList());
        assertThat(exception.getMessage(), containsString("Intentionally fails all tasks."));
    }

    private static class TaskFailureStateCounter {
        private static int totalTaskFailures = 0;

        public static synchronized void clear() {
            totalTaskFailures = 0;
        }
    }

}
