package com.exasol.glue.reader;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.connector.read.*;
import org.apache.spark.sql.execution.datasources.v2.csv.CSVTable;
import org.apache.spark.sql.sources.Filter;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.util.CaseInsensitiveStringMap;

import com.exasol.errorreporting.ExaError;
import com.exasol.glue.ExasolOptions;
import com.exasol.glue.ExasolValidationException;
import com.exasol.glue.connection.ExasolConnectionFactory;
import com.exasol.glue.listener.ExasolJobEndCleanupListener;
import com.exasol.glue.query.AbstractQueryGenerator;
import com.exasol.glue.query.ExportQueryGenerator;
import com.exasol.spark.common.FilterConverter;
import com.exasol.spark.common.SelectStatementGenerator;
import com.exasol.spark.common.StatementGeneratorFactory;
import com.exasol.sql.expression.BooleanExpression;

import scala.Option;
import scala.collection.JavaConverters;
import scala.collection.Seq;

/**
 * A class that implements {@link ScanBuilder} interface for Exasol database.
 */
public class ExasolScanBuilder implements ScanBuilder, SupportsPushDownFilters, SupportsPushDownRequiredColumns {
    private static final Logger LOGGER = Logger.getLogger(ExasolScanBuilder.class.getName());
    private final ExasolOptions options;
    private final CaseInsensitiveStringMap properties;

    private StructType schema;
    private Filter[] pushedFilters;

    /**
     * Creates a new instance of {@link ExasolScanBuilder}.
     *
     * @param options    user provided options
     * @param schema     user-provided {@link StructType} schema
     * @param properties original key-value properties map that is passed to delegating classes
     */
    public ExasolScanBuilder(final ExasolOptions options, final StructType schema,
            final CaseInsensitiveStringMap properties) {
        this.options = options;
        this.schema = schema;
        this.properties = properties;
        this.pushedFilters = new Filter[0];
    }

    @Override
    public Filter[] pushFilters(final Filter[] filters) {
        final List<Filter> unsupportedFilters = getUnsupportedFilters(filters);
        final List<Filter> supportedFilters = new ArrayList<>(Arrays.asList(filters));
        supportedFilters.removeAll(unsupportedFilters);
        this.pushedFilters = supportedFilters.toArray(new Filter[] {});
        return unsupportedFilters.toArray(new Filter[] {});
    }

    private List<Filter> getUnsupportedFilters(final Filter[] filters) {
        final FilterConverter filterConverter = new FilterConverter();
        return Arrays.asList(filters).stream().filter(f -> !filterConverter.isFilterSupported(f))
                .collect(Collectors.toList());
    }

    @Override
    public Filter[] pushedFilters() {
        return this.pushedFilters;
    }

    @Override
    public void pruneColumns(final StructType schema) {
        this.schema = schema;
    }

    @Override
    public Scan build() {
        final SparkSession sparkSession = SparkSession.active();
        final String s3Bucket = this.options.getS3Bucket();
        final String s3BucketKey = UUID.randomUUID() + "-" + sparkSession.sparkContext().applicationId();
        prepareIntermediateData(sparkSession, s3Bucket, s3BucketKey);
        return getScan(sparkSession, s3Bucket, s3BucketKey);
    }

    /**
     * Returns SQL query that would be run on the Exasol database.
     *
     * @return SQL query for the scan
     */
    protected String getScanQuery() {
        final Optional<BooleanExpression> predicate = new FilterConverter().convert(this.pushedFilters);
        final SelectStatementGenerator renderer = StatementGeneratorFactory.selectFrom(getTableOrQuery())
                .columns(getColumnNames());
        if (predicate.isPresent()) {
            renderer.where(predicate.get());
        }
        return renderer.render();
    }

    private String getTableOrQuery() {
        if (this.options.hasTable()) {
            return this.options.getTable();
        } else {
            return "(" + this.options.getQuery() + ")";
        }
    }

    private String[] getColumnNames() {
        return Stream.of(this.schema.fields()).map(StructField::name).toArray(String[]::new);
    }

    private void prepareIntermediateData(final SparkSession spark, final String s3Bucket, final String s3BucketKey) {
        LOGGER.info(() -> "Using S3 bucket '" + s3Bucket + "' with folder '" + s3BucketKey + "' for scan job data.");
        setupSparkCleanupJobListener(spark, s3BucketKey);
        exportIntermediateData(s3BucketKey);
    }

    private Scan getScan(final SparkSession spark, final String s3Bucket, final String s3BucketKey) {
        final CSVTable csvTable = new CSVTable("", spark, this.properties, getS3ScanPath(s3Bucket, s3BucketKey),
                Option.apply(this.schema), null);
        return csvTable.newScanBuilder(getUpdatedMapWithCSVOptions(this.properties)).build();
    }

    private void setupSparkCleanupJobListener(final SparkSession spark, final String s3BucketKey) {
        spark.sparkContext().addSparkListener(new ExasolJobEndCleanupListener(this.options, s3BucketKey));
    }

    private void exportIntermediateData(final String s3BucketKey) {
        final int numberOfPartitions = this.options.getNumberOfPartitions();
        final String innerScanQuery = getScanQuery();
        final String exportQuery = new ExportQueryGenerator(this.options, s3BucketKey, numberOfPartitions)
                .generateQuery(innerScanQuery);
        LOGGER.info(() -> "Running export statement with inner query '" + innerScanQuery + "'.");
        final ExasolConnectionFactory connectionFactory = new ExasolConnectionFactory(this.options);
        try (final Connection connection = connectionFactory.getConnection()) {
            final int numberOfExportedRows = new ExportQueryRunner(connection).runExportQuery(exportQuery);
            LOGGER.info(() -> "Exported '" + numberOfExportedRows + "' rows into '" + this.options.getS3Bucket() + "/"
                    + s3BucketKey + "'.");
        } catch (final SQLException exception) {
            throw new ExasolValidationException(ExaError.messageBuilder("E-EGC-17")
                    .message("Failed to run export query {{exportQuery}} into S3 path {{s3Path}} location.")
                    .parameter("exportQuery", AbstractQueryGenerator.identifierRemoved(exportQuery))
                    .parameter("s3Path", this.options.getS3Bucket() + "/" + s3BucketKey)
                    .mitigation("Please make sure that the query or table name is correct and obeys SQL syntax rules.")
                    .toString(), exception);
        }
    }

    private Seq<String> getS3ScanPath(final String s3Bucket, final String s3BucketKey) {
        final String path = "s3a://" + Paths.get(s3Bucket, s3BucketKey, "*.csv").toString();
        return JavaConverters.asScalaIteratorConverter(Arrays.asList(path).iterator()).asScala().toSeq();
    }

    private CaseInsensitiveStringMap getUpdatedMapWithCSVOptions(final CaseInsensitiveStringMap map) {
        final Map<String, String> updatedMap = new HashMap<>(map.asCaseSensitiveMap());
        updatedMap.put("header", "true");
        updatedMap.put("delimiter", ",");
        return new CaseInsensitiveStringMap(updatedMap);
    }

}
