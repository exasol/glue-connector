package com.exasol.glue.query;

import java.util.Collections;
import java.util.Optional;

import com.exasol.glue.ExasolOptions;

/**
 * A class that generates an Exasol {@code EXPORT} query.
 */
public final class ExportQueryGenerator extends AbstractQueryGenerator {
    private static final String EXPORT_QUERY_FOOTER = "WITH COLUMN NAMES";
    private String filesPrefix;
    private int numberOfFiles;

    /**
     * Creates a new instance of {@link ExportQueryGenerator}.
     *
     * @param options       user provided options
     * @param filesPrefix   prefix string that indicate folder inside a bucket
     * @param numberOfFiles the number of files to export data into
     */
    public ExportQueryGenerator(final ExasolOptions options, final String filesPrefix, final int numberOfFiles) {
        super(options);
        this.filesPrefix = filesPrefix;
        this.numberOfFiles = numberOfFiles;
    }

    /**
     * Generates an {@code EXPORT} query using the user provided base query.
     *
     * @param baseQuery base query to use for export
     * @return generated export query
     */
    public String generateQuery(final String baseQuery) {
        final StringBuilder builder = new StringBuilder();
        builder //
                .append("EXPORT (\n" + baseQuery + "\n) INTO CSV\n") //
                .append(getIdentifier()) //
                .append(getFiles()) //
                .append(getFooter());
        return builder.toString();
    }

    @Override
    public String getHeader() {
        final StringBuilder builder = new StringBuilder();
        builder.append("EXPORT (\n").append(getSelectQuery()).append("\n) INTO CSV\n");
        return builder.toString();
    }

    @Override
    public String getFiles() {
        final StringBuilder builder = new StringBuilder();
        final String prefix = "FILE '" + this.filesPrefix + "/";
        for (int fileIndex = 1; fileIndex <= this.numberOfFiles; fileIndex++) {
            builder.append(prefix).append(String.format("part-%03d", fileIndex)).append(".csv'\n");
        }
        return builder.toString();
    }

    @Override
    public String getFooter() {
        return EXPORT_QUERY_FOOTER;
    }

    private String getSelectQuery() {
        return new SelectStatementGenerator().getSelectStatement(getSelectFromTableOrQuery(), Collections.emptyList(),
                Optional.empty());
    }

    private String getSelectFromTableOrQuery() {
        if (this.options.hasTable()) {
            return this.options.getTable();
        } else {
            return "(" + this.options.getQuery() + ")";
        }
    }

}
