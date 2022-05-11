package com.exasol.glue;

import static com.exasol.glue.Constants.QUERY;
import static com.exasol.glue.Constants.TABLE;
import static com.exasol.glue.Constants.USERNAME;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.Map;

import org.apache.spark.sql.util.CaseInsensitiveStringMap;
import org.junit.jupiter.api.Test;

class DefaultSourceTest {
    private final DefaultSource source = new DefaultSource();

    @Test
    void testShortName() {
        assertThat(new DefaultSource().shortName(), equalTo("exasol"));
    }

    @Test
    void testInferSchemaThrowsWhenNoQueryOrTableOption() {
        final CaseInsensitiveStringMap emptyOptions = new CaseInsensitiveStringMap(Collections.emptyMap());
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> source.inferSchema(emptyOptions));
        assertThat(exception.getMessage(), startsWith("E-EGC-1"));
    }

    @Test
    void testInferSchemaThrowsWhenBothQueryOrTableOption() {
        final CaseInsensitiveStringMap options = new CaseInsensitiveStringMap(
                Map.of(TABLE, "db_table", QUERY, "db_query"));
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> source.inferSchema(options));
        assertThat(exception.getMessage(), startsWith("E-EGC-2"));
    }

    @Test
    void testInferSchemaThrowsIfRequiredParametersMissing() {
        final CaseInsensitiveStringMap options = new CaseInsensitiveStringMap(
                Map.of(QUERY, "db_query", USERNAME, "sys"));
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> source.inferSchema(options));
        assertThat(exception.getMessage(), startsWith("E-EGC-3"));
    }

}
