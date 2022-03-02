package com.exasol.glue;

import java.util.List;

import com.exasol.errorreporting.ExaError;

import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

/**
 * A class that converts JDBC result set into a Spark {@link StructType} schema.
 */
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
        final StructType dummySchema = new StructType() //
                .add("col_str", DataTypes.StringType, false) //
                .add("col_int", DataTypes.IntegerType, false);
        return dummySchema;
    }

    /**
     * Converts a column description into a Spark {@link StructField} field type.
     *
     * @param column a column description
     * @return a matching Spark StructField type
     */
    public StructField convertColumn(final ColumnDescription column) {
        return null;
    }

}
