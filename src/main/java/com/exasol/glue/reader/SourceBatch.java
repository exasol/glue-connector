package com.exasol.glue.reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import com.exasol.glue.ExasolOptions;

import org.apache.spark.sql.catalyst.InternalRow;
import org.apache.spark.sql.catalyst.expressions.GenericInternalRow;
import org.apache.spark.sql.connector.read.Batch;
import org.apache.spark.sql.connector.read.InputPartition;
import org.apache.spark.sql.connector.read.PartitionReader;
import org.apache.spark.sql.connector.read.PartitionReaderFactory;
import org.apache.spark.sql.types.StructType;

public class SourceBatch implements Batch {
    private final StructType schema;
    private final ExasolOptions options;

    public SourceBatch(final StructType schema, final ExasolOptions options) {
        this.schema = schema;
        this.options = options;
    }

    @Override
    public InputPartition[] planInputPartitions() {
        return new InputPartition[] { //
                new SourceInputPartition(0, schema), //
                new SourceInputPartition(1, schema), //
                new SourceInputPartition(2, schema), //
        };
    }

    @Override
    public PartitionReaderFactory createReaderFactory() {
        return new SourcePartitionReaderFactory();
    }

    private static class SourcePartitionReaderFactory implements PartitionReaderFactory {
        private static final long serialVersionUID = 6570701104630705124L;

        @Override
        public PartitionReader<InternalRow> createReader(InputPartition inputPartition) {
            return new SourcePartitionReader((SourceInputPartition) inputPartition);
        }
    }

    private static class SourcePartitionReader implements PartitionReader<InternalRow> {
        private static final Logger LOGGER = Logger.getLogger(SourcePartitionReader.class.getName());
        private final SourceInputPartition partition;

        private Iterator<InternalRow> iterator;
        private InternalRow currentRow = null;

        public SourcePartitionReader(final SourceInputPartition partition) {
            this.partition = partition;
            this.iterator = createIterator();
        }

        private Iterator<InternalRow> createIterator() {
            LOGGER.info(() -> "Creating partition reader for partition id '" + this.partition.id + "'.");
            List<InternalRow> result = new ArrayList<>();
            for (int i = 1; i <= 2; i++) {
                result.add(new GenericInternalRow(new Object[] { this.partition.id * 2 + i }));
            }
            return result.iterator();
        }

        @Override
        public boolean next() {
            if (!iterator.hasNext()) {
                return false;
            }
            currentRow = iterator.next();
            return true;
        }

        @Override
        public InternalRow get() {
            return currentRow;
        }

        @Override
        public void close() {
        }
    }

    private static class SourceInputPartition implements InputPartition {
        private static final long serialVersionUID = 2666711621377659123L;
        private final int id;
        private final StructType schema;

        public SourceInputPartition(final int id, final StructType schema) {
            this.id = id;
            this.schema = schema;
        }

        public StructType getSchema() {
            return schema;
        }

    }
}
