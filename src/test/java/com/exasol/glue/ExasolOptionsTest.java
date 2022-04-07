package com.exasol.glue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.Test;
import nl.jqno.equalsverifier.EqualsVerifier;

class ExasolOptionsTest {

    @Test
    void testWithDefaults() {
        final ExasolOptions options = ExasolOptions.builder().build();
        assertAll(() -> assertThat(options.getJdbcUrl(), equalTo("jdbc:exa:localhost:8563")),
                () -> assertThat(options.getUsername(), equalTo("sys")),
                () -> assertThat(options.getPassword(), equalTo("exasol")));
    }

    @Test
    void testHasS3BucketFalse() {
        assertAll(() -> assertThat(ExasolOptions.builder().build().hasS3Bucket(), equalTo(false)),
                () -> assertThat(ExasolOptions.builder().s3Bucket("").build().hasS3Bucket(), equalTo(false)));
    }

    @Test
    void testHasS3BucketTrue() {
        assertThat(ExasolOptions.builder().s3Bucket("s3").build().hasS3Bucket(), equalTo(true));
    }

    @Test
    void testGetS3Bucket() {
        final ExasolOptions options = ExasolOptions.builder().s3Bucket("bucket").build();
        assertThat(options.getS3Bucket(), equalTo("bucket"));
    }

    @Test
    void testGetJDBCUrl() {
        final ExasolOptions options = ExasolOptions.builder().jdbcUrl("jdbc:exa:127.0.0.1:6666").build();
        assertThat(options.getJdbcUrl(), equalTo("jdbc:exa:127.0.0.1:6666"));
    }

    @Test
    void testUsername() {
        final ExasolOptions options = ExasolOptions.builder().username("user").build();
        assertThat(options.getUsername(), equalTo("user"));
    }

    @Test
    void testPassword() {
        final ExasolOptions options = ExasolOptions.builder().password("pass").build();
        assertThat(options.getPassword(), equalTo("pass"));
    }

    @Test
    void testHasTableFalse() {
        assertAll(() -> assertThat(ExasolOptions.builder().build().hasTable(), equalTo(false)),
                () -> assertThat(ExasolOptions.builder().table("").build().hasTable(), equalTo(false)));
    }

    @Test
    void testHasTableTrue() {
        assertThat(ExasolOptions.builder().table("table").build().hasTable(), equalTo(true));
    }

    @Test
    void testGetTable() {
        assertThat(ExasolOptions.builder().table("table").build().getTable(), equalTo("table"));
    }

    @Test
    void testHasQueryFalse() {
        assertAll(() -> assertThat(ExasolOptions.builder().build().hasQuery(), equalTo(false)),
                () -> assertThat(ExasolOptions.builder().query("").build().hasQuery(), equalTo(false)));
    }

    @Test
    void testHasQueryTrue() {
        assertThat(ExasolOptions.builder().query("query").build().hasQuery(), equalTo(true));
    }

    @Test
    void testGetQuery() {
        assertThat(ExasolOptions.builder().query("query").build().getQuery(), equalTo("query"));
    }

    @Test
    void testGetTableOrQuery() {
        assertAll(() -> assertThat(ExasolOptions.builder().table("table").build().getTableOrQuery(), equalTo("table")),
                () -> assertThat(ExasolOptions.builder().query("query").build().getTableOrQuery(), equalTo("query")));
    }

    @Test
    void testValidatesOnlyTableOrQueryExists() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ExasolOptions.builder().table("table").query("query").build());
        assertThat(exception.getMessage(), startsWith("E-EGC-7"));
    }

    @Test
    void testContainsKeyWithEmptyMap() {
        final ExasolOptions options = ExasolOptions.builder().build();
        assertThat(options.containsKey("aKey"), equalTo(false));
    }

    @Test
    void testContainsKeyWithMap() {
        final ExasolOptions options = ExasolOptions.builder()
                .withOptionsMap(Map.of("awsRegion", "us-east-1", "count", "3")).build();
        assertAll(() -> assertThat(options.containsKey("awsRegion"), equalTo(true)),
                () -> assertThat(options.containsKey("count"), equalTo(true)),
                () -> assertThat(options.containsKey("test"), equalTo(false)));
    }

    @Test
    void testGetEmptyMap() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ExasolOptions.builder().build().get("key"));
        assertThat(exception.getMessage(), startsWith("E-EGC-17"));
    }

    @Test
    void testGetWithOptionsMap() {
        assertThat(ExasolOptions.builder().withOptionsMap(Map.of("key", "value")).build().get("key"), equalTo("value"));
    }

    @Test
    void testDuplicateKeyValuesThrows() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ExasolOptions.builder().withOptionsMap(Map.of("k1", "v1", "K1", "v2")).build());
        assertThat(exception.getMessage(), startsWith("E-EGC-18"));
    }

    @Test
    void testObeysCaseInsensitivity() {
        final ExasolOptions options = ExasolOptions.builder().withOptionsMap(Map.of("key", "value")).build();
        assertAll(() -> assertThat(options.containsKey("KEY"), equalTo(true)),
                () -> assertThat(options.get("keY"), equalTo("value")));
    }

    @Test
    void testHasEnabled() {
        final ExasolOptions options = ExasolOptions.builder()
                .withOptionsMap(Map.of("enabled", "true", "notenabled", "false", "key", "10")).build();
        assertAll(() -> assertThat(options.hasEnabled("enabled"), equalTo(true)),
                () -> assertThat(options.hasEnabled("notenabled"), equalTo(false)),
                () -> assertThat(options.hasEnabled("key"), equalTo(false)),
                () -> assertThat(options.hasEnabled("non-existing-key"), equalTo(false)));
    }

    @Test
    void testToStringWithTable() {
        final ExasolOptions options = ExasolOptions.builder().table("table").build();
        final String expected = "ExasolOptions{"
                + "jdbcUrl=\"jdbc:exa:localhost:8563\", username=\"sys\", password=\"*******\", table=\"table\"}";
        assertThat(options.toString(), equalTo(expected));
    }

    @Test
    void testToStringWithQuery() {
        final ExasolOptions options = ExasolOptions.builder().query("query").build();
        final String expected = "ExasolOptions{jdbcUrl=\"jdbc:exa:localhost:8563\", username=\"sys\", "
                + "password=\"*******\", query=\"query\"}";
        assertThat(options.toString(), equalTo(expected));
    }

    @Test
    void testToStringWithS3Bucket() {
        final ExasolOptions options = ExasolOptions.builder().table("table").s3Bucket("s3").build();
        final String expected = "ExasolOptions{"
                + "jdbcUrl=\"jdbc:exa:localhost:8563\", username=\"sys\", password=\"*******\", "
                + "s3Bucket=\"s3\", table=\"table\"}";
        assertThat(options.toString(), equalTo(expected));
    }

    @Test
    void testToStringWithOptionsMap() {
        final ExasolOptions options = ExasolOptions.builder().table("table")
                .withOptionsMap(Map.of("k1", "v1", "k2", "10")).build();
        final String expected = "ExasolOptions{"
                + "jdbcUrl=\"jdbc:exa:localhost:8563\", username=\"sys\", password=\"*******\", "
                + "table=\"table\", map=\"{k1=v1, k2=10}\"}";
        assertThat(options.toString(), equalTo(expected));
    }

    @Test
    void testEqualsAndHashMethods() {
        EqualsVerifier.forClass(ExasolOptions.class).verify();
    }

}
