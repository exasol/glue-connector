package com.exasol.glue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void testHasS3LocationFalse() {
        assertAll(() -> assertThat(ExasolOptions.builder().build().hasS3Location(), equalTo(false)),
                () -> assertThat(ExasolOptions.builder().s3Location("").build().hasS3Location(), equalTo(false)));
    }

    @Test
    void testHasS3LocationTrue() {
        assertThat(ExasolOptions.builder().s3Location("s3").build().hasS3Location(), equalTo(true));
    }

    @Test
    void testGetS3Location() {
        final ExasolOptions options = ExasolOptions.builder().s3Location("bucket").build();
        assertThat(options.getS3Location(), equalTo("bucket"));
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
    void testToStringWithTable() {
        final ExasolOptions options = ExasolOptions.builder().table("table").s3Location("s3").build();
        final String expected = "ExasolOptions{"
                + "jdbcUrl=\"jdbc:exa:localhost:8563\", username=\"sys\", password=\"*******\", "
                + "s3Location=\"s3\", table=\"table\"}";
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
    void testEqualsAndHashMethods() {
        EqualsVerifier.forClass(ExasolOptions.class).verify();
    }

}
