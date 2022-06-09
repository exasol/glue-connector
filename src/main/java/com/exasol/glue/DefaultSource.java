package com.exasol.glue;

import static com.exasol.glue.Constants.*;
import static java.sql.ResultSetMetaData.columnNoNulls;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

import com.exasol.errorreporting.ExaError;
import com.exasol.glue.connection.ExasolConnectionException;
import com.exasol.glue.connection.ExasolConnectionFactory;
import com.exasol.sql.StatementFactory;
import com.exasol.sql.dql.select.Select;
import com.exasol.sql.dql.select.rendering.SelectRenderer;
import com.exasol.sql.rendering.StringRendererConfig;

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
    private static final List<String> REQUIRED_OPTIONS = Arrays.asList(JDBC_URL, USERNAME, PASSWORD);

    @Override
    // [impl->dsn~default-source-infers-schema~1]
    public StructType inferSchema(final CaseInsensitiveStringMap options) {
        LOGGER.fine(() -> "Running schema inference of the default source.");
        validateOptions(options);
        return getSchema(getExasolOptions(options));
    }

    @Override
    public Table getTable(final StructType schema, final Transform[] partitioning, final Map<String, String> map) {
        return new ExasolTable(schema);
    }

    @Override
    public boolean supportsExternalMetadata() {
        return true;
    }

    @Override
    public String shortName() {
        return "exasol";
    }

    private void validateOptions(final CaseInsensitiveStringMap options) {
        LOGGER.finest(() -> "Validating options of the default source.");
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
        try (final Connection connection = new ExasolConnectionFactory(options).getConnection();
                final Statement statement = connection.createStatement()) {
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

    private String generateInferSchemaQuery(final ExasolOptions options) {
        final Select select = StatementFactory.getInstance().select();
        select.all().from().table("<SCHEMA_INFERENCE_TABLE>");
        if (options.hasQuery()) {
            select.limit(1);
        }
        final StringRendererConfig rendererConfig = StringRendererConfig.builder().quoteIdentifiers(true).build();
        final SelectRenderer renderer = new SelectRenderer(rendererConfig);
        select.accept(renderer);
        return renderer.render().replace("\"<SCHEMA_INFERENCE_TABLE>\"", getTableOrQuery(options));
    }

    private String getTableOrQuery(final ExasolOptions options) {
        if (options.hasTable()) {
            return options.getTable();
        } else {
            return "(" + options.getQuery() + ")";
        }
    }

    private StructType getSparkSchema(final ResultSet resultSet) {
        try {
            final ResultSetMetaData metadata = resultSet.getMetaData();
            final int numberOfColumns = metadata.getColumnCount();
            final List<ColumnDescription> columns = new ArrayList<>(numberOfColumns);
            for (int i = 1; i <= numberOfColumns; i++) {
                columns.add(ColumnDescription.builder() //
                        .name(metadata.getColumnLabel(i)) //
                        .type(metadata.getColumnType(i)) //
                        .precision(metadata.getPrecision(i)) //
                        .scale(metadata.getScale(i)) //
                        .isSigned(metadata.isSigned(i)) //
                        .isNullable(metadata.isNullable(i) != columnNoNulls) //
                        .build());

            }
            return new SchemaConverter().convert(columns);
        } catch (final SQLException exception) {
            throw new ExasolConnectionException(
                    ExaError.messageBuilder("E-EGC-5")
                            .message("Could not create Spark schema from provided Exasol SQL query or table name.")
                            .mitigation("Please make sure that Exasol SQL query or table have columns.").toString(),
                    exception);
        }
    }

}
