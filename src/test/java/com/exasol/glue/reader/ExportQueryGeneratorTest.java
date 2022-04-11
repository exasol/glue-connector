package com.exasol.glue.reader;

import static com.exasol.glue.Constants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.hamcrest.Matchers.containsString;

import java.util.Map;

import com.exasol.glue.ExasolOptions;

import org.junit.jupiter.api.Test;

class ExportQueryGeneratorTest {
    final ExasolOptions.Builder builder = ExasolOptions.builder().s3Bucket("bucket")
            .withOptionsMap(Map.of(AWS_ACCESS_KEY_ID, "user", AWS_SECRET_ACCESS_KEY, "pass"));

    @Test
    void testGeneratesExportQueryWithTable() {
        final ExasolOptions options = builder.table("table").build();
        final ExportQueryGenerator generator = new ExportQueryGenerator(options);
        final String result = generator.generateExportQuery("a", 2);
        final String expected = "EXPORT (\n" //
                + "SELECT * FROM table\n" //
                + ") INTO CSV\n" //
                + "AT 'https://bucket.s3.amazonaws.com'\n" //
                + "USER 'user' IDENTIFIED BY 'pass'\n" //
                + "FILE 'a/part-001.csv'\n" //
                + "FILE 'a/part-002.csv'\n" //
                + "WITH COLUMN NAMES";
        assertThat(result, equalTo(expected));
    }

    @Test
    void testGeneratesExportQueryWithQuery() {
        final ExasolOptions options = builder.query("SELECT * FROM table").build();
        final ExportQueryGenerator generator = new ExportQueryGenerator(options);
        final String result = generator.generateExportQuery("a", 3);
        final String expected = "EXPORT (\n" //
                + "SELECT * FROM (SELECT * FROM table) A\n" //
                + ") INTO CSV\n" //
                + "AT 'https://bucket.s3.amazonaws.com'\n" //
                + "USER 'user' IDENTIFIED BY 'pass'\n" //
                + "FILE 'a/part-001.csv'\n" //
                + "FILE 'a/part-002.csv'\n" //
                + "FILE 'a/part-003.csv'\n" //
                + "WITH COLUMN NAMES";
        assertThat(result, equalTo(expected));
    }

    @Test
    void testGeneratesExportQueryWithNumberOfPartitions() {
        final ExasolOptions options = builder.table("table").build();
        final ExportQueryGenerator generator = new ExportQueryGenerator(options);
        final String result = generator.generateExportQuery("a", 13);
        assertAll(() -> assertThat(result, containsString("FILE 'a/part-009.csv'")),
                () -> assertThat(result, containsString("FILE 'a/part-010.csv'")),
                () -> assertThat(result, containsString("FILE 'a/part-011.csv'")),
                () -> assertThat(result, containsString("FILE 'a/part-012.csv'")),
                () -> assertThat(result, containsString("FILE 'a/part-013.csv'")));
    }

    @Test
    void testGeneratesExportQueryWithS3EndpointOverride() {
        final Map<String, String> map = Map.of(AWS_ACCESS_KEY_ID, "name", AWS_SECRET_ACCESS_KEY, "key",
                S3_ENDPOINT_OVERRIDE, "localstack.dev:4566");
        final ExasolOptions options = builder.table("table").withOptionsMap(map).build();
        final ExportQueryGenerator generator = new ExportQueryGenerator(options);
        final String result = generator.generateExportQuery("a", 1);
        final String expected = "EXPORT (\n" //
                + "SELECT * FROM table\n" //
                + ") INTO CSV\n" //
                + "AT 'https://bucket.s3.localstack.dev:4566'\n" //
                + "USER 'name' IDENTIFIED BY 'key'\n" //
                + "FILE 'a/part-001.csv'\n" //
                + "WITH COLUMN NAMES";
        assertThat(result, equalTo(expected));
    }

    @Test
    void testGeneratesExportQueryWithS3EndpointOverrideReplacedForCITrue() {
        final Map<String, String> map = Map.of(AWS_ACCESS_KEY_ID, "user", AWS_SECRET_ACCESS_KEY, "pass",
                S3_ENDPOINT_OVERRIDE, "localhost:1777", "exasol-ci", "true");
        final ExasolOptions options = builder.table("table").withOptionsMap(map).build();
        final ExportQueryGenerator generator = new ExportQueryGenerator(options);
        final String result = generator.generateExportQuery("a", 1);
        assertThat(result, containsString("AT 'https://bucket.s3.amazonaws.com:1777'"));
    }

    @Test
    void testGeneratesExportQueryWithS3EndpointOverrideReplacedForCIFalse() {
        final Map<String, String> map = Map.of(AWS_ACCESS_KEY_ID, "user", AWS_SECRET_ACCESS_KEY, "pass",
                S3_ENDPOINT_OVERRIDE, "localhost:1337", "exasol-ci", "false");
        final ExasolOptions options = builder.table("table").withOptionsMap(map).build();
        final ExportQueryGenerator generator = new ExportQueryGenerator(options);
        final String result = generator.generateExportQuery("a", 1);
        assertThat(result, containsString("AT 'https://bucket.s3.localhost:1337'"));
    }

}
