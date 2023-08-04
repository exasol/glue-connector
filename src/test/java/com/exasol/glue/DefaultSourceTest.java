package com.exasol.glue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.Map;

import org.apache.spark.sql.util.CaseInsensitiveStringMap;
import org.junit.jupiter.api.Test;

import com.exasol.spark.common.Option;

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
                Map.of(Option.TABLE.key(), "db_table", Option.QUERY.key(), "db_query"));
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> source.inferSchema(options));
        assertThat(exception.getMessage(), startsWith("E-EGC-2"));
    }

    @Test
    void testInferSchemaThrowsIfRequiredParametersMissing() {
        final CaseInsensitiveStringMap options = new CaseInsensitiveStringMap(
                Map.of(Option.QUERY.key(), "db_query", Option.USERNAME.key(), "sys"));
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> source.inferSchema(options));
        assertThat(exception.getMessage(), startsWith("E-EGC-3"));
    }

}
