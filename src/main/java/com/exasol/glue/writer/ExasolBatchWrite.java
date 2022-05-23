package com.exasol.glue.writer;

import static com.exasol.glue.Constants.INTERMEDIATE_DATA_PATH;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

import com.exasol.errorreporting.ExaError;
import com.exasol.glue.ExasolOptions;
import com.exasol.glue.ExasolValidationException;
import com.exasol.glue.connection.ExasolConnectionException;
import com.exasol.glue.connection.ExasolConnectionFactory;
import com.exasol.glue.query.ImportQueryGenerator;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.connector.write.*;

/**
 * An Exasol {@link BatchWrite} class.
 */
public class ExasolBatchWrite implements BatchWrite {
    private static final Logger LOGGER = Logger.getLogger(ExasolBatchWrite.class.getName());
    private static final String MITIGATION_MESSAGE = "Please make sure the path is correct, starts with 's3a://'.";

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
        LOGGER.info(() -> "Generated import '" + query + "' query.");
        try {
            final int rows = runImportQuery(query);
            final long time = System.currentTimeMillis() - start;
            LOGGER.info(() -> "Imported '" + rows + "' rows into the table '" + table + "' in '" + time + "' millis.");
        } catch (final SQLException exception) {
            throw new ExasolConnectionException(ExaError.messageBuilder("E-EGC-24")
                    .message("Failure running the import {{query}} query.", query)
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
        final String path = this.options.get(INTERMEDIATE_DATA_PATH);
        LOGGER.info(() -> "Running cleanup process for directory '" + path + "'.");
        final SparkSession sparkSession = SparkSession.active();
        final Configuration conf = sparkSession.sparkContext().hadoopConfiguration();
        final URI tmpDirectoryURI = getPathURI(path);
        try (final FileSystem fileSystem = FileSystem.get(tmpDirectoryURI, conf)) {
            final RemoteIterator<LocatedFileStatus> listFiles = getFileStatuses(path, fileSystem);
            while (listFiles.hasNext()) {
                final LocatedFileStatus fileStatus = listFiles.next();
                fileSystem.delete(fileStatus.getPath(), false);
            }
            fileSystem.delete(new Path(this.options.get("tempdir")), true);
        } catch (final IOException exception) {
            throw new ExasolValidationException(
                    ExaError.messageBuilder("E-EGC-25").message("Failed to list files in the path {{path}}.", path)
                            .mitigation(MITIGATION_MESSAGE).toString(),
                    exception);
        }

    }

    private URI getPathURI(final String path) {
        try {
            return new URI(path);
        } catch (final URISyntaxException exception) {
            throw new ExasolValidationException(ExaError.messageBuilder("E-EGC-26")
                    .message("Provided path {{path}} cannot be converted to URI systax.", path)
                    .mitigation(MITIGATION_MESSAGE).toString(), exception);
        }
    }

    private RemoteIterator<LocatedFileStatus> getFileStatuses(final String path, final FileSystem fs) {
        try {
            return fs.listFiles(new Path(path), false);
        } catch (final FileNotFoundException exception) {
            return new EmptyRemoteIterator<>();
        } catch (final IOException exception) {
            throw new ExasolValidationException(ExaError.messageBuilder("E-EGC-27")
                    .message("Provided path {{path}} does not exist or the path does not allow listing files.", path)
                    .mitigation(MITIGATION_MESSAGE).toString(), exception);
        }
    }

    private static class EmptyRemoteIterator<E> implements RemoteIterator<E> {
        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public E next() {
            throw new NoSuchElementException();
        }
    }

}
