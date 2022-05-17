package com.exasol.glue.writer;

import com.exasol.glue.ExasolOptions;

import org.apache.spark.sql.connector.write.BatchWrite;
import org.apache.spark.sql.connector.write.WriteBuilder;

public class DelegatingWriteBuilder implements WriteBuilder {
    private final ExasolOptions options;
    private final WriteBuilder delegate;

    public DelegatingWriteBuilder(final ExasolOptions options, final WriteBuilder delegate) {
        this.options = options;
        this.delegate = delegate;
    }

    @Override
    public BatchWrite buildForBatch() {
        return new ExasolBatchWrite(this.options, delegate.buildForBatch());
    }
}
