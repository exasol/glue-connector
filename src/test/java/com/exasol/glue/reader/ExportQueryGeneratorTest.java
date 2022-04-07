package com.exasol.glue.reader;

import static com.exasol.glue.Constants.S3_ENDPOINT_OVERRIDE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.containsString;

import java.util.Map;

import com.exasol.glue.ExasolOptions;

import org.junit.jupiter.api.Test;

class ExportQueryGeneratorTest {
    final ExasolOptions.Builder builder = ExasolOptions.builder().s3Bucket("bucket");

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
    void testGeneratesExportQueryWithS3EndpointOverride() {
        final ExasolOptions options = builder.table("table")
                .withOptionsMap(Map.of(S3_ENDPOINT_OVERRIDE, "localstack.dev:4566")).build();
        final ExportQueryGenerator generator = new ExportQueryGenerator(options);
        final String result = generator.generateExportQuery("a", 1);
        final String expected = "EXPORT (\n" //
                + "SELECT * FROM table\n" //
                + ") INTO CSV\n" //
                + "AT 'https://bucket.s3.localstack.dev:4566'\n" //
                + "USER 'user' IDENTIFIED BY 'pass'\n" //
                + "FILE 'a/part-001.csv'\n" //
                + "WITH COLUMN NAMES";
        assertThat(result, equalTo(expected));
    }

    @Test
    void testGeneratesExportQueryWithS3EndpointOverrideReplacedForCITrue() {
        final ExasolOptions options = builder.table("table")
                .withOptionsMap(Map.of(S3_ENDPOINT_OVERRIDE, "localhost:1777", "exasol-ci", "true")).build();
        final ExportQueryGenerator generator = new ExportQueryGenerator(options);
        final String result = generator.generateExportQuery("a", 1);
        assertThat(result, containsString("AT 'https://bucket.s3.amazonaws.com:1777'"));
    }

    @Test
    void testGeneratesExportQueryWithS3EndpointOverrideReplacedForCIFalse() {
        final ExasolOptions options = builder.table("table")
                .withOptionsMap(Map.of(S3_ENDPOINT_OVERRIDE, "localhost:1337", "exasol-ci", "false")).build();
        final ExportQueryGenerator generator = new ExportQueryGenerator(options);
        final String result = generator.generateExportQuery("a", 1);
        assertThat(result, containsString("AT 'https://bucket.s3.localhost:1337'"));
    }

}
