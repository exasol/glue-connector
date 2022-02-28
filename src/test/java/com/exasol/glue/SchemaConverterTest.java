package com.exasol.glue;

import static org.apache.spark.sql.types.DataTypes.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.sql.Types;
import java.util.List;
import java.util.stream.Stream;

import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class SchemaConverterTest {

    private final DataType LONG_DECIMAL = DataTypes.createDecimalType(20, 0);

    private static final Stream<Arguments> mappedTypes() {
        return Stream.of(//
                Arguments.of(Types.TINYINT, ShortType), //
                Arguments.of(Types.SMALLINT, ShortType), //
                Arguments.of(Types.DOUBLE, DoubleType), //
                Arguments.of(Types.FLOAT, DoubleType), //
                Arguments.of(Types.REAL, FloatType), //
                // Strings
                Arguments.of(Types.CHAR, StringType), //
                Arguments.of(Types.NCHAR, StringType), //
                Arguments.of(Types.VARCHAR, StringType), //
                Arguments.of(Types.NVARCHAR, StringType), //
                Arguments.of(Types.LONGVARCHAR, StringType), //
                Arguments.of(Types.LONGNVARCHAR, StringType), //
                // Binaries
                Arguments.of(Types.BINARY, StringType), //
                Arguments.of(Types.VARBINARY, StringType), //
                Arguments.of(Types.LONGVARBINARY, StringType), //
                // Booleans
                Arguments.of(Types.BIT, BooleanType), //
                Arguments.of(Types.BOOLEAN, BooleanType), //

                // Datetime
                Arguments.of(Types.DATE, DateType), //
                Arguments.of(Types.TIME, TimestampType), //
                Arguments.of(Types.TIMESTAMP, TimestampType), //

                // Others
                Arguments.of(Types.ROWID, LongType), //
                Arguments.of(Types.STRUCT, StringType));
    }

    @ParameterizedTest
    @MethodSource("mappedTypes")
    void testTypeConversions(final int jdbcType, final DataType sparkType) {
        assertConversion(jdbcType, sparkType).verify();
    }

    @ParameterizedTest
    @MethodSource("mappedTypes")
    void testTypeConversionsWithNullable(final int jdbcType, final DataType sparkType) {
        assertConversion(jdbcType, sparkType).isNullable(true).verify();
    }

    @Test
    void testIntegerConversion() {
        assertConversion(Types.INTEGER, IntegerType).verify();
    }

    @Test
    void testIntegerConversionWithSigned() {
        assertConversion(Types.INTEGER, LongType).isSigned(true).verify();
    }

    @Test
    void testBigintConversion() {
        assertConversion(Types.BIGINT, LongType).verify();
    }

    @Test
    void testBigintConversionWithSigned() {
        assertConversion(Types.BIGINT, LONG_DECIMAL).isSigned(true).verify();
    }

    @Test
    void testDecimalDefaultConversion() {
        for (final int jdbcType : List.of(Types.DECIMAL, Types.NUMERIC)) {
            assertConversion(jdbcType, DataTypes.createDecimalType()).verify();
        }
    }

    @ParameterizedTest
    @CsvSource({ //
            "38,4,  36,4", // Spark's max precision is 38, Exasol's is 36
            "37,37, 36,36", //
            "37,2,  36,2", //
            "36,2,  36,2", //
            "18,6   18,6", //
            "22,1   22,1", //
            "6,6    6,6", //
    })
    void testDecimalWithPrecisionAndScaleConversion(final int givenPrecision, final int givenScale,
            final int expectedPrecision, final int expectedScale) {
        for (final int jdbcType : List.of(Types.DECIMAL, Types.NUMERIC)) {
            final DataType sparkType = DataTypes.createDecimalType(expectedPrecision, expectedScale);
            assertConversion(jdbcType, sparkType).withPrecision(givenPrecision).withScale(givenScale).verify();
        }
    }

    private FieldConversionChecker assertConversion(final int jdbcType, final DataType sparkType) {
        return new FieldConversionChecker().withJdbcType(jdbcType).withSparkType(sparkType);
    }

    private static class FieldConversionChecker {
        private final String columnName = "COLUMN";
        private int jdbcType;
        private DataType sparkType;
        private boolean isNullable = false;
        private boolean isSigned = false;
        private int precision = 0;
        private int scale = 0;

        public FieldConversionChecker withJdbcType(final int jdbcType) {
            this.jdbcType = jdbcType;
            return this;
        }

        public FieldConversionChecker withSparkType(final DataType sparkType) {
            this.sparkType = sparkType;
            return this;
        }

        public FieldConversionChecker isNullable(final boolean isNullable) {
            this.isNullable = isNullable;
            return this;
        }

        public FieldConversionChecker isSigned(final boolean isSigned) {
            this.isSigned = isSigned;
            return this;
        }

        public FieldConversionChecker withPrecision(final int precision) {
            this.precision = precision;
            return this;
        }

        public FieldConversionChecker withScale(final int scale) {
            this.scale = scale;
            return this;
        }

        public void verify() {
            final ColumnDescription column = ColumnDescription.builder() //
                    .name(this.columnName) //
                    .type(this.jdbcType) //
                    .isNullable(this.isNullable) //
                    .isSigned(this.isSigned) //
                    .precision(this.precision) //
                    .scale(this.scale) //
                    .build();
            final StructField field = DataTypes.createStructField(this.columnName, this.sparkType, this.isNullable);
            assertThat(new SchemaConverter().convertColumn(column), equalTo(field));
        }
    }

}
