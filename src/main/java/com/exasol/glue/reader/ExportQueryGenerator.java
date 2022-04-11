package com.exasol.glue.reader;

import static com.exasol.glue.Constants.*;

import com.exasol.glue.ExasolOptions;

/**
 * A class that generates an Exasol export query.
 */
public final class ExportQueryGenerator {
    private final ExasolOptions options;

    /**
     * Creates a new instance of {@link ExportQueryGenerator}.
     *
     * @param options user provided options
     */
    public ExportQueryGenerator(final ExasolOptions options) {
        this.options = options;
    }

    /**
     * Generates an Exasol CSV export query.
     *
     * @param bucketFolder  a folder in the S3 bucket location
     * @param numberOfFiles the number of files the exported data will be splitted into
     * @return generated export query
     */
    public String generateExportQuery(final String bucketFolder, final int numberOfFiles) {
        final StringBuilder builder = new StringBuilder("EXPORT (\n");
        builder //
                .append(getSelectPart()) //
                .append("\n)") //
                .append(" INTO CSV\n") //
                .append(getIdentifiedPart()) //
                .append(getFilesPart(bucketFolder, numberOfFiles)) //
                .append(getFooterPart());
        return builder.toString();
    }

    private String getSelectPart() {
        if (this.options.hasTable()) {
            return "SELECT * FROM " + options.getTable();
        } else {
            return "SELECT * FROM (" + options.getQuery() + ") A";
        }
    }

    private String getIdentifiedPart() {
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
        if (this.options.hasEnabled("exasol-ci")) {
            return endpoint.replaceAll("localhost", "amazonaws.com");
        } else {
            return endpoint;
        }
    }

    private String getFilesPart(final String folder, final int numberOfFiles) {
        final StringBuilder builder = new StringBuilder();
        final String prefix = "FILE '" + folder + "/";
        for (int fileIndex = 1; fileIndex <= numberOfFiles; fileIndex++) {
            builder.append(prefix).append(String.format("part-%03d", fileIndex)).append(".csv'\n");
        }
        return builder.toString();
    }

    private String getFooterPart() {
        return "WITH COLUMN NAMES";
    }
}
