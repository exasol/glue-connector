package com.exasol.glue.ittests;

import static org.apache.spark.sql.functions.col;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

import com.exasol.dbbuilder.dialects.Table;
import com.exasol.logging.CapturingLogHandler;

import org.apache.spark.sql.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testcontainers.junit.jupiter.Testcontainers;

@Tag("integration")
@Testcontainers
class ColumnProjectionPredicatePushdownIT extends BaseIntegrationTestSetup {
    private static Table table;
    private final CapturingLogHandler capturingLogHandler = new CapturingLogHandler();

    @BeforeAll
    static void setup() {
        table = schema.createTableBuilder("table_pruning_pushdown") //
                .column("c_int", "DECIMAL(9,0)") //
                .column("c_str", "VARCHAR(30)") //
                .column("c_double", "DOUBLE") //
                .column("c_bool", "BOOLEAN") //
                .build() //
                .insert(1, "abc", 3.14, true) //
                .insert(2, "xyz", 2.72, false) //
                .insert(3, "str", 0.02, false);
    }

    private Dataset<Row> loadTable() {
        return loadTable(table.getFullyQualifiedName());
    }

    @BeforeEach
    void beforeEach() {
        loadTable().createOrReplaceTempView("t1");
        Logger.getLogger("com.exasol").addHandler(this.capturingLogHandler);
        this.capturingLogHandler.reset();
    }

    @AfterEach
    void afterEach() {
        Logger.getLogger("com.exasol").removeHandler(this.capturingLogHandler);
    }

    @Test
    void testProjectColumn() {
        final Dataset<String> df = loadTable().select("c_str").as(Encoders.STRING());
        assertAll(() -> assertThat(df.collectAsList(), contains("abc", "xyz", "str")),
                () -> assertThat(df.queryExecution().toString(), containsString("Project [c_str")),
                () -> assertThat(this.capturingLogHandler.getCapturedData(),
                        containsString("SELECT \"c_str\" FROM \"DEFAULT_SCHEMA\".\"table_pruning_pushdown\"")));
    }

    @Test
    void testPredicateEqualTo() {
        final Dataset<Row> df = spark.sql("SELECT c_int, c_bool FROM t1 WHERE c_str = 'abc'");
        final List<Row> rows = df.collectAsList();
        assertAll(() -> assertThat(rows.size(), equalTo(1)), //
                () -> assertThat(rows.get(0).getInt(0), equalTo(1)),
                () -> assertThat(rows.get(0).getBoolean(1), equalTo(true)),
                () -> assertThat(df.queryExecution().toString(), containsString("Filter ('c_str = abc)")), //
                () -> assertThat(this.capturingLogHandler.getCapturedData(),
                        containsString("WHERE (\"c_str\" IS NOT NULL) AND (\"c_str\" = 'abc')")));
    }

    @Test
    void testPredicateStartsWith() {
        final Dataset<Integer> df = spark.sql("SELECT c_int FROM t1 WHERE c_str LIKE 'x%'").as(Encoders.INT());
        assertAll(() -> assertThat(df.collectAsList(), contains(2)), //
                () -> assertThat(df.queryExecution().toString(), containsString("LIKE x%")), //
                () -> assertThat(this.capturingLogHandler.getCapturedData(),
                        containsString("WHERE (\"c_str\" IS NOT NULL) AND (\"c_str\" LIKE 'x%' ESCAPE '\\')")));
    }

    @Test
    void testPredicateStringContains() {
        final Dataset<String> df = spark.sql("SELECT c_str FROM t1 WHERE c_str LIKE '%y%'").as(Encoders.STRING());
        assertThat(df.collectAsList(), contains("xyz"));
    }

    @Test
    void testPredicateStringEndsWith() {
        final Dataset<String> df = spark.sql("SELECT c_str FROM t1 WHERE c_str LIKE '%c'").as(Encoders.STRING());
        assertThat(df.collectAsList(), contains("abc"));
    }

    private static final Table escapedStringsTable = schema.createTableBuilder("table_pruning_pushdown_strings") //
            .column("c_int", "DECIMAL(9,0)") //
            .column("c_str", "VARCHAR(30)") //
            .build() //
            .insert("1", "unders\\corewildcard") //
            .insert("2", "%underscore_wild%card%") //
            .insert("3", "underscoreXwildcard") //
            .insert("4", "contains'singlequote") //
            .insert("5", "escaped\\_underscore");

    private static final Stream<Arguments> stringFilters() {
        return Stream.of(//
                Arguments.of(col("c_str").startsWith("%under"), 2), //
                Arguments.of(col("c_str").contains("e_wild%"), 2), //
                Arguments.of(col("c_str").endsWith("card%"), 2), //
                Arguments.of(col("c_str").contains("s\\cor"), 1), //
                Arguments.of(col("c_str").contains("ains'sing"), 4), //
                Arguments.of(col("c_str").contains("d\\_"), 5) //
        );
    }

    @ParameterizedTest
    @MethodSource("stringFilters")
    void testPredicateStringLiteralsEscaped(final Column column, final int id) {
        final Dataset<Integer> df = loadTable(escapedStringsTable.getFullyQualifiedName()).select("c_int")
                .filter(column).as(Encoders.INT());
        assertThat(df.collectAsList(), contains(id));
    }

    @Test
    void testPredicateLessThan() {
        final Dataset<String> df = spark.sql("SELECT c_str FROM t1 WHERE c_double < 1.00").as(Encoders.STRING());
        assertThat(df.collectAsList(), contains("str"));
    }

    @Test
    void testPredicateLessThanOrEqual() {
        final Dataset<String> df = spark.sql("SELECT c_str FROM t1 WHERE c_int <= 2").as(Encoders.STRING());
        assertThat(df.collectAsList(), contains("abc", "xyz"));
    }

    @Test
    void testPredicateGreaterThan() {
        final Dataset<Double> df = spark.sql("SELECT c_double FROM t1 WHERE c_int > 1").as(Encoders.DOUBLE());
        assertThat(df.collectAsList(), contains(2.72, 0.02));
    }

    @Test
    void testPredicateGreaterThanOrEqual() {
        final Dataset<Double> df = spark.sql("SELECT c_double FROM t1 WHERE c_int >= 1").as(Encoders.DOUBLE());
        assertThat(df.collectAsList(), contains(3.14, 2.72, 0.02));
    }

    @Test
    void testNonPushedFiltersAreRunPostScan() {
        final Dataset<Row> df = loadTable() //
                .select("c_str", "c_int", "c_bool") //
                .filter(col("c_str").eqNullSafe("abc")) // not pushed, should be filtered after scan
                .filter(col("c_double").gt(0.0));
        assertThat(df.collectAsList(), contains(RowFactory.create("abc", 1, true)));
    }

    @Test
    void testMultipleFilters() {
        final Dataset<Row> df = loadTable() //
                .select("c_str", "c_bool") //
                .filter(col("c_bool").equalTo(false)) //
                .filter(col("c_double").gt(0.00));
        assertThat(df.collectAsList(), contains(RowFactory.create("xyz", false), RowFactory.create("str", false)));
    }

}
