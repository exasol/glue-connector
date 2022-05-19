package com.exasol.glue.query;

import static com.exasol.glue.Constants.*;

import com.exasol.glue.ExasolOptions;

/**
 * An common {@code CSV} query generator class.
 *
 * It generator Exasol import or export queries that access {@code CSV} files in AWS S3 bucket.
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
        final String awsAccessKeyId = this.options.get(AWS_ACCESS_KEY_ID);
        final String awsSecretAccessKey = this.options.get(AWS_SECRET_ACCESS_KEY);
        return "AT 'https://" + this.options.getS3Bucket() + ".s3." + getS3Endpoint() + "'\n" + "USER '"
                + awsAccessKeyId + "' IDENTIFIED BY '" + awsSecretAccessKey + "'\n";
    }

    private String getS3Endpoint() {
        String endpointOverride = "amazonaws.com";
        if (this.options.containsKey(S3_ENDPOINT_OVERRIDE)) {
            endpointOverride = this.options.get(S3_ENDPOINT_OVERRIDE);
            endpointOverride = replaceInCITests(endpointOverride);
        }
        return endpointOverride;
    }

    private String replaceInCITests(final String endpoint) {
        if (this.options.hasEnabled(CI_ENABLED)) {
            return endpoint.replace("localhost", "amazonaws.com");
        } else {
            return endpoint;
        }
    }

}
