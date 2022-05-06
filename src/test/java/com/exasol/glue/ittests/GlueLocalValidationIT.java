package com.exasol.glue.ittests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.*;
import java.util.stream.Stream;

import com.amazonaws.services.glue.*;
import com.amazonaws.services.glue.errors.CallSite;
import com.amazonaws.services.glue.schema.Schema;
import com.amazonaws.services.glue.schema.TypeCode;
import com.amazonaws.services.glue.schema.builders.SchemaBuilder;
import com.amazonaws.services.glue.util.JsonOptions;
import com.exasol.dbbuilder.dialects.Table;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Testcontainers;

import scala.Product;
import scala.Tuple4;
import scala.collection.JavaConverters;
import scala.collection.Seq;

@Tag("integration")
@Testcontainers
class GlueLocalValidationIT extends BaseIntegrationTest {

    private static Table table;
    private static GlueContext glueContext;
    private Map<String, String> options;

    @BeforeAll
    static void setup() {
        table = schema.createTableBuilder("table_local_validation") //
                .column("c1", "SMALLINT") //
                .build() //
                .bulkInsert(Stream.of(1, 2, 3, 4, 5, 6).map(n -> List.of(n)));
        setupGlueSparkContext();
    }

    private static void setupGlueSparkContext() {
        if (spark != null) {
            spark.stop();
        }
        final SparkConf conf = new SparkConf() //
                .setMaster("local[*]") //
                .setAppName("GlueLocalValidationTests");
        final SparkContext sparkContext = new SparkContext(conf);
        glueContext = new GlueContext(sparkContext);
        spark = glueContext.getSparkSession();
    }

    @BeforeEach
    void beforeEach() {
        this.options = new HashMap<>();
        this.options.put("JOB_NAME", "ExasolGlueJob");
        this.options.put("className", "exasol");
        this.options.putAll(getDefaultOptions());
    }

    @Test
    void testDataSourceSchema() {
        this.options.put("table", table.getFullyQualifiedName());
        final Schema expectedSchema = new Schema(
                new SchemaBuilder().beginStruct().atomicField("c1", TypeCode.INT).endStruct().build());
        assertThat(getGlueSource().getDynamicFrame().schema(), equalTo(expectedSchema));
    }

    @Test
    void testDataSourceRead() {
        this.options.put("query", "SELECT * FROM " + table.getFullyQualifiedName());
        final DynamicFrame dyf = getGlueSource().getDynamicFrame();
        final Dataset<Integer> ds = getDynamicFrameAsDataset(dyf, Encoders.INT());
        assertAll(() -> assertThat(dyf.count(), equalTo(6L)),
                () -> assertThat(ds.collectAsList(), containsInAnyOrder(1, 2, 3, 4, 5, 6)));
    }

    @Test
    void testApplyMapping() {
        this.options.put("table", table.getFullyQualifiedName());
        final Seq<Product> mappings = JavaConverters
                .asScalaBuffer(Arrays.asList(Tuple4.apply("c1", "int", "c1", "string")));
        final DynamicFrame dyf = getGlueSource().getDynamicFrame().applyMapping(mappings, true, "", null, 0, 0);
        final Dataset<String> ds = getDynamicFrameAsDataset(dyf, Encoders.STRING());
        assertThat(ds.collectAsList(), containsInAnyOrder("1", "2", "3", "4", "5", "6"));
    }

    @Test
    void testReadWrite() {
        this.options.put("table", table.getFullyQualifiedName());
        final DynamicFrame dyf = getGlueSource().getDynamicFrame();

        // Save DynamicFrame
        final String writePath = "/tmp/output/readwritetest/" + UUID.randomUUID();
        final JsonOptions writeOptions = new JsonOptions("{\"separator\":\",\", \"withHeader\": true}");
        glueContext //
                .getSinkWithFormat("s3", new JsonOptions("{\"path\":\"" + writePath + "\"}"), "", "csv", writeOptions) //
                .writeDynamicFrame(dyf, CallSite.apply("", ""));

        // Read Saved DynamicFrame
        final DynamicFrame readDyf = glueContext //
                .getSource("s3", new JsonOptions("{\"paths\":[\"" + writePath + "\"]}"), "", "") //
                .withFormat("csv", writeOptions) //
                .getDynamicFrame();

        assertAll( //
                () -> assertThat(readDyf.count(), equalTo(6L)), //
                () -> assertThat(getDynamicFrameAsDataset(readDyf, Encoders.STRING()).collectAsList(),
                        containsInAnyOrder("1", "2", "3", "4", "5", "6")));
    }

    private DataSource getGlueSource() {
        return glueContext.getSource("custom.spark", getJsonOptions(), "customSource", "");
    }

    private JsonOptions getJsonOptions() {
        return new JsonOptions(JavaConverters.mapAsScalaMap(this.options));
    }

    private <T> Dataset<T> getDynamicFrameAsDataset(final DynamicFrame dyf, final Encoder<T> encoder) {
        final Seq<ResolveSpec> emptySpec = JavaConverters.asScalaBuffer(Arrays.asList());
        return dyf.toDF(emptySpec).as(encoder);
    }

}
