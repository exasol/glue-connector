package com.exasol.glue.reader;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A class that runs an Exasol {@code EXPORT} query.
 */
public final class ExportQueryRunner {
    private final Connection connection;

    /**
     * Creates a new instance of {@link ExportQueryRunner}.
     *
     * @param connection an JDBC connection object
     */
    public ExportQueryRunner(final Connection connection) {
        this.connection = connection;
    }

    /**
     * Runs the given export query.
     *
     * @param query an export query string
     * @return the number of rows exported
     * @throws SQLException if the query run fails
     */
    public int runExportQuery(final String query) throws SQLException {
        try (final Statement stmt = this.connection.createStatement()) {
            return stmt.executeUpdate(query);
        }
    }
}
