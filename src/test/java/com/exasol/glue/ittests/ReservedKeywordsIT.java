package com.exasol.glue.ittests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

import com.exasol.dbbuilder.dialects.Table;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

@Tag("integration")
@Testcontainers
class ReservedKeywordsIT extends BaseIntegrationTestSetup {
    private static Table table;

    @BeforeAll
    static void setup() {
        table = schema.createTableBuilder("table_reserved_keywords") //
                .column("id", "DECIMAL(9,0)") //
                .column("CONDITION", "VARCHAR(30)") //
                .build() //
                .insert(1, "blocked") //
                .insert(2, "validated") //
                .insert(3, "updated");
    }

    private Dataset<Row> loadQuery(final String query) {
        return spark.read().format("exasol").option("query", query).options(getDefaultOptions()).load();
    }

    @Test
    void testReadFromTableWithReservedKeyword() {
        final Dataset<String> df = loadTable(table.getFullyQualifiedName())
                .map((MapFunction<Row, String>) row -> row.getString(1), Encoders.STRING());
        assertThat(df.collectAsList(), contains("blocked", "validated", "updated"));
    }

    @Test
    void testReadFromQueryWithReservedKeyword() {
        final Dataset<String> df = loadQuery("SELECT \"CONDITION\" FROM " + table.getFullyQualifiedName())
                .as(Encoders.STRING());
        assertThat(df.collectAsList(), contains("blocked", "validated", "updated"));
    }

    @Test
    void testColumnProjectionWithReservedKeyword() {
        final Dataset<String> df = loadTable(table.getFullyQualifiedName()).select("condition").as(Encoders.STRING());
        assertThat(df.collectAsList(), contains("blocked", "validated", "updated"));
    }

    @Test
    void testFilterPredicateWithReservedKeyword() {
        loadTable(table.getFullyQualifiedName()).createOrReplaceTempView("T1");
        final Dataset<Integer> df = spark.sql("SELECT id FROM T1 WHERE condition LIKE '%lock%'").as(Encoders.INT());
        assertThat(df.collectAsList(), contains(1));
    }

}
