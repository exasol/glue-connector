package com.exasol.glue;

import java.sql.Types;
import java.util.List;
import java.util.stream.Collectors;

import com.exasol.errorreporting.ExaError;

import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.DecimalType;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

/**
 * A class that converts JDBC result set into a Spark {@link StructType} schema.
 */
// [impl->dsn~schameconverter-converts-data-types~1]
public final class SchemaConverter {

    /**
     * Converts a list of column descriptions to Spark {@link StructType}.
     *
     * @param columns list of column descriptions
     * @return Spark schema
     */
    public StructType convert(final List<ColumnDescription> columns) {
        if (columns == null || columns.isEmpty()) {
            throw new IllegalArgumentException(ExaError.messageBuilder("E-EGC-10")
                    .message("Provided list of column descriptions is empty or null.")
                    .mitigation("Please make sure that table or query has column definitions.").toString());
        }
        final List<StructField> fields = columns.stream().map(this::convertColumn).collect(Collectors.toList());
        return new StructType(fields.toArray(new StructField[0]));
    }

    /**
     * Converts a column description into a Spark {@link StructField} field type.
     *
     * @param column a column description
     * @return a matching Spark StructField type
     */
    public StructField convertColumn(final ColumnDescription column) {
        return DataTypes.createStructField(column.getName(), mapJDBCType(column), column.isNullable());
    }

    private DataType mapJDBCType(final ColumnDescription column) {
        final int jdbcType = column.getType();
        switch (jdbcType) {
        case Types.BIT:
        case Types.BOOLEAN:
            return DataTypes.BooleanType;
        case Types.TINYINT:
        case Types.SMALLINT:
            return DataTypes.ShortType;
        case Types.DOUBLE:
        case Types.FLOAT:
            return DataTypes.DoubleType;
        case Types.REAL:
            return DataTypes.FloatType;
        case Types.INTEGER:
            return column.isSigned() ? DataTypes.IntegerType : DataTypes.LongType;
        case Types.BIGINT:
            return column.isSigned() ? DataTypes.LongType : DataTypes.createDecimalType(20, 0);
        case Types.DECIMAL:
        case Types.NUMERIC:
            if (column.getPrecision() != 0 || column.getScale() != 0) {
                return createDecimalDataType(column.getPrecision(), column.getScale());
            } else {
                return DecimalType.USER_DEFAULT();
            }
        case Types.CHAR:
        case Types.NCHAR:
        case Types.VARCHAR:
        case Types.NVARCHAR:
        case Types.LONGVARCHAR:
        case Types.LONGNVARCHAR:
        case Types.BINARY:
        case Types.VARBINARY:
        case Types.LONGVARBINARY:
            return DataTypes.StringType;
        case Types.DATE:
            return DataTypes.DateType;
        case Types.TIME:
        case Types.TIMESTAMP:
            return DataTypes.TimestampType;
        default:
            throw new UnsupportedOperationException(
                    ExaError.messageBuilder("E-EGC-12").message("Unsupported JDBC type {{type}} for column {{column}}.",
                            column.getType(), column.getName()).ticketMitigation().toString());
        }
    }

    private DataType createDecimalDataType(int precision, int scale) {
        if (precision > DecimalType.MAX_PRECISION()) {
            throw new IllegalStateException(ExaError.messageBuilder("E-EGC-13") //
                    .message("Exasol decimal precision {{exasolPrecision}} is larger than "
                            + "maximum Spark decimal precision {{sparkPrecision}}.") //
                    .parameter("exasolPrecision", precision) //
                    .parameter("sparkPrecision", DecimalType.MAX_PRECISION()) //
                    .ticketMitigation() //
                    .toString());
        }
        if (scale > DecimalType.MAX_SCALE()) {
            throw new IllegalStateException(ExaError.messageBuilder("E-EGC-14") //
                    .message("Exasol decimal scale {{exasolScale}} is larger than "
                            + "maximum Spark decimal scale {{sparkScale}}.") //
                    .parameter("exasolScale", scale) //
                    .parameter("sparkScale", DecimalType.MAX_SCALE()) //
                    .ticketMitigation() //
                    .toString());
        }
        return DataTypes.createDecimalType(precision, scale);
    }

}
