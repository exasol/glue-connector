package com.exasol.glue.reader;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

import com.exasol.errorreporting.ExaError;
import com.exasol.glue.ExasolOptions;
import com.exasol.glue.ExasolValidationException;
import com.exasol.glue.connection.ExasolConnectionFactory;
import com.exasol.glue.listener.ExasolJobEndCleanupListener;
import com.exasol.glue.query.ExportQueryGenerator;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.connector.read.Scan;
import org.apache.spark.sql.connector.read.ScanBuilder;
import org.apache.spark.sql.execution.datasources.v2.csv.CSVTable;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.util.CaseInsensitiveStringMap;

import scala.Option;
import scala.collection.JavaConverters;
import scala.collection.Seq;

/**
 * A class that implements {@link ScanBuilder} interface for Exasol database.
 */
public class ExasolScanBuilder implements ScanBuilder {
    private static final Logger LOGGER = Logger.getLogger(ExasolScanBuilder.class.getName());
    private final StructType schema;
    private final ExasolOptions options;
    private final CaseInsensitiveStringMap map;

    /**
     * Creates a new instance of {@link ExasolScanBuilder}.
     *
     * @param options user provided options
     * @param schema  user-provided {@link StructType} schema
     * @param map     user-provided key-value options map
     */
    public ExasolScanBuilder(final ExasolOptions options, final StructType schema, final CaseInsensitiveStringMap map) {
        this.options = options;
        this.schema = schema;
        this.map = map;
    }

    @Override
    public Scan build() {
        final SparkSession sparkSession = SparkSession.active();
        final String s3Bucket = this.options.getS3Bucket();
        final String s3BucketKey = UUID.randomUUID() + "-" + sparkSession.sparkContext().applicationId();
        prepareIntermediateData(sparkSession, s3Bucket, s3BucketKey);
        return getScan(sparkSession, s3Bucket, s3BucketKey);
    }

    private void prepareIntermediateData(final SparkSession spark, final String s3Bucket, final String s3BucketKey) {
        LOGGER.info(() -> "Using S3 bucket '" + s3Bucket + "' with folder '" + s3BucketKey + "' for scan job data.");
        setupSparkCleanupJobListener(spark, s3BucketKey);
        exportIntermediateData(s3BucketKey);
    }

    private Scan getScan(final SparkSession spark, final String s3Bucket, final String s3BucketKey) {
        final CSVTable csvTable = new CSVTable("", spark, this.map, getS3ScanPath(s3Bucket, s3BucketKey),
                Option.apply(this.schema), null);
        return csvTable.newScanBuilder(getUpdatedMapWithCSVOptions(this.map)).build();
    }

    private void setupSparkCleanupJobListener(final SparkSession spark, final String s3BucketKey) {
        spark.sparkContext().addSparkListener(new ExasolJobEndCleanupListener(this.options, s3BucketKey));
    }

    private void exportIntermediateData(final String s3BucketKey) {
        final int numberOfPartitions = this.options.getNumberOfPartitions();
        final String exportQuery = new ExportQueryGenerator(this.options, s3BucketKey, numberOfPartitions)
                .generateQuery();
        final ExasolConnectionFactory connectionFactory = new ExasolConnectionFactory(this.options);
        try (final Connection connection = connectionFactory.getConnection()) {
            final int numberOfExportedRows = new ExportQueryRunner(connection).runExportQuery(exportQuery);
            LOGGER.info(() -> "Exported '" + numberOfExportedRows + "' rows into '" + this.options.getS3Bucket() + "/"
                    + s3BucketKey + "'.");
        } catch (final SQLException exception) {
            throw new ExasolValidationException(ExaError.messageBuilder("E-EGC-17")
                    .message("Failed to run export query {{exportQuery}} into S3 path {{s3Path}} location.")
                    .parameter("exportQuery", exportQuery)
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
