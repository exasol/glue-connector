package com.exasol.glue.query;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.jupiter.api.Test;

class AbstractQueryGeneratorTest {

    @Test
    void testRemoveIdentifier() {
        final String input = "EXPORT (\nSELECT * FROM table\n) INTO CSV\n" //
                + "AT 'https://bucket.s3.amazonaws.com'\n" //
                + "USER 'user' IDENTIFIED BY 'pass'\n" //
                + "FILE 'a/part-001.csv'\n" //
                + "FILE 'a/part-002.csv'\n" //
                + "WITH COLUMN NAMES";
        final String expected = "EXPORT (\nSELECT * FROM table\n) INTO CSV\n" //
                + "AT 'https://bucket.s3.amazonaws.com'\n" //
                + "FILE 'a/part-001.csv'\n" //
                + "FILE 'a/part-002.csv'\n" //
                + "WITH COLUMN NAMES";
        assertThat(AbstractQueryGenerator.identifierRemoved(input), equalTo(expected));
    }

}
