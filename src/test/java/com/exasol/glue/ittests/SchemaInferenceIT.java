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
            "BOOLEAN;          BOOLEAN", //
            "CHAR(1);          STRING", //
            "CHAR(20);         STRING", //
            "CHAR(2000);       STRING", //
            "VARCHAR(5);       STRING", //
            "VARCHAR(20);      STRING", //
            "VARCHAR(2000000); STRING", //
            "DECIMAL(3,2);     DECIMAL(3,2)", //
            "DECIMAL(18,0);    LONG", //
            "DECIMAL(30,20);   DECIMAL(30,20)", //
            "DECIMAL(36,36);   DECIMAL(36,36)", //
            "DOUBLE PRECISION; DOUBLE", //
            "DATE;             DATE", //
            "TIMESTAMP;        TIMESTAMP", //
    }, delimiter = ';')
    void testInferSchemaSingleColumnBaseTypes(final String exasolType, final String sparkType) {
        final StructType schema = StructType.fromDDL("c1 " + sparkType);
        assertThat(getSingleColumnTableSchema(exasolType), equalTo(schema));
    }

    @ParameterizedTest
    @CsvSource(value = { //
            "BOOL;                            BOOLEAN", //
            "CHAR;                            STRING", //
            "CHAR VARYING(1);                 STRING", //
            "CHAR VARYING(12);                STRING", //
            "CHAR VARYING(2000000);           STRING", //
            "CHARACTER;                       STRING", //
            "CHARACTER VARYING(3);            STRING", //
            "CHARACTER VARYING(2000000);      STRING", //
            "CHARACTER LARGE OBJECT;          STRING", //
            "CHARACTER LARGE OBJECT(1);       STRING", //
            "CHARACTER LARGE OBJECT(2000000); STRING", //
            "CLOB;                            STRING", //
            "CLOB(2);                         STRING", //
            "CLOB(27);                        STRING", //
            "CLOB(2000000);                   STRING", //
            "DEC;                             LONG", //
            "DEC(18);                         LONG", //
            "DEC(29);                         DECIMAL(29,0)", //
            "DEC(36);                         DECIMAL(36,0)", //
            "DEC(6,2);                        DECIMAL(6,2)", //
            "DEC(36,5);                       DECIMAL(36,5)", //
            "DEC(36,36);                      DECIMAL(36,36)", //
            "DECIMAL;                         LONG", //
            "DECIMAL(18);                     LONG", //
            "DECIMAL(23);                     DECIMAL(23,0)", //
            "DECIMAL(36);                     DECIMAL(36,0)", //
            "DOUBLE;                          DOUBLE", //
            "FLOAT;                           DOUBLE", //
            "REAL;                            DOUBLE", //
            "BIGINT;                          DECIMAL(36,0)", //
            "INT;                             LONG", //
            "INTEGER;                         LONG", //
            "SHORTINT;                        INT", //
            "SMALLINT;                        INT", //
            "TINYINT;                         SHORT", //
            "LONG VARCHAR;                    STRING", //
            "NCHAR(1);                        STRING", //
            "NCHAR(2000);                     STRING", //
            "NVARCHAR(1337);                  STRING", //
            "NVARCHAR2(1337);                 STRING", //
            "VARCHAR2(13);                    STRING", //
            "VARCHAR2(2000000);               STRING", //
            "NUMBER;                          DOUBLE", //
            "NUMBER(18);                      LONG", //
            "NUMBER(36);                      DECIMAL(36,0)", //
            "NUMBER(13,11);                   DECIMAL(13,11)", //
            "NUMERIC;                         LONG", //
            "NUMERIC(18);                     LONG", //
            "NUMERIC(22);                     DECIMAL(22,0)", //
            "NUMERIC(22,14);                  DECIMAL(22,14)", //
            "NUMERIC(36,18);                  DECIMAL(36,18)", //
    }, delimiter = ';')
    void testInferSchemaSingleColumnAliasTypes(final String exasolType, final String sparkType) {
        final StructType schema = StructType.fromDDL("c1 " + sparkType);
        assertThat(getSingleColumnTableSchema(exasolType), equalTo(schema));
    }

    private StructType getSingleColumnTableSchema(final String exasolType) {
        final String tableName = exasolType.replaceAll("[(, )]", "_") + "_TABLE";
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
