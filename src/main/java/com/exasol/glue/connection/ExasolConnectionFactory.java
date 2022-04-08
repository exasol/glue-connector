package com.exasol.glue.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.exasol.glue.ExasolOptions;
import com.exasol.errorreporting.ExaError;

/**
 * A factory that creates JDBC connection to Exasol database.
 */
public final class ExasolConnectionFactory {
    private static final Logger LOGGER = Logger.getLogger(ExasolConnectionFactory.class.getName());
    private final ExasolOptions options;
    private Connection connection = null;

    /**
     * Creates an instance of a {@link ExasolConnectionFactory}.
     *
     * @param options an {@link ExasolOptions} options
     */
    public ExasolConnectionFactory(final ExasolOptions options) {
        this.options = options;
    }

    /**
     * Creates a JDBC connection to an Exasol database if none exists yet.
     *
     * @return JDBC connection
     * @throws SQLException if the connection cannot be established
     */
    public synchronized Connection getConnection() throws SQLException {
        if (this.connection == null) {
            this.connection = createConnection();
        }
        return this.connection;
    }

    private Connection createConnection() {
        verifyExasolJDBCDriverAvailable();
        final String address = options.getJdbcUrl();
        final String username = options.getUsername();
        LOGGER.fine(() -> "Getting connection at '" + address + "' with username '" + username + "' and password.");
        try {
            final long start = System.currentTimeMillis();
            final Connection connection = DriverManager.getConnection(address, username, options.getPassword());
            final long connectionTime = System.currentTimeMillis() - start;
            LOGGER.info(() -> "Obtained connection to '" + address + "' in '" + connectionTime + "' milliseconds.");
            return connection;
        } catch (final SQLException exception) {
            throw new ExasolConnectionException(ExaError.messageBuilder("E-EGC-5")
                    .message("Could not connect to Exasol address on {{address}} with username {{username}}.")
                    .parameter("address", address).parameter("username", username)
                    .mitigation("Please check that connection address, username and password are correct.").toString(),
                    exception);
        }
    }

    private void verifyExasolJDBCDriverAvailable() {
        final String driverClassName = "com.exasol.jdbc.EXADriver";
        try {
            Class.forName(driverClassName);
        } catch (final ClassNotFoundException exception) {
            throw new ExasolConnectionException(
                    ExaError.messageBuilder("E-EGC-11")
                            .message("Failed to find Exasol JDBC Driver class {{class}}.", driverClassName)
                            .mitigation("Please make sure that Exasol JDBC Driver is installed.").toString(),
                    exception);
        }
    }

}