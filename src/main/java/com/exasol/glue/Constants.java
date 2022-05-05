package com.exasol.glue;

import java.util.Arrays;
import java.util.List;

/**
 * A class that contains common constant variables.
 */
public final class Constants {

    public static final String TABLE = "TABLE";
    public static final String QUERY = "QUERY";
    public static final String JDBC_URL = "JDBC_URL";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String AWS_ACCESS_KEY_ID = "awsAccessKeyId";
    public static final String AWS_SECRET_ACCESS_KEY = "awsSecretAccessKey";
    public static final String AWS_SESSION_TOKEN = "awsSessionToken";
    public static final String AWS_REGION = "awsRegion";
    public static final String DEFAULT_AWS_REGION = "us-east-1";
    public static final String AWS_USE_SSL = "useSsl";
    public static final String S3_BUCKET = "s3Bucket";
    public static final String S3_ENDPOINT_OVERRIDE = "awsEndpointOverride";
    public static final String S3_PATH_STYLE_ACCESS = "s3PathStyleAccess";
    public static final String NUMBER_OF_PARTITIONS = "numPartitions";
    public static final int DEFAULT_NUMBER_OF_PARTITIONS = 8;

    public static final List<String> REQUIRED_OPTIONS = Arrays.asList(JDBC_URL, USERNAME, PASSWORD);

    private Constants() {
        // prevent instantiation
    }

}
