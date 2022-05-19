package com.exasol.glue.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.exasol.errorreporting.ExaError;
import com.exasol.glue.ExasolOptions;
import com.exasol.glue.S3ClientFactory;
import com.exasol.glue.connection.ExasolConnectionException;

import org.apache.spark.scheduler.SparkListener;
import org.apache.spark.scheduler.SparkListenerJobEnd;

import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

/**
 * A {@link SparkListener} class that cleans up intermediate data at the end of job run.
 */
public class ExasolJobEndListener extends SparkListener {
    private static final Logger LOGGER = Logger.getLogger(ExasolJobEndListener.class.getName());
    private final ExasolOptions options;
    private final String bucketKey;

    /**
     * Creates an instance of {@link ExasolJobEndListener}.
     *
     * @param options   a user provided options
     * @param bucketKey a folder inside the user provided bucket
     */
    public ExasolJobEndListener(final ExasolOptions options, final String bucketKey) {
        this.options = options;
        this.bucketKey = bucketKey;
    }

    @Override
    public void onJobEnd(final SparkListenerJobEnd jobEnd) {
        LOGGER.info(() -> "Cleaning up the bucket '" + this.options.getS3Bucket() + "' with key '" + this.bucketKey
                + "' in job '" + jobEnd.jobId() + "'.");
        deleteObjects();
        super.onJobEnd(jobEnd);
    }

    private void deleteObjects() {
        final String bucketName = this.options.getS3Bucket();
        final S3ClientFactory s3ClientFactory = new S3ClientFactory(options);
        try (final S3Client s3Client = s3ClientFactory.getS3Client()) {
            final List<S3Object> objects = listObject(s3Client, bucketName, this.bucketKey);
            List<ObjectIdentifier> objectIds = objects.stream() //
                    .map(object -> ObjectIdentifier.builder().key(object.key()).build()) //
                    .collect(Collectors.toList());
            if (objectIds.isEmpty()) {
                return;
            }
            DeleteObjectsRequest deleteObjectsRequest = DeleteObjectsRequest.builder() //
                    .bucket(bucketName) //
                    .delete(Delete.builder().objects(objectIds).build()) //
                    .build();
            s3Client.deleteObjects(deleteObjectsRequest);
        } catch (final SdkClientException exception) {
            throw new ExasolConnectionException(
                    ExaError.messageBuilder("E-EGC-19")
                            .message("Failed to delete objects in {{BUCKET}} with key {{KEY}}.", bucketName,
                                    this.bucketKey)
                            .mitigation("Please check that credentials and bucket name are correct.").toString(),
                    exception);
        } catch (final S3Exception exception) {
            throw new ExasolConnectionException(ExaError.messageBuilder("E-EGC-20")
                    .message("Failed to delete objects in {{BUCKET}} with key {{KEY}} because of unknown S3 exception.")
                    .parameter("BUCKET", bucketName).parameter("KEY", this.bucketKey).ticketMitigation().toString(),
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

}
