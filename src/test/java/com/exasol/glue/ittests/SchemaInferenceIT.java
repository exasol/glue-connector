package com.exasol.glue.ittests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;
import java.util.Map;

import com.exasol.dbbuilder.dialects.Table;

import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.testcontainers.junit.jupiter.Testcontainers;

@Tag("integration")
@Testcontainers
class SchemaInferenceIT extends BaseIntegrationTest {

    @Test
    void testSparkSanityCheck() {
        final StructType schema = new StructType() //
                .add("col_str", DataTypes.StringType, false) //
                .add("col_int", DataTypes.IntegerType, false);
        spark.createDataFrame(List.of(RowFactory.create("value", 10)), schema).show();
    }

    @Test
    void testInferSchemaMultiColumn() {
        final Table table = schema.createTable("multi_column_table", //
                List.of("c1", "c2", "c3", "c4"), //
                List.of("VARCHAR(10)", "INTEGER", "DATE", "DOUBLE"));
        final StructType expectedSchema = new StructType() //
                .add("c1", DataTypes.StringType, true) //
                .add("c2", DataTypes.LongType, true) //
                .add("c3", DataTypes.DateType, true) //
                .add("c4", DataTypes.DoubleType, true);
        final StructType schema = spark.read() //
                .format("exasol") //
                .option("query", "SELECT * FROM " + table.getFullyQualifiedName()) //
                .options(getDefaultOptions()) //
                .load() //
                .schema();
        assertThat(schema, equalTo(expectedSchema));
    }

    @ParameterizedTest
    @CsvSource(value = { //
            "BOOLEAN;        BOOLEAN", //
            "INTEGER;        LONG", //
            "DOUBLE;         DOUBLE", //
            "FLOAT;          DOUBLE", //
            "SHORTINT;       INT", //
            "SMALLINT;       INT", //
            "TINYINT;        SHORT", //
            "BIGINT;         DECIMAL(36,0)", //
            "DECIMAL;        LONG", //
            "DEC(36);        DECIMAL(36,0)", //
            "DECIMAL(3,2);   DECIMAL(3,2)", //
            "DECIMAL(36,36); DECIMAL(36,36)", //
            "CHAR(20);       STRING", //
            "VARCHAR(20);    STRING", //
            "CLOB;           STRING", //
            "CLOB(20);       STRING", //
            "DATE;           DATE", //
            "TIMESTAMP;      TIMESTAMP" //
    }, delimiter = ';')
    void testInferSchemaSingleColumn(final String exasolType, final String sparkType) {
        final StructType schema = StructType.fromDDL("c1 " + sparkType);
        assertThat(getSingleColumnTableSchema(exasolType), equalTo(schema));
    }

    private StructType getSingleColumnTableSchema(final String exasolType) {
        final String tableName = exasolType.replaceAll("[(,)]", "_") + "_TABLE";
        final Table table = schema.createTable(tableName, "c1", exasolType);
        return spark.read() //
                .format("exasol") //
                .option("table", table.getFullyQualifiedName()) //
                .options(getDefaultOptions()) //
                .load() //
                .schema();
    }

    private Map<String, String> getDefaultOptions() {
        return Map.of("jdbc_url", getJdbcUrl(), "username", getUsername(), "password", getPassword());
    }

}
