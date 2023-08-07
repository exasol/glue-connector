package com.exasol.glue;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.exasol.errorreporting.ExaError;
import com.exasol.spark.common.Option;

/**
 * A helper class to extract Exasol {@code JDBC} URL components.
 */
public final class ExasolJdbcUrlParser {
    private static final String EXASOL_JDBC_URL_REGEX = "jdbc:exa:(?<host>[^:\\/;]+)(\\/(?<fingerprint>[^:;\\/]*))?:(?<port>\\d+)(;(?<options>.*))?";
    private static final Pattern EXASOL_JDBC_URL_PATTERN = Pattern.compile(EXASOL_JDBC_URL_REGEX);

    /**
     * Extracts {@code HOST}, {@code PORT} and other components from Exasol {code @JDBC} connection string.
     *
     * @param jdbcUrl Exasol JDBC connection string
     * @return map of components
     */
    public Map<String, String> parse(final String jdbcUrl) {
        final Matcher matcher = EXASOL_JDBC_URL_PATTERN.matcher(jdbcUrl);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(ExaError.messageBuilder("E-EGC-29")
                    .message("Unable to extract JDBC URL {{jdbcUrl}} components.", jdbcUrl)
                    .mitigation("Please check that Exasol JDBC URL is constructed properly.")
                    .mitigation(
                            "JDBC URL should be in 'jdbc:exa:<host>[/<fingerprint>]:<port>[;<prop_1>=<value_1>]...[;<prop_n>=<value_n>]' format.")
                    .toString());
        }
        final Map<String, String> result = new HashMap<>();
        result.put(Option.HOST.key(), matcher.group("host"));
        result.put(Option.PORT.key(), matcher.group("port"));
        final String fingerprint = getOptionalGroup(matcher, "fingerprint");
        final String options = getOptionalGroup(matcher, "options");
        if (fingerprint != null && !fingerprint.isEmpty() && !isBlank(fingerprint)) {
            result.put(Option.FINGERPRINT.key(), fingerprint);
        }
        if (options != null && !options.isEmpty() && !isBlank(options)) {
            result.put(Option.JDBC_OPTIONS.key(), options);
        }
        return result;
    }

    private String getOptionalGroup(final Matcher matcher, final String groupName) {
        try {
            return matcher.group(groupName);
        } catch (final IllegalStateException exception) {
            return null;
        }
    }

    private boolean isBlank(final String string) {
        return string.trim().isEmpty();
    }

}
