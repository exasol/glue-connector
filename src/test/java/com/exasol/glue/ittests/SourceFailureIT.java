package com.exasol.glue.ittests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.stream.Stream;

import com.exasol.dbbuilder.dialects.Table;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkException;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.api.java.function.MapGroupsFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

@Tag("integration")
@Testcontainers
class SourceFailureIT extends BaseIntegrationTest {

    private static Table table;

    private Dataset<Row> loadTable() {
        return spark.read() //
                .format("exasol") //
                .option("table", table.getFullyQualifiedName()) //
                .options(getDefaultOptions()) //
                .load();
    }

    @BeforeAll
    static void setup() {
        table = schema.createTableBuilder("table_failure") //
                .column("c1", "SMALLINT") //
                .build() //
                .bulkInsert(Stream.of(1, 2, 3, 4, 5, 6).map(n -> List.of(n)));
        spark.stop();
        final SparkConf conf = new SparkConf() //
                .setMaster("local[2,2]") //
                .setAppName("FailureTests") //
                .set("spark.ui.enabled", "false") //
                .set("spark.driver.host", "localhost");
        spark = SparkSessionProvider.getSparkSession(conf);
    }

    @AfterEach
    void afterEach() {
        TaskRunnerStateCounter.clear();
    }

    @Test
    void testSingleStageMapWithoutFailure() {
        final Dataset<Integer> df = loadTable() //
                .map((MapFunction<Row, Integer>) row -> {
                    final int value = row.getInt(0);
                    synchronized (TaskRunnerStateCounter.class) {
                        TaskRunnerStateCounter.totalTaskRuns += 1;
                    }
                    return value * 2;
                }, Encoders.INT());

        assertThat(df.collectAsList(), contains(2, 4, 6, 8, 10, 12));
        TaskRunnerStateCounter.assertCounterValues(6, 0);
    }

    // Intentionally fails a map task, ensure that failed task is restarted and Spark job finishes succesfully.
    @Test
    void testSingleStageMapFailure() {
        final Dataset<Integer> df = loadTable() //
                .map((MapFunction<Row, Integer>) row -> {
                    final int value = row.getInt(0);
                    synchronized (TaskRunnerStateCounter.class) {
                        TaskRunnerStateCounter.totalTaskRuns += 1;
                        if (value == 1 && TaskRunnerStateCounter.totalTaskFailures == 0) {
                            TaskRunnerStateCounter.totalTaskFailures += 1;
                            throw new RuntimeException("Intentionally fails currect task.");
                        }
                    }
                    return value * value;
                }, Encoders.INT());

        assertThat(df.collectAsList(), contains(1, 4, 9, 16, 25, 36));
        TaskRunnerStateCounter.assertCounterValues(7, 1);
    }

    @Test
    void testMultiStageMapWithCacheFailure() {
        final Dataset<Row> cachedDF = loadTable() //
                .filter((FilterFunction<Row>) row -> {
                    final int value = row.getInt(0);
                    synchronized (TaskRunnerStateCounter.class) {
                        TaskRunnerStateCounter.totalTaskRuns += 1;
                        if ((value == 1 || value == 3) && TaskRunnerStateCounter.totalTaskFailures < 2) {
                            TaskRunnerStateCounter.totalTaskFailures += 1;
                            throw new RuntimeException("Intentionally fails currect task for value '" + value + "'.");
                        }
                    }
                    return List.of(1, 3, 5).contains(row.getInt(0));
                }) //
                .cache();

        long size = cachedDF.count();
        assertThat(size, equalTo(3L));
        TaskRunnerStateCounter.assertCounterValues(8, 2);

        // Should stay the same
        size = cachedDF.count();
        TaskRunnerStateCounter.assertCounterValues(8, 2);
        assertThat(size, equalTo(3L));
    }

    // Intentionally fails a reduce task of two stage Spark job, ensure that failed task is restarted and Spark job
    // finishes succesfully.
    @Test
    void testMapReduceFailure() {
        final Dataset<String> df = loadTable() //
                .map((MapFunction<Row, Long>) row -> row.getInt(0) * 1L, Encoders.LONG()) //
                .groupByKey((MapFunction<Long, String>) v -> (v % 2) == 0 ? "even" : "odd", Encoders.STRING()) //
                .mapGroups((MapGroupsFunction<String, Long, String>) (key, values) -> {
                    synchronized (TaskRunnerStateCounter.class) {
                        TaskRunnerStateCounter.totalTaskRuns += 1;
                        if (key.equals("even") && TaskRunnerStateCounter.totalTaskFailures == 0) {
                            TaskRunnerStateCounter.totalTaskFailures += 1;
                            throw new RuntimeException("Intentionally fails reduce task with 'even' key.");
                        }
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append(key).append(": ");
                    while (values.hasNext()) {
                        sb.append(values.next());
                    }
                    return sb.toString();
                }, Encoders.STRING());

        assertThat(df.collectAsList(), containsInAnyOrder("even: 246", "odd: 135"));
        TaskRunnerStateCounter.assertCounterValues(3, 1);
    }

    @Test
    void testMapAlwaysFailure() {
        final Dataset<Integer> df = loadTable() //
                .map((MapFunction<Row, Integer>) row -> {
                    throw new RuntimeException("Intentionally fails all tasks.");
                }, Encoders.INT());

        final SparkException exception = assertThrows(SparkException.class, () -> df.collectAsList());
        assertThat(exception.getMessage(), containsString("Intentionally fails all tasks."));
    }

    private static class TaskRunnerStateCounter {
        private static int totalTaskRuns = 0;
        private static int totalTaskFailures = 0;

        public static synchronized void clear() {
            totalTaskRuns = 0;
            totalTaskFailures = 0;
        }

        public static synchronized void assertCounterValues(final int runs, final int failures) {
            assertThat(totalTaskRuns, equalTo(runs));
            assertThat(totalTaskFailures, equalTo(failures));
        }
    }

}
