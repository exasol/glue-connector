package com.exasol.glue;

import java.util.List;

import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public final class SchemaConverter {

    public StructType convert(final List<ColumnDescription> columns) {
        return null;
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
