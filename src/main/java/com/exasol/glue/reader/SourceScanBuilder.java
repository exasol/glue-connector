package com.exasol.glue.reader;

import com.exasol.glue.ExasolOptions;

import org.apache.spark.sql.connector.read.Scan;
import org.apache.spark.sql.connector.read.ScanBuilder;
import org.apache.spark.sql.types.StructType;

/**
 * A builder class that provides data reader classes.
 */
public class SourceScanBuilder implements ScanBuilder {
    private final StructType schema;
    private final ExasolOptions options;

    /**
     * Creates a new instance of {@link SourceScanBuilder}.
     *
     * @param schema schema of the resulting data
     * @param options key value map of options provided by users
     */
    public SourceScanBuilder(final StructType schema, final ExasolOptions options) {
        this.schema = schema;
        this.options = options;
    }

    @Override
    public Scan build() {
        return new SourceScan(schema, options);
    }

}
