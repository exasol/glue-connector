package com.exasol.glue.reader;

import static com.exasol.glue.Constants.AWS_ACCESS_KEY_ID;
import static com.exasol.glue.Constants.AWS_SECRET_ACCESS_KEY;
import static org.apache.spark.sql.types.DataTypes.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;

import com.exasol.glue.ExasolOptions;

import org.apache.spark.sql.sources.*;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.util.CaseInsensitiveStringMap;
import org.junit.jupiter.api.Test;

class ExasolScanBuilderTest {
    private final ExasolOptions options = ExasolOptions.builder() //
            .s3Bucket("bucket") //
            .table("t1") //
            .withOptionsMap(Map.of(AWS_ACCESS_KEY_ID, "user", AWS_SECRET_ACCESS_KEY, "pass")) //
            .build();
    private final ExasolScanBuilder scanBuilder = new ExasolScanBuilder(options,
            new StructType().add("column", LongType, false), new CaseInsensitiveStringMap(Collections.emptyMap()));

    @Test
    void testWithoutPushedFilters() throws SQLException {
        assertThat(this.scanBuilder.getScanQuery(), equalTo("SELECT \"column\" FROM t1"));
    }

    @Test
    void testUnsupportedFilters() {
        final Filter[] filters = new Filter[] { //
                new EqualTo("c", 1), //
                new GreaterThan("c", 1), //
                new GreaterThanOrEqual("c", 1), //
                new LessThan("c", 1), //
                new LessThanOrEqual("c", 1), //
                new IsNull("c"), //
                new IsNotNull("c"), //
                new StringStartsWith("c", "abc"), //
                new StringEndsWith("c", "abc"), //
                new StringContains("c", "abc"), //
                new In("c", new Object[] { 1, 2 }), //
                new Not(new EqualTo("c", "a")), //
                new Or(new EqualTo("c", "a"), new LessThan("c1", 2)), //
                new And(new EqualTo("c", "a"), new LessThan("c1", 2)), //
                new EqualNullSafe("c", "null") //
        };
        assertThat(this.scanBuilder.pushFilters(filters), equalTo(new Filter[] { new EqualNullSafe("c", "null") }));
    }

    @Test
    void testWithPushedFilters() {
        this.scanBuilder.pushFilters(new Filter[] { new EqualTo("c1", "xyz"), new GreaterThan("c2", 10) });
        assertThat(this.scanBuilder.getScanQuery(),
                equalTo("SELECT \"column\" FROM t1 WHERE (\"c1\" = 'xyz') AND (\"c2\" > 10)"));
    }

    @Test
    void testWithPrunedColumns() {
        this.scanBuilder.pruneColumns(new StructType().add("c1", LongType, false).add("c4", DoubleType, false));
        assertThat(this.scanBuilder.getScanQuery(), equalTo("SELECT \"c1\", \"c4\" FROM t1"));
    }

    @Test
    void testWithPushedFiltersAndPrunedColumns() {
        this.scanBuilder.pruneColumns(new StructType().add("c1", LongType, false));
        this.scanBuilder.pushFilters(new Filter[] { new StringContains("c2", "xyz"), new IsNull("c3") });
        assertThat(this.scanBuilder.getScanQuery(),
                equalTo("SELECT \"c1\" FROM t1 WHERE (\"c2\" LIKE '%xyz%') AND (\"c3\" IS NULL)"));
    }

    @Test
    void testWithEmptyColumns() {
        this.scanBuilder.pruneColumns(new StructType());
        assertThat(this.scanBuilder.getScanQuery(), equalTo("SELECT * FROM t1"));
    }

    @Test
    void testWithPushedFiltersAndPrunedColumnsForQuery() {
        final ExasolOptions options = ExasolOptions.builder().s3Bucket("bucket") //
                .query("SELECT a,b FROM t3 WHERE c = 1") //
                .withOptionsMap(Map.of(AWS_ACCESS_KEY_ID, "user", AWS_SECRET_ACCESS_KEY, "pass")) //
                .build();
        final ExasolScanBuilder scanBuilder = new ExasolScanBuilder(options,
                new StructType().add("column", LongType, false), new CaseInsensitiveStringMap(Collections.emptyMap()));

        scanBuilder.pruneColumns(new StructType().add("c1", LongType, false));
        scanBuilder.pushFilters(new Filter[] { new StringContains("c2", "xyz"), new IsNull("c3") });
        assertThat(scanBuilder.getScanQuery(), equalTo(
                "SELECT \"c1\" FROM (SELECT a,b FROM t3 WHERE c = 1) WHERE (\"c2\" LIKE '%xyz%') AND (\"c3\" IS NULL)"));
    }

}
