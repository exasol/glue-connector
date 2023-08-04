package com.exasol.glue;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.Test;

class ExasolJdbcUrlParserTest {
    private final ExasolJdbcUrlParser parser = new ExasolJdbcUrlParser();

    @Test
    void testParsesJdbcUrl() {
        final Map<String, String> options = parser.parse("jdbc:exa:localhost:8563");
        assertAll(() -> assertThat(options.get("host"), equalTo("localhost")),
                () -> assertThat(options.get("port"), equalTo("8563")));
    }

    @Test
    void testParsesJdbcUrlWithFingerprint() {
        final Map<String, String> options = parser.parse("jdbc:exa:localhost/fingerprintValue:8563");
        assertAll(() -> assertThat(options.get("host"), equalTo("localhost")),
                () -> assertThat(options.get("port"), equalTo("8563")),
                () -> assertThat(options.get("fingerprint"), equalTo("fingerprintValue")));
    }

    @Test
    void testParsesJdbcUrlWithJdbcOptions() {
        final Map<String, String> options = parser.parse("jdbc:exa:localhost/fingerp:8563;k1=v1;k2=v2;");
        assertAll(() -> assertThat(options.get("host"), equalTo("localhost")),
                () -> assertThat(options.get("port"), equalTo("8563")),
                () -> assertThat(options.get("fingerprint"), equalTo("fingerp")),
                () -> assertThat(options.get("jdbc_options"), equalTo("k1=v1;k2=v2;")));
    }

    @Test
    void testThrowsIfHostDoesNotMatch() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> parser.parse("jdbc:exa:8566;k1=v1"));
        assertThat(exception.getMessage(), startsWith("E-EGC-29"));
    }

    @Test
    void testThrowsIfPortDoesNotMatch() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> parser.parse("jdbc:exa:127.0.0.1/certificate;k1=v1"));
        assertThat(exception.getMessage(), startsWith("E-EGC-29"));
    }

}
