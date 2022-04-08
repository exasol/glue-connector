package com.exasol.glue;

import static com.exasol.glue.Constants.*;

import java.net.URI;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
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
     * Creates a new AWS S3 client.
     *
     * @return a new S3 client
     */
    public S3Client getS3Client() {
        final S3ClientBuilder builder = S3Client.builder() //
                .region(Region.of(getRegion())) //
                .credentialsProvider(getCredentialsProvider()) //
                .endpointOverride(URI.create(getEndpointOverride()));
        if (this.options.hasEnabled(S3_PATH_STYLE_ACCESS)) {
            builder.serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build());
        }
        return builder.build();
    }

    private String getRegion() {
        if (this.options.containsKey(AWS_REGION)) {
            return this.options.get(AWS_REGION);
        } else {
            return "us-east-1";
        }
    }

    private AwsCredentialsProvider getCredentialsProvider() {
        final String awsAccessKeyId = this.options.get(AWS_ACCESS_KEY_ID);
        final String awsSecretAccessKey = this.options.get(AWS_SECRET_ACCESS_KEY);
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(awsAccessKeyId, awsSecretAccessKey));
    }

    private String getEndpointOverride() {
        final String protocol = getProtocol();
        if (this.options.containsKey(S3_ENDPOINT_OVERRIDE)) {
            return protocol + "://s3." + this.options.get(S3_ENDPOINT_OVERRIDE);
        } else {
            return protocol + "://s3.amazonaws.com";
        }
    }

    private String getProtocol() {
        if (!this.options.containsKey(AWS_USE_SSL) || this.options.hasEnabled(AWS_USE_SSL)) {
            return "https";
        } else {
            return "http";
        }
    }

}
