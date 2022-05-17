package com.exasol.glue.query;

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
     * @param options user provided options
     */
    public ExportQueryGenerator(final ExasolOptions options, final String filesPrefix, final int numberOfFiles) {
        super(options);
        this.filesPrefix = filesPrefix;
        this.numberOfFiles = numberOfFiles;
    }

    @Override
    public String getHeader() {
        final StringBuilder builder = new StringBuilder();
        builder.append("EXPORT (\n").append(getSelectFromTableOrQuery()).append("\n) INTO CSV\n");
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

    private String getSelectFromTableOrQuery() {
        if (this.options.hasTable()) {
            return "SELECT * FROM " + this.options.getTable();
        } else {
            return "SELECT * FROM (" + this.options.getQuery() + ") A";
        }
    }

}
