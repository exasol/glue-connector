package com.exasol.glue;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.exasol.spark.common.Option;

class ExasolJdbcUrlParserTest {
    private final ExasolJdbcUrlParser parser = new ExasolJdbcUrlParser();

    @Test
    void testParsesJdbcUrl() {
        final Map<String, String> options = parser.parse("jdbc:exa:localhost:8563");
        assertAll(() -> assertThat(options.get(Option.HOST.key()), equalTo("localhost")),
                () -> assertThat(options.get(Option.PORT.key()), equalTo("8563")));
    }

    @Test
    void testParsesJdbcUrlHostAsIPAddress() {
        final Map<String, String> options = parser.parse("jdbc:exa:192.168.0.1:8563");
        assertAll(() -> assertThat(options.get(Option.HOST.key()), equalTo("192.168.0.1")),
                () -> assertThat(options.get(Option.PORT.key()), equalTo("8563")));
    }

    @Test
    void testParsesJdbcUrlWithFingerprint() {
        final Map<String, String> options = parser.parse("jdbc:exa:localhost/fingerprintValue:8563");
        assertAll(() -> assertThat(options.get(Option.HOST.key()), equalTo("localhost")),
                () -> assertThat(options.get(Option.PORT.key()), equalTo("8563")),
                () -> assertThat(options.get(Option.FINGERPRINT.key()), equalTo("fingerprintValue")));
    }

    @Test
    void testParsesJdbcUrlWithEmptyFingerprint() {
        final Map<String, String> options = parser.parse("jdbc:exa:localhost/:8563");
        assertAll(() -> assertThat(options.get(Option.HOST.key()), equalTo("localhost")),
                () -> assertThat(options.get(Option.PORT.key()), equalTo("8563")),
                () -> assertThat(options.containsKey(Option.FINGERPRINT.key()), equalTo(false)));
    }

    @Test
    void testParsesJdbcUrlWithBlankFingerprint() {
        final Map<String, String> options = parser.parse("jdbc:exa:localhost/   :8563");
        assertAll(() -> assertThat(options.get(Option.HOST.key()), equalTo("localhost")),
                () -> assertThat(options.get(Option.PORT.key()), equalTo("8563")),
                () -> assertThat(options.containsKey(Option.FINGERPRINT.key()), equalTo(false)));
    }

    @Test
    void testParsesJdbcUrlWithJdbcOptions() {
        final Map<String, String> options = parser.parse("jdbc:exa:localhost/fingerp:8563;k1=v1;k2=v2;");
        assertAll(() -> assertThat(options.get(Option.HOST.key()), equalTo("localhost")),
                () -> assertThat(options.get(Option.PORT.key()), equalTo("8563")),
                () -> assertThat(options.get(Option.FINGERPRINT.key()), equalTo("fingerp")),
                () -> assertThat(options.get(Option.JDBC_OPTIONS.key()), equalTo("k1=v1;k2=v2;")));
    }

    @Test
    void testParsesJdbcUrlWithFingerprintInJdbcOptions() {
        final Map<String, String> options = parser.parse("jdbc:exa:localhost:8563;k1=v1;fingerprint=DA7A;");
        assertAll(() -> assertThat(options.get(Option.HOST.key()), equalTo("localhost")),
                () -> assertThat(options.get(Option.PORT.key()), equalTo("8563")),
                () -> assertThat(options.get(Option.JDBC_OPTIONS.key()), equalTo("k1=v1;fingerprint=DA7A;")));
    }

    @ParameterizedTest
    @ValueSource(strings = { "jdbc:exa:8566;k1=v1", "jdbc:exa:127.0.0.1/certificate;k1=v1",
            "jdbc:exa:127.0.0.1/certificate:13e7;k1=v1" })
    void testThrowsOnNonParseableJdbcUrl(final String nonParseableJdbcUrl) {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> parser.parse(nonParseableJdbcUrl));
        assertThat(exception.getMessage(), startsWith("E-EGC-29"));
    }

}
