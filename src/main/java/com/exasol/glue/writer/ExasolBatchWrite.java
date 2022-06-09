package com.exasol.glue.writer;

import static com.exasol.glue.Constants.INTERMEDIATE_DATA_PATH;
import static com.exasol.glue.Constants.WRITE_S3_BUCKET_KEY;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import com.exasol.errorreporting.ExaError;
import com.exasol.glue.ExasolOptions;
import com.exasol.glue.connection.ExasolConnectionException;
import com.exasol.glue.connection.ExasolConnectionFactory;
import com.exasol.glue.filesystem.S3FileSystem;
import com.exasol.glue.query.AbstractQueryGenerator;
import com.exasol.glue.query.ImportQueryGenerator;

import org.apache.spark.sql.connector.write.*;

/**
 * An Exasol {@link BatchWrite} class.
 */
public class ExasolBatchWrite implements BatchWrite {
    private static final Logger LOGGER = Logger.getLogger(ExasolBatchWrite.class.getName());

    private final ExasolOptions options;
    private final BatchWrite delegate;

    /**
     * Creates a new instance of {@link ExasolBatchWrite}.
     *
     * @param options  user provided options
     * @param delegate delegate {@code CSV} batch write
     */
    public ExasolBatchWrite(final ExasolOptions options, final BatchWrite delegate) {
        this.options = options;
        this.delegate = delegate;
    }

    @Override
    public DataWriterFactory createBatchWriterFactory(final PhysicalWriteInfo info) {
        return delegate.createBatchWriterFactory(info);
    }

    @Override
    public boolean useCommitCoordinator() {
        return delegate.useCommitCoordinator();
    }

    @Override
    public void commit(final WriterCommitMessage[] messages) {
        LOGGER.info("Committing the file writing stage of the job.");
        delegate.commit(messages);
        importIntermediateData();
    }

    @Override
    public void abort(final WriterCommitMessage[] messages) {
        LOGGER.info("Running abort stage of the job.");
        cleanup();
        delegate.abort(messages);
    }

    private void importIntermediateData() {
        final long start = System.currentTimeMillis();
        final String table = this.options.getTable();
        final String query = new ImportQueryGenerator(this.options).generateQuery();
        try {
            final int rows = runImportQuery(query);
            final long time = System.currentTimeMillis() - start;
            LOGGER.info(() -> "Imported '" + rows + "' rows into the table '" + table + "' in '" + time + "' millis.");
        } catch (final SQLException exception) {
            throw new ExasolConnectionException(ExaError.messageBuilder("E-EGC-24")
                    .message("Failure running the import {{query}} query.",
                            AbstractQueryGenerator.identifierRemoved(query))
                    .mitigation("Please check that connection address, username and password are correct.").toString(),
                    exception);
        } finally {
            cleanup();
        }
    }

    private int runImportQuery(final String query) throws SQLException {
        try (final Connection connection = new ExasolConnectionFactory(this.options).getConnection();
                final Statement stmt = connection.createStatement()) {
            connection.setAutoCommit(false);
            final int rows = stmt.executeUpdate(query);
            connection.commit();
            return rows;
        }
    }

    private void cleanup() {
        LOGGER.info(() -> "Running cleanup process for directory '" + this.options.get(INTERMEDIATE_DATA_PATH) + "'.");
        try (final S3FileSystem s3FileSystem = new S3FileSystem(this.options)) {
            s3FileSystem.deleteKeys(this.options.getS3Bucket(), this.options.get(WRITE_S3_BUCKET_KEY));
        }
    }

}
