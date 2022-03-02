package com.exasol.glue;

import static com.exasol.glue.Constants.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.Map;

import org.apache.spark.sql.util.CaseInsensitiveStringMap;
import org.junit.jupiter.api.Test;

class DefaultSourceTest {

    @Test
    void testShortName() {
        assertThat(new DefaultSource().shortName(), equalTo("exasol"));
    }

    @Test
    void testInferSchemaThrowsWhenNoQueryOrTableOption() {
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new DefaultSource().inferSchema(new CaseInsensitiveStringMap(Collections.emptyMap())));
        assertThat(exception.getMessage(), startsWith("E-EGC-1"));
    }

    @Test
    void testInferSchemaThrowsWhenBothQueryOrTableOption() {
        final Map<String, String> options = Map.of(TABLE, "db_table", QUERY, "db_query");
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new DefaultSource().inferSchema(new CaseInsensitiveStringMap(options)));
        assertThat(exception.getMessage(), startsWith("E-EGC-2"));
    }

    @Test
    void testInferSchemaThrowsIfRequiredParametersMissing() {
        final Map<String, String> options = Map.of(QUERY, "db_query", USERNAME, "sys");
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new DefaultSource().inferSchema(new CaseInsensitiveStringMap(options)));
        assertThat(exception.getMessage(), startsWith("E-EGC-3"));
    }

}
