package com.exasol.glue.query;

/**
 * An interface for Exasol {@code CSV} import or export query generators.
 */
public interface QueryGenerator {

    /**
     * Generates {@code IMPORT} or {@code EXPORT} query.
     *
     * @return generated query
     */
    default String generateQuery() {
        final StringBuilder builder = new StringBuilder();
        builder //
                .append(getHeader()) //
                .append(getIdentifier()) //
                .append(getFiles()) //
                .append(getFooter());
        return builder.toString();
    }

    /**
     * Creates header part of the query.
     *
     * @return header of a query
     */
    public String getHeader();

    /**
     * Creates identifier part of the query.
     *
     * @return identifer of a query
     */
    public String getIdentifier();

    /**
     * Creates files part of the query.
     *
     * @return files of a query
     */
    public String getFiles();

    /**
     * Creates footer part of the query.
     *
     * @return footer of a query
     */
    public String getFooter();

}
