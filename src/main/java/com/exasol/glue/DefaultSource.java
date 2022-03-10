package com.exasol.glue;

import static java.sql.ResultSetMetaData.columnNoNulls;
import static com.exasol.glue.Constants.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.exasol.errorreporting.ExaError;

import org.apache.spark.sql.connector.catalog.Table;
import org.apache.spark.sql.connector.catalog.TableProvider;
import org.apache.spark.sql.connector.expressions.Transform;
import org.apache.spark.sql.sources.DataSourceRegister;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.util.CaseInsensitiveStringMap;

/**
 * An entry class for the connector.
 */
public class DefaultSource implements TableProvider, DataSourceRegister {
    private static final Logger LOGGER = Logger.getLogger(DefaultSource.class.getName());

    @Override
    public StructType inferSchema(final CaseInsensitiveStringMap options) {
        LOGGER.fine(() -> "Running schema inference of the default source.");
        validateOptions(options);
        final StructType schema = getSchema(getExasolOptions(options));
        return schema;
    }

    @Override
    public Table getTable(final StructType schema, final Transform[] partitioning, final Map<String, String> map) {
        return new ExasolTable(schema);
    }

    @Override
    public String shortName() {
        return "exasol";
    }

    private void validateOptions(final CaseInsensitiveStringMap options) {
        LOGGER.fine(() -> "Validating options of the default source.");
        if (!options.containsKey(TABLE) && !options.containsKey(QUERY)) {
            throw new IllegalArgumentException(
                    ExaError.messageBuilder("E-EGC-1").message("Missing 'query' or 'table' option.")
                            .mitigation("Please provide either one of 'query' or 'table' options.").toString());
        }
        if (options.containsKey(TABLE) && options.containsKey(QUERY)) {
            throw new IllegalArgumentException(
                    ExaError.messageBuilder("E-EGC-2").message("Both 'query' and 'table' options are provided.")
                            .mitigation("Please use only either one of the options.").toString());
        }
        validateRequiredOptions(options);
    }

    private void validateRequiredOptions(CaseInsensitiveStringMap options) {
        for (final String key : REQUIRED_OPTIONS) {
            if (!options.containsKey(key)) {
                throw new IllegalArgumentException(
                        ExaError.messageBuilder("E-EGC-3").message("Required option {{key}} is not found.", key)
                                .mitigation("Please provide a value for the {{key}} option.", key).toString());
            }
        }
    }

    private ExasolOptions getExasolOptions(final CaseInsensitiveStringMap options) {
        final ExasolOptions.Builder builder = ExasolOptions.builder() //
                .jdbcUrl(options.get(JDBC_URL)) //
                .username(options.get(USERNAME)) //
                .password(options.get(PASSWORD));
        if (options.containsKey(TABLE)) {
            builder.table(options.get(TABLE));
        } else if (options.containsKey(QUERY)) {
            builder.query(options.get(QUERY));
        }
        return builder.build();
    }

    private StructType getSchema(final ExasolOptions options) {
        final String limitQuery = generateInferSchemaQuery(options);
        LOGGER.info(() -> "Running schema inference using limited query '" + limitQuery + "' for the default source.");
        try (final Connection connection = getConnection(options)) {
            final Statement statement = connection.createStatement();
            final StructType schema = getSparkSchema(statement.executeQuery(limitQuery));
            LOGGER.info(() -> "Inferred schema as '" + schema.toString() + "' for the default source.");
            return schema;
        } catch (final SQLException exception) {
            throw new ExasolConnectionException(ExaError.messageBuilder("E-EGC-4")
                    .message("Could not run the limit query {{limitQuery}} to infer the schema.", limitQuery)
                    .mitigation("Please check that connection properties and original query are correct.").toString(),
                    exception);
        }
    }

    private Connection getConnection(final ExasolOptions options) {
        if (hasExasolJDBCDriverClass()) {
            final String address = options.getJdbcUrl();
            final String username = options.getUsername();
            LOGGER.fine(() -> "Getting connection at '" + address + "' with username '" + username + "' and password.");
            try {
                return DriverManager.getConnection(address, username, options.getPassword());
            } catch (final SQLException exception) {
                throw new ExasolConnectionException(ExaError.messageBuilder("E-EGC-5")
                        .message("Could not connect to Exasol address on {{address}} with username {{username}}.")
                        .parameter("address", address).parameter("username", username)
                        .mitigation("Please check that connection address, username and password are correct.")
                        .toString(), exception);
            }
        } else {
            return null;
        }
    }

    private boolean hasExasolJDBCDriverClass() {
        final String driverClassName = "com.exasol.jdbc.EXADriver";
        try {
            Class.forName(driverClassName);
            return true;
        } catch (final ClassNotFoundException exception) {
            throw new ExasolConnectionException(
                    ExaError.messageBuilder("E-EGC-11")
                            .message("Failed to find Exasol JDBC Driver class {{class}}.", driverClassName)
                            .mitigation("Please make sure that Exasol JDBC Driver is installed.").toString(),
                    exception);
        }
    }

    private String generateInferSchemaQuery(final ExasolOptions options) {
        if (options.hasTable()) {
            return "SELECT * FROM " + options.getTable();
        } else {
            return "SELECT * FROM (" + options.getQuery() + ") A LIMIT 1";
        }
    }

    private StructType getSparkSchema(final ResultSet resultSet) {
        try {
            final ResultSetMetaData metadata = resultSet.getMetaData();
            final int numberOfColumns = metadata.getColumnCount();
            final List<ColumnDescription> columns = new ArrayList<>(numberOfColumns);
            for (int i = 0; i < numberOfColumns; i++) {
                columns.add(ColumnDescription.builder() //
                        .name(metadata.getColumnLabel(i + 1)) //
                        .type(metadata.getColumnType(i + 1)) //
                        .precision(metadata.getPrecision(i + 1)) //
                        .scale(metadata.getScale(i + 1)) //
                        .isSigned(metadata.isSigned(i + 1)) //
                        .isNullable(metadata.isNullable(i + 1) != columnNoNulls) //
                        .build());

            }
            return new SchemaConverter().convert(columns);
        } catch (final SQLException exception) {
            throw new ExasolConnectionException(ExaError.messageBuilder("E-EGC-6")
                    .message("Could not create Spark schema from provided Exasol SQL query or table name.")
                    .mitigation("Please check make sure that Exasol SQL query or table have columns.").toString(),
                    exception);
        }
    }

}
