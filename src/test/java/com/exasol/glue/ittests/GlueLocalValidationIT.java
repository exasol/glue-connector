package com.exasol.glue.ittests;

import static com.exasol.matcher.ResultSetStructureMatcher.table;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import org.hamcrest.Matcher;
import org.junit.jupiter.api.*;
import org.testcontainers.junit.jupiter.Testcontainers;

import scala.Product;
import scala.Tuple4;
import scala.collection.JavaConverters;
import scala.collection.Seq;

@Tag("integration")
@Testcontainers
class GlueLocalValidationIT extends BaseIntegrationTestSetup {

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
        final Schema expectedSchema = new Schema(
                new SchemaBuilder().beginStruct().atomicField("c1", TypeCode.INT).endStruct().build());
        assertThat(getGlueSource(table.getFullyQualifiedName()).getDynamicFrame().schema(), equalTo(expectedSchema));
    }

    @Test
    void testDataSourceRead() {
        final DynamicFrame dyf = getGlueSource(table.getFullyQualifiedName()).getDynamicFrame();
        final Dataset<Integer> ds = getDynamicFrameAsDataset(dyf, Encoders.INT());
        assertAll(() -> assertThat(dyf.count(), equalTo(6L)),
                () -> assertThat(ds.collectAsList(), containsInAnyOrder(1, 2, 3, 4, 5, 6)));
    }

    @Test
    void testApplyMapping() {
        final Seq<Product> mappings = JavaConverters
                .asScalaBuffer(Arrays.asList(Tuple4.apply("c1", "int", "c1", "string")));
        final DynamicFrame dyf = getGlueSource(table.getFullyQualifiedName()) //
                .getDynamicFrame() //
                .applyMapping(mappings, true, "", null, 0, 0);
        final Dataset<String> ds = getDynamicFrameAsDataset(dyf, Encoders.STRING());
        assertThat(ds.collectAsList(), containsInAnyOrder("1", "2", "3", "4", "5", "6"));
    }

    @Test
    void testReadExasolWriteS3() {
        final DynamicFrame dynamicFrame = getGlueSource(table.getFullyQualifiedName()).getDynamicFrame();

        // Save DynamicFrame
        final String writePath = "/tmp/output/readwritetest/" + UUID.randomUUID();
        glueContext //
                .getSinkWithFormat("s3", new JsonOptions("{\"path\":\"" + writePath + "\"}"), "", "csv",
                        getCSVWriteOptions()) //
                .writeDynamicFrame(dynamicFrame, CallSite.apply("", ""));

        // Read Saved DynamicFrame
        final DynamicFrame readDynamicFrame = glueContext //
                .getSource("s3", getCSVPaths(writePath), "", "") //
                .withFormat("csv", getCSVWriteOptions()) //
                .getDynamicFrame();

        assertAll(() -> assertThat(readDynamicFrame.count(), equalTo(6L)),
                () -> assertThat(getDynamicFrameAsDataset(readDynamicFrame, Encoders.STRING()).collectAsList(),
                        containsInAnyOrder("1", "2", "3", "4", "5", "6")));
    }

    @Test
    void testReadS3WriteExasol() throws SQLException {
        final String dataPath = new File("src/test/resources/data/").getAbsolutePath();
        final Seq<Product> mappings = JavaConverters
                .asScalaBuffer(Arrays.asList(Tuple4.apply("sensor_id", "string", "sensor_id", "string"),
                        Tuple4.apply("humidity", "string", "humidity", "int")));

        final DynamicFrame readDynamicFrame = glueContext //
                .getSource("s3", getCSVPaths(dataPath), "", "") //
                .withFormat("csv", getCSVWriteOptions()) //
                .getDynamicFrame() //
                .applyMapping(mappings, true, "", null, 0, 0);

        final Table savedTable = schema.createTable("table_local_validation_read_s3", "SENSOR_ID", "VARCHAR(5)",
                "HUMIDITY", "DECIMAL(9,0)");
        final String savedTableName = savedTable.getFullyQualifiedName();
        getGlueSink(savedTableName).writeDynamicFrame(readDynamicFrame, null);

        assertQuery("SELECT * FROM " + savedTableName + " ORDER BY HUMIDITY ASC", //
                table().row("s1", 10) //
                        .row("s1", 78) //
                        .row("s2", 80) //
                        .row("s2", 88) //
                        .row("s3", 98) //
                        .matches());
    }

    @Test
    void testReadExasolWriteExasol() throws SQLException {
        final DynamicFrame dynamicFrame = getGlueSource(table.getFullyQualifiedName()).getDynamicFrame();

        final Table savedTable = schema.createTable("table_local_validation_read_exasol", "ID", "DECIMAL(9,0)");
        final String savedTableName = savedTable.getFullyQualifiedName();
        getGlueSink(savedTableName).writeDynamicFrame(dynamicFrame, null);

        assertQuery("SELECT * FROM " + savedTableName + " ORDER BY ID ASC", //
                table().row(1).row(2).row(3).row(4).row(5).row(6).matches());
    }

    private JsonOptions getCSVWriteOptions() {
        return new JsonOptions("{\"separator\":\",\", \"withHeader\": true}");
    }

    private JsonOptions getCSVPaths(final String path) {
        return new JsonOptions("{\"paths\":[\"" + path + "\"]}");
    }

    private DataSource getGlueSource(final String tableName) {
        return glueContext.getSource("custom.spark", getJsonOptions(tableName), "customSource", "");
    }

    private DataSink getGlueSink(final String tableName) {
        return glueContext.getSink("custom.spark", getJsonOptions(tableName), "");
    }

    private JsonOptions getJsonOptions(final String tableName) {
        final Map<String, String> map = new HashMap<>(this.options);
        map.put("table", tableName);
        return new JsonOptions(JavaConverters.mapAsScalaMap(map));
    }

    private <T> Dataset<T> getDynamicFrameAsDataset(final DynamicFrame dyf, final Encoder<T> encoder) {
        final Seq<ResolveSpec> emptySpec = JavaConverters.asScalaBuffer(Arrays.asList());
        return dyf.toDF(emptySpec).as(encoder);
    }

    private void assertQuery(final String query, final Matcher<ResultSet> matcher) throws SQLException {
        try (final ResultSet result = connection.createStatement().executeQuery(query)) {
            assertThat(result, matcher);
        }
    }

}
