package com.exasol.glue;

import java.util.Map;

import org.apache.spark.sql.util.CaseInsensitiveStringMap;

import com.exasol.spark.common.ExasolOptions;
import com.exasol.spark.common.Option;

/**
 * This class provides the {@link ExasolOptions} using {@code JDBC} connection string and Spark configuration options.
 *
 * It is to keep backward compatibility with previous versions of {@code glue-connector}. It allows to use {@code
 * jdbc_url} to access Exasol database.
 */
public final class ExasolOptionsProvider {

    /**
     * Creates {@link ExasolOptions} by extracting {@code JDBC} connection string components.
     *
     * @param sparkStringMap Spark configuration options
     * @return Exasol options instance
     */
    public ExasolOptions fromJdbcUrl(final CaseInsensitiveStringMap sparkStringMap) {
        final String jdbcUrl = sparkStringMap.get(Option.JDBC_URL.key());
        final Map<String, String> jdbcUrlMap = new ExasolJdbcUrlParser().parse(jdbcUrl);
        jdbcUrlMap.putAll(sparkStringMap.asCaseSensitiveMap());
        return ExasolOptions.from(new CaseInsensitiveStringMap(jdbcUrlMap));
    }

}
