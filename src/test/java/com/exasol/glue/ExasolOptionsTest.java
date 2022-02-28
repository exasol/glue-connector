package com.exasol.glue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.Test;
import nl.jqno.equalsverifier.EqualsVerifier;

class ExasolOptionsTest {

    @Test
    void testWithDefaults() {
        final ExasolOptions options = ExasolOptions.builder().build();
        assertAll(() -> assertThat(options.getHost(), equalTo("localhost")),
                () -> assertThat(options.getPort(), equalTo(8563)),
                () -> assertThat(options.getUsername(), equalTo("sys")),
                () -> assertThat(options.getPassword(), equalTo("exasol")));
    }

    @Test
    void testHasS3Location() {
        assertAll(() -> assertThat(ExasolOptions.builder().build().hasS3Location(), equalTo(false)),
                () -> assertThat(ExasolOptions.builder().s3Location("s3").build().hasS3Location(), equalTo(true)));
    }

    @Test
    void testGetS3Location() {
        final ExasolOptions options = ExasolOptions.builder().s3Location("bucket").build();
        assertThat(options.getS3Location(), equalTo("bucket"));
    }

    @Test
    void testGetHost() {
        final ExasolOptions options = ExasolOptions.builder().host("host").build();
        assertThat(options.getHost(), equalTo("host"));
    }

    @Test
    void testGetPort() {
        final ExasolOptions options = ExasolOptions.builder().port(1234).build();
        assertThat(options.getPort(), equalTo(1234));
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
        assertThat(ExasolOptions.builder().build().hasTable(), equalTo(false));
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
        assertThat(ExasolOptions.builder().build().hasQuery(), equalTo(false));
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
    void testToString() {
        final ExasolOptions options = ExasolOptions.builder().build();
        assertThat(options.toString(),
                equalTo("ExasolOptions{host=\"localhost\", port=\"8563\", username=\"sys\", password=\"*******\"}"));
    }

    @Test
    void testEqualsAndHashMethods() {
        EqualsVerifier.forClass(ExasolOptions.class).verify();
    }

}
