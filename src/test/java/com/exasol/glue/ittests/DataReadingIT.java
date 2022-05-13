package com.exasol.glue.ittests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.exasol.dbbuilder.dialects.Table;
import com.exasol.glue.ExasolValidationException;

import org.apache.spark.api.java.function.*;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.types.StructType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

@Tag("integration")
@Testcontainers
class DataReadingIT extends BaseIntegrationTestSetup {

    private static Table table;

    private Dataset<Row> loadTable() {
        return loadTable(table.getFullyQualifiedName());
    }

    @BeforeAll
    static void setup() {
        table = schema.createTableBuilder("table_transformation") //
                .column("c1", "SMALLINT") //
                .build() //
                .bulkInsert(Stream.of(1, 2, 3, 4, 5, 6).map(n -> List.of(n)));
    }

    @Test
    void testDataFrameShow() {
        final Dataset<Row> df = spark.read() //
                .format("exasol") //
                .option("query", "SELECT * FROM " + table.getFullyQualifiedName()) //
                .options(getDefaultOptions()) //
                .load();
        df.show();
        assertThat(df.count(), equalTo(6L));
    }

    @Test
    void testProvidedSchema() {
        final StructType expectedSchema = StructType.fromDDL("col_str STRING");
        final StructType schema = spark.read() //
                .schema(expectedSchema) //
                .format("exasol") //
                .option("table", table.getFullyQualifiedName()) //
                .options(getDefaultOptions()) //
                .load() //
                .schema();
        assertThat(schema, equalTo(expectedSchema));
    }

    @Test
    void testMapTransformation() {
        Dataset<Integer> df = loadTable().map((MapFunction<Row, Integer>) row -> {
            return row.getInt(0) * 2;
        }, Encoders.INT());
        assertThat(df.collectAsList(), contains(2, 4, 6, 8, 10, 12));
    }

    @Test
    void testMapPartitionsTransformation() {
        Dataset<String> df = loadTable().mapPartitions((MapPartitionsFunction<Row, String>) it -> {
            List<String> result = new ArrayList<>();
            while (it.hasNext()) {
                result.add(String.valueOf(it.next().getInt(0)));
            }
            return result.iterator();
        }, Encoders.STRING());
        assertThat(df.collectAsList(), contains("1", "2", "3", "4", "5", "6"));
    }

    @Test
    void testFlatMapTransformation() {
        Dataset<Integer> df = loadTable().flatMap((FlatMapFunction<Row, Integer>) row -> {
            return List.of(row.getInt(0), row.getInt(0)).iterator();
        }, Encoders.INT());
        assertThat(df.collectAsList(), contains(1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6));
    }

    @Test
    void testFilterTransformation() {
        Dataset<Integer> df = loadTable() //
                .filter((FilterFunction<Row>) row -> (row.getInt(0) % 2) == 0 ? true : false) //
                .map((MapFunction<Row, Integer>) row -> row.getInt(0), Encoders.INT());
        assertThat(df.collectAsList(), contains(2, 4, 6));
    }

    @Test
    void testThrowsIfNumberOfPartitionsExceedsMaximumAllowed() {
        Dataset<Row> df = spark.read() //
                .format("exasol") //
                .option("table", table.getFullyQualifiedName()) //
                .options(getDefaultOptions()) //
                .option("numPartitions", "1001") //
                .load();
        final ExasolValidationException exception = assertThrows(ExasolValidationException.class,
                () -> df.collectAsList());
        assertThat(exception.getMessage(), containsString("is larger than maximum allowed '1000' value."));
    }

}
