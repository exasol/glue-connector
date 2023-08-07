package com.exasol.glue.listener;

import java.util.logging.Logger;

import org.apache.spark.scheduler.SparkListener;
import org.apache.spark.scheduler.SparkListenerJobEnd;

import com.exasol.glue.filesystem.S3FileSystem;
import com.exasol.spark.common.ExasolOptions;

/**
 * A {@link SparkListener} class that cleans up intermediate data at the end of job run.
 */
public final class ExasolJobEndCleanupListener extends SparkListener {
    private static final Logger LOGGER = Logger.getLogger(ExasolJobEndCleanupListener.class.getName());
    private final ExasolOptions options;
    private final String bucketKey;

    /**
     * Creates an instance of {@link ExasolJobEndCleanupListener}.
     *
     * @param options   user provided options
     * @param bucketKey bucketKey inside the user provided bucket
     */
    public ExasolJobEndCleanupListener(final ExasolOptions options, final String bucketKey) {
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
        try (final S3FileSystem s3FileSystem = new S3FileSystem(this.options)) {
            s3FileSystem.deleteKeys(this.options.getS3Bucket(), this.bucketKey);
        }
    }

}
