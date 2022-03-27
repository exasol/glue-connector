package com.exasol.glue;

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
    public static final String S3_BUCKET = "s3Bucket";
    public static final List<String> REQUIRED_OPTIONS = List.of(JDBC_URL, USERNAME, PASSWORD);

    private Constants() {
        // prevent instantiation
    }

}
