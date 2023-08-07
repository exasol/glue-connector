package com.exasol.glue.query;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.exasol.spark.common.ExasolOptions;
import com.exasol.spark.common.Option;

/**
 * An common {@code CSV} query generator class.
 *
 * A generator for Exasol {@code IMPORT} or {@code EXPORT} queries that access {@code CSV} files in AWS S3 bucket.
 *
 * @see <a href="https://docs.exasol.com/db/latest/sql/import.htm">Exasol Import</a>
 * @see <a href="https://docs.exasol.com/db/latest/sql/export.htm">Exasol Export</a>
 */
public abstract class AbstractQueryGenerator implements QueryGenerator {
    /** An {@link ExasolOptions} options. */
    protected final ExasolOptions options;

    /**
     * Creates a new instance of {@link AbstractQueryGenerator}.
     *
     * @param options user provided options
     */
    protected AbstractQueryGenerator(final ExasolOptions options) {
        this.options = options;
    }

    @Override
    public String getIdentifier() {
        final String awsAccessKeyId = this.options.get(Option.AWS_ACCESS_KEY_ID.key());
        final String awsSecretAccessKey = this.options.get(Option.AWS_SECRET_ACCESS_KEY.key());
        return "AT '" + escapeStringLiteral(getBucketURL()) + "'\nUSER '" + escapeStringLiteral(awsAccessKeyId)
                + "' IDENTIFIED BY '" + escapeStringLiteral(awsSecretAccessKey) + "'\n";
    }

    private String escapeStringLiteral(final String input) {
        return input.replace("'", "''");
    }

    /**
     * Removes {@code IDENTIFIED BY} part of the Exasol SQL statement that contains credentials.
     *
     * @param input input query string
     * @return identifier part removed query string
     */
    public static String identifierRemoved(final String input) {
        return Stream.of(input.split("\n")).filter(s -> !s.contains("IDENTIFIED BY")).collect(Collectors.joining("\n"));
    }

    private String getBucketURL() {
        return "https://" + this.options.getS3Bucket() + ".s3." + getS3Endpoint();
    }

    private String getS3Endpoint() {
        if (this.options.containsKey(Option.S3_ENDPOINT_OVERRIDE.key())) {
            return replaceInCITests(this.options.get(Option.S3_ENDPOINT_OVERRIDE.key()));
        } else {
            return "amazonaws.com";
        }
    }

    private String replaceInCITests(final String endpoint) {
        if (this.options.hasEnabled(Option.REPLACE_LOCALHOST_BY_DEFAULT_S3_ENDPOINT.key())) {
            return endpoint.replace("localhost", "amazonaws.com");
        } else {
            return endpoint;
        }
    }

}
