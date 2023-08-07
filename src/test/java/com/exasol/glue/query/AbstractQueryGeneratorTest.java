package com.exasol.glue.query;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.exasol.spark.common.ExasolOptions;
import com.exasol.spark.common.Option;

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

    @Test
    void testEscapedIdentifier() {
        final ExasolOptions options = ExasolOptions.builder() //
            .table("table") //
            .s3Bucket("bu'cket")
            .withOptionsMap( //
                    Map.of(Option.AWS_ACCESS_KEY_ID.key(), "user'name", Option.AWS_SECRET_ACCESS_KEY.key(), "pa'ss") //
            ) //
            .build();
        final String expected = "AT 'https://bu''cket.s3.amazonaws.com'\nUSER 'user''name' IDENTIFIED BY 'pa''ss'\n";
        assertThat(new ImportQueryGenerator(options).getIdentifier(), equalTo(expected));
    }

}
