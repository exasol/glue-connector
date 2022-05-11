package com.exasol.glue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ColumnDescriptionTest {

    @Test
    void testValidateName() {
        final ColumnDescription.Builder builder = ColumnDescription.builder().type(-1);
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> builder.build());
        assertThat(exception.getMessage(), startsWith("E-EGC-8"));
    }

    @Test
    void testValidateType() {
        final ColumnDescription.Builder builder = ColumnDescription.builder().name("c1");
        final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> builder.build());
        assertThat(exception.getMessage(), startsWith("E-EGC-9"));
    }

}
