package com.exasol.glue.ittests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.exasol.glue.filesystem.S3FileSystem;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Tag("integration")
@Testcontainers
class S3FileSystemIT extends BaseIntegrationTestSetup {
    private static final S3FileSystem s3FileSystem = new S3FileSystem(s3Client);
    private static final String bucketName = "s3testbucket";

    @Test
    void testDoesBucketExistsFalse() {
        assertThat(s3FileSystem.doesBucketExist("non_existing_bucket_name"), equalTo(false));
    }

    @Test
    void testDoesBucketExistsTrue() {
        assertThat(s3FileSystem.doesBucketExist(DEFAULT_BUCKET_NAME), equalTo(true));
    }

    @Test
    void testDeleteBucket() {
        createBucket(bucketName);
        createEmptyFile(bucketName, "testFile.txt");
        s3FileSystem.deleteBucket(bucketName);
        assertThat(isBucketEmpty(bucketName), equalTo(true));
    }

    @Test
    void testDeleteBucketKey() {
        createBucket(bucketName);
        createEmptyFile(bucketName, "bucketkey/testFile01.txt");
        createEmptyFile(bucketName, "bucketkey/testFile02.txt");
        s3FileSystem.deleteBucketKey(bucketName, "bucketkey");
        assertThat(isBucketEmpty(bucketName), equalTo(true));
    }

    @Test
    void testDeleteNestedFolders() {
        createBucket(bucketName);
        createEmptyFile(bucketName, "bucketkey/another/testFile01.txt");
        createEmptyFile(bucketName, "bucketkey/another/testFile02.txt");
        s3FileSystem.deleteBucketKey(bucketName, "bucketkey");
        assertThat(isBucketEmpty(bucketName), equalTo(true));
    }

    private void createEmptyFile(final String bucketName, final String fileName) {
        s3Client.putObject(PutObjectRequest.builder().bucket(bucketName).key(fileName).build(),
                RequestBody.fromBytes(new byte[0]));
    }

}
