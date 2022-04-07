package com.exasol.glue.reader;

import static com.exasol.glue.Constants.S3_ENDPOINT_OVERRIDE;

import com.exasol.glue.ExasolOptions;

/**
 * A class that generates an Exasol export query.
 */
public final class ExportQueryGenerator {
    private final ExasolOptions options;

    /**
     * Creates a new instance of {@link ExportQueryGenerator}.
     *
     * @param options an user provided options
     */
    public ExportQueryGenerator(final ExasolOptions options) {
        this.options = options;
    }

    /**
     * Generates an Exasol CSV export query.
     *
     * @param bucketFolder  a folder in the S3 bucket location
     * @param numberOfFiles a number of files to export the data
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
        return "AT 'https://" + this.options.getS3Bucket() + ".s3." + getS3Endpoint() + "'\n"
                + "USER 'user' IDENTIFIED BY 'pass'\n";
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
        for (int i = 1; i <= numberOfFiles; i++) {
            builder.append(prefix).append("part-00").append(i).append(".csv'\n");
        }
        return builder.toString();
    }

    private String getFooterPart() {
        return "WITH COLUMN NAMES";
    }
}
