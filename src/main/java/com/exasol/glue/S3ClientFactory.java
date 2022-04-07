package com.exasol.glue;

import static com.exasol.glue.Constants.S3_ENDPOINT_OVERRIDE;

import java.net.URI;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;

/**
 * A factory class that creates S3 clients.
 */
public final class S3ClientFactory {
    private final ExasolOptions options;

    /**
     * Creates a new instance of {@link S3ClientFactory}.
     *
     * @param options an {@link ExasolOptions} options
     */
    public S3ClientFactory(final ExasolOptions options) {
        this.options = options;
    }

    /**
     * Returns an AWS S3 client.
     *
     * @return an S3 client
     */
    public S3Client getS3Client() {
        return S3Client //
                .builder() //
                .endpointOverride(URI.create(getEndpointOverride())) //
                .serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build()) //
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create("user", "pass"))) //
                .build();
    }

    private String getEndpointOverride() {
        if (this.options.containsKey(S3_ENDPOINT_OVERRIDE)) {
            return "http://s3." + this.options.get(S3_ENDPOINT_OVERRIDE);
        } else {
            return "https://s3.amazonaws.com";
        }
    }

}
