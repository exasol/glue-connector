package com.exasol.glue;

import java.util.Objects;

import com.exasol.errorreporting.ExaError;

/**
 * A configuration parameters for Exasol AWS Glue connector.
 */
public final class ExasolOptions {
    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final String table;
    private final String query;
    private final String s3Location;

    private ExasolOptions(final Builder builder) {
        this.host = builder.host;
        this.port = builder.port;
        this.username = builder.username;
        this.password = builder.password;
        this.table = builder.table;
        this.query = builder.query;
        this.s3Location = builder.s3Location;
    }

    /**
     * Gets the connection host.
     *
     * @return connection host
     */
    public String getHost() {
        return this.host;
    }

    /**
     * Gets the connection port.
     *
     * @return connection port
     */
    public int getPort() {
        return this.port;
    }

    /**
     * Gets the connection username.
     *
     * @return connection username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Gets the connection password.
     *
     * @return connection password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Checks if an table name parameter is available.
     *
     * @return {@code true} if table parameter is available
     */
    public boolean hasTable() {
        if (this.table == null || this.table.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Gets the table parameter.
     *
     * @return a table
     */
    public String getTable() {
        return this.table;
    }

    /**
     * Checks if a query parameter is available.
     *
     * @return {@code true} if query parameter is available
     */
    public boolean hasQuery() {
        if (this.query == null || this.query.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Gets the query parameter.
     *
     * @return a query
     */
    public String getQuery() {
        return this.query;
    }

    /**
     * Gets the table or query parameter.
     *
     * Both of them would not be set at the same time.
     *
     * @return a table or query
     */
    public String getTableOrQuery() {
        return hasTable() ? this.table : this.query;
    }

    /**
     * Checks if an S3 location parameter is available.
     *
     * @return {@code true} if S3 location is available
     */
    public boolean hasS3Location() {
        if (this.s3Location == null || this.s3Location.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Gets the S3 bucket location.
     *
     * @return an S3 location
     */
    public String getS3Location() {
        return this.s3Location;
    }

    /**
     * Creates a new builder for {@link ExasolOptions}.
     *
     * @return builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ExasolOptions)) {
            return false;
        }
        final ExasolOptions options = (ExasolOptions) other;
        return Objects.equals(this.host, options.host) && (this.port == options.port) //
                && Objects.equals(this.username, options.username) //
                && Objects.equals(this.password, options.password) //
                && Objects.equals(this.table, options.table) //
                && Objects.equals(this.query, options.query) //
                && Objects.equals(this.s3Location, options.s3Location); //
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.host, this.port, this.username, this.password, this.table, this.query,
                this.s3Location);
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder("ExasolOptions{");
        stringBuilder.append("host=\"").append(this.host) //
                .append("\", port=\"").append(this.port) //
                .append("\", username=\"").append(this.username) //
                .append("\", password=\"*******\"");
        if (this.hasS3Location()) {
            stringBuilder.append("s3Location=\"").append(this.s3Location).append("\"");
        }
        if (this.hasTable()) {
            stringBuilder.append("table=\"").append(this.table).append("\"");
        }
        if (this.hasQuery()) {
            stringBuilder.append("query=\"").append(this.query).append("\"");
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    /**
     * Builder for {@link ExasolOptions}.
     */
    public static class Builder {
        private String host = "localhost";
        private int port = 8563;
        private String username = "sys";
        private String password = "exasol";
        private String table = null;
        private String query = null;
        private String s3Location = null;

        /**
         * Sets the connection host.
         *
         * @param host connection host
         * @return builder instance for fluent programming
         */
        public Builder host(final String host) {
            this.host = host;
            return this;
        }

        /**
         * Sets the connection port.
         *
         * @param port connection port
         * @return builder instance for fluent programming
         */
        public Builder port(final int port) {
            this.port = port;
            return this;
        }

        /**
         * Sets the connection username.
         *
         * @param username connection username
         * @return builder instance for fluent programming
         */
        public Builder username(final String username) {
            this.username = username;
            return this;
        }

        /**
         * Sets the connection password.
         *
         * @param password connection password
         * @return builder instance for fluent programming
         */
        public Builder password(final String password) {
            this.password = password;
            return this;
        }

        /**
         * Sets the user provided table name option.
         *
         * @param table for querying or writing
         * @return builder instance for fluent programming
         */
        public Builder table(final String table) {
            this.table = table;
            return this;
        }

        /**
         * Sets the user provided query string.
         *
         * @param query string for reading
         * @return builder instance for fluent programming
         */
        public Builder query(final String query) {
            this.query = query;
            return this;
        }

        /**
         * Sets the S3 bucket location.
         *
         * @param s3Location S3 bucket location
         * @return builder instance for fluent programming
         */
        public Builder s3Location(final String s3Location) {
            this.s3Location = s3Location;
            return this;
        }

        /**
         * Builds a new instance of {@link ExasolOptions}.
         *
         * @return new instance of {@link ExasolOptins}
         */
        public ExasolOptions build() {
            validate();
            return new ExasolOptions(this);
        }

        private void validate() {
            if (this.table != null && this.query != null) {
                throw new IllegalArgumentException(ExaError.messageBuilder("E-EGC-7")
                        .message("It is not possible to set both 'query' and 'table' options.")
                        .mitigation("Please set only one of the them.").toString());
            }

        }

    }

}
