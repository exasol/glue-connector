package com.exasol.glue.writer;

import static com.exasol.glue.Constants.PASSWORD;
import static com.exasol.glue.Constants.PATH;

import java.io.IOException;
import java.sql.*;
import java.util.logging.Logger;

import com.exasol.errorreporting.ExaError;
import com.exasol.glue.ExasolOptions;
import com.exasol.glue.ExasolValidationException;
import com.exasol.glue.connection.ExasolConnectionException;
import com.exasol.glue.query.ImportQueryGenerator;

import org.apache.hadoop.fs.*;
import org.apache.spark.sql.connector.write.*;

public class ExasolBatchWrite implements BatchWrite {
    private static final Logger LOGGER = Logger.getLogger(ExasolBatchWrite.class.getName());

    private final ExasolOptions options;
    private final BatchWrite delegate;

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
        final long start = System.currentTimeMillis();
        final String table = this.options.getTable();
        final String query = new ImportQueryGenerator(this.options).generateQuery();
        LOGGER.info(() -> "Generated import '" + query + "' query.");
        try {
            final int rows = runImportQuery(query);
            final long time = System.currentTimeMillis() - start;
            LOGGER.info(() -> "Imported '" + rows + "' rows into the table '" + table + "' in '" + time + "' millis.");
        } catch (final SQLException exception) {
            throw new ExasolConnectionException(ExaError.messageBuilder("E-ESC-7")
                    .message("Failure running the import {{query}} query.", query)
                    .mitigation("Please check that connection address, username and password are correct.").toString(),
                    exception);
        } finally {
            cleanup();
        }
    }

    @Override
    public void abort(final WriterCommitMessage[] messages) {
        LOGGER.info("Running abort stage of the job.");
        cleanup();
        delegate.abort(messages);
    }

    private int runImportQuery(final String query) throws SQLException {
        try (final Connection connection = getConnection(); final Statement stmt = connection.createStatement()) {
            connection.setAutoCommit(false);
            final int rows = stmt.executeUpdate(query);
            connection.commit();
            return rows;
        }

    }

    public Connection getConnection() {
        final String address = this.options.getJdbcUrl();
        final String username = this.options.getUsername();
        LOGGER.fine(() -> "Getting connection at '" + address + "' with username '" + username + "' and password.");
        try {
            final Connection connection = DriverManager.getConnection(address, username, this.options.get(PASSWORD));
            return connection;
        } catch (final SQLException exception) {
            throw new ExasolConnectionException(ExaError.messageBuilder("E-ESC-6")
                    .message("Could not connect to Exasol address on {{address}} with username {{username}}.")
                    .parameter("address", address).parameter("username", username)
                    .mitigation("Please check that connection address, username and password are correct.").toString(),
                    exception);
        }
    }

    private void cleanup() {
        final String path = this.options.get(PATH);
        LOGGER.info(() -> "Running cleanup process for directory '" + path + "'.");
        // final SparkSession sparkSession = SparkSession.active();
        // final Configuration conf = sparkSession.sparkContext().hadoopConfiguration();
        // final URI tmpDirectoryURI = getPathURI(path);
        // try (final FileSystem fileSystem = FileSystem.get(tmpDirectoryURI, conf)) {
        // final RemoteIterator<LocatedFileStatus> listFiles = getFileStatuses(path, fileSystem);
        // while (listFiles.hasNext()) {
        // final LocatedFileStatus fileStatus = listFiles.next();
        // fileSystem.delete(fileStatus.getPath(), false);
        // }
        // fileSystem.delete(new Path(this.options.get("tempdir")), true);
        // } catch (final IOException exception) {
        // throw new ExasolValidationException(ExaError.messageBuilder("E-ESC-5")
        // .message("Failed to list files in the path {{path}}.", path)
        // .mitigation("Please make sure the path is correct file system (hdfs, s3a, etc) path.").toString(),
        // exception);
        // }

    }

    private RemoteIterator<LocatedFileStatus> getFileStatuses(final String path, final FileSystem fs) {
        try {
            return fs.listFiles(new Path(path), false);
        } catch (final IOException exception) {
            throw new ExasolValidationException(ExaError.messageBuilder("E-ESC-4")
                    .message("Provided path {{path}} does not exist or the path does not allow listing files.", path)
                    .mitigation("Please make sure the path is correct file system (hdfs, s3a, etc) path.").toString(),
                    exception);
        }
    }

}
