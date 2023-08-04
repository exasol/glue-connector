package com.exasol.glue;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.exasol.errorreporting.ExaError;

/**
 * A helper class to extract Exasol {@code JDBC} URL components.
 */
public final class ExasolJdbcUrlParser {
    private static final String EXASOL_JDBC_URL_REGEX = "jdbc:exa:(?<host>[^:\\/;]+)(\\/(?<fingerprint>[^:;\\/]*))?:(?<port>\\d+)(;(?<options>.*))?";
    private static final Pattern EXASOL_JDBC_URL_PATTERN = Pattern.compile(EXASOL_JDBC_URL_REGEX);

    /**
     * Extracts {@code HOST}, {@code PORT} and other components from Exasol {@JDBC} connection string.
     *
     * @param jdbcUrl Exasol JDBC connection string
     * @return map of components
     */
    public Map<String, String> parse(final String jdbcUrl) {
        final Matcher matcher = EXASOL_JDBC_URL_PATTERN.matcher(jdbcUrl);
        if (!matcher.matches()) {
            throw new IllegalArgumentException(ExaError.messageBuilder("E-EGC-29")
                    .message("Unable to extract JDBC URL '{{jdbcUrl}}' components.", jdbcUrl)
                    .mitigation("Please check that Exasol JDBC URL is constructed properly.").toString());
        }
        final Map<String, String> result = new HashMap<>();
        final String host = matcher.group("host");
        final String port = matcher.group("port");
        final String fingerprint = getOptionalGroup(matcher, "fingerprint");
        final String options = getOptionalGroup(matcher, "options");
        result.put("host", host);
        result.put("port", port);
        if (fingerprint != null && !fingerprint.isEmpty() && !isBlank(fingerprint)) {
            result.put("fingerprint", fingerprint);
        }
        if (options != null && !options.isEmpty() && !isBlank(options)) {
            result.put("jdbc_options", options);
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
