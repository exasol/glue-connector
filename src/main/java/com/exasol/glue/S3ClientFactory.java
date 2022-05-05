package com.exasol.glue;

import static com.exasol.glue.Constants.*;

import java.net.URI;

import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.*;

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
     * Creates a default AWS S3 client.
     *
     * This client will be used inside the AWS Glue service that uses default credencials chain and region.
     *
     * @return a new S3 client
     */
    public S3Client getDefaultS3Client() {
        final S3ClientBuilder builder = S3Client.builder() //
                .credentialsProvider(DefaultCredentialsProvider.create());
        setPathStyleAccessIfEnabled(builder);
        setEndpointOverrideIfEnabled(builder);
        setRegionInCI(builder);
        return builder.build();
    }

    private void setRegionInCI(final S3BaseClientBuilder<?, ?> builder) {
        if (this.options.hasEnabled(CI_ENABLED)) {
            builder.region(Region.of(getRegion()));
        }
    }

    /**
     * Creates a new AWS S3 client.
     *
     * This is client is used outside of the AWS services that uses user provided credentials and S3 region.
     *
     * @return a new S3 client
     */
    public S3Client getS3Client() {
        final S3ClientBuilder builder = S3Client.builder() //
                .region(Region.of(getRegion())) //
                .credentialsProvider(getCredentialsProvider());
        setPathStyleAccessIfEnabled(builder);
        setEndpointOverrideIfEnabled(builder);
        return builder.build();
    }

    private String getRegion() {
        if (this.options.containsKey(AWS_REGION)) {
            return this.options.get(AWS_REGION);
        } else {
            return DEFAULT_AWS_REGION;
        }
    }

    private AwsCredentialsProvider getCredentialsProvider() {
        final String awsAccessKeyId = this.options.get(AWS_ACCESS_KEY_ID);
        final String awsSecretAccessKey = this.options.get(AWS_SECRET_ACCESS_KEY);
        return StaticCredentialsProvider.create(AwsBasicCredentials.create(awsAccessKeyId, awsSecretAccessKey));
    }

    private void setPathStyleAccessIfEnabled(final S3BaseClientBuilder<?, ?> builder) {
        if (this.options.hasEnabled(S3_PATH_STYLE_ACCESS)) {
            builder.serviceConfiguration(S3Configuration.builder().pathStyleAccessEnabled(true).build());
        }
    }

    private void setEndpointOverrideIfEnabled(final S3BaseClientBuilder<?, ?> builder) {
        if (this.options.containsKey(S3_ENDPOINT_OVERRIDE)) {
            builder.endpointOverride(URI.create(getEndpointOverride()));
        }
    }

    private String getEndpointOverride() {
        final String protocol = getProtocol();
        return protocol + "://s3." + this.options.get(S3_ENDPOINT_OVERRIDE);
    }

    private String getProtocol() {
        if (!this.options.containsKey(AWS_USE_SSL) || this.options.hasEnabled(AWS_USE_SSL)) {
            return "https";
        } else {
            return "http";
        }
    }

}
