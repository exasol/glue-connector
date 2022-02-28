package com.exasol.glue;

import java.util.List;

public final class Constants {

    public static final int DEFAULT_PORT = 8563;

    public static final String TABLE = "TABLE";
    public static final String QUERY = "QUERY";
    public static final String HOST = "HOST";
    public static final String PORT = "PORT";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String S3_LOCATION = "S3_LOCATION";
    public static final List<String> REQUIRED_OPTIONS = List.of(HOST, PORT, USERNAME, PASSWORD);

    private Constants() {
        // prevent instantiation
    }

}
