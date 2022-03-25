package com.exasol.glue.reader;

import com.exasol.glue.ExasolOptions;

import org.apache.spark.sql.connector.read.Batch;
import org.apache.spark.sql.connector.read.Scan;
import org.apache.spark.sql.types.StructType;

/**
 * A scan class that provides source {@linkg Batch} implementation.
 */
public class SourceScan implements Scan {
    private final StructType schema;
    private final ExasolOptions options;

    /**
     * Creates a new instance of {@link SourceScan}.
     *
     * @param schema schema of the resulting data
     * @param options key value map of options provided by users
     */
    public SourceScan(final StructType schema, final ExasolOptions options) {
        this.schema = schema;
        this.options = options;
    }

    @Override
    public StructType readSchema() {
        return this.schema;
    }

    @Override
    public Batch toBatch() {
        return new SourceBatch(this.schema, this.options);
    }
}
