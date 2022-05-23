package com.exasol.glue.query;

import static com.exasol.glue.Constants.INTERMEDIATE_DATA_PATH;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.exasol.errorreporting.ExaError;
import com.exasol.glue.ExasolOptions;
import com.exasol.glue.ExasolValidationException;
import com.exasol.glue.S3ClientFactory;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Object;

/**
 * A class that generates an Exasol {@code IMPORT} query.
 */
public final class ImportQueryGenerator extends AbstractQueryGenerator {
    private static final String IMPORT_QUERY_FOOTER = "SKIP = 1";

    /**
     * Creates a new instance of {@link ImportQueryGenerator}.
     *
     * @param options user provided options
     */
    public ImportQueryGenerator(final ExasolOptions options) {
        super(options);
    }

    @Override
    public String getHeader() {
        final String table = this.options.getTable();
        final StringBuilder builder = new StringBuilder();
        builder.append("IMPORT INTO ").append(table).append(" FROM CSV\n");
        return builder.toString();
    }

    @Override
    public String getFiles() {
        final String path = this.options.get(INTERMEDIATE_DATA_PATH);
        final URI pathURI = getPathURI(path);
        final String bucketName = pathURI.getHost();
        final String bucketKey = pathURI.getPath().substring(1);
        final S3ClientFactory s3ClientFactory = new S3ClientFactory(this.options);
        try (final S3Client s3Client = s3ClientFactory.getS3Client()) {
            final List<S3Object> objects = listObject(s3Client, bucketName, bucketKey);
            final StringBuilder builder = new StringBuilder();
            for (final S3Object object : objects) {
                builder.append("FILE '").append(object.key()).append("'\n");
            }
            return builder.toString();
        }
    }

    private URI getPathURI(final String path) {
        try {
            return new URI(path);
        } catch (final URISyntaxException exception) {
            throw new ExasolValidationException(ExaError.messageBuilder("E-EGC-18")
                    .message("Provided path {{path}} cannot be converted to URI systax.", path)
                    .mitigation("Please make sure the path is correct file system (hdfs, s3a, etc) path.").toString(),
                    exception);
        }
    }

    private List<S3Object> listObject(final S3Client s3Client, final String bucketName, final String bucketKey) {
        final ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder().bucket(bucketName)
                .prefix(bucketKey).build();
        final List<S3Object> result = new ArrayList<>();
        for (final ListObjectsV2Response page : s3Client.listObjectsV2Paginator(listObjectsRequest)) {
            for (final S3Object s3Object : page.contents()) {
                result.add(s3Object);
            }
        }
        return result;
    }

    @Override
    public String getFooter() {
        return IMPORT_QUERY_FOOTER;
    }

}
