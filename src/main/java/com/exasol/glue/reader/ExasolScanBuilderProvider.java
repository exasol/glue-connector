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
import org.apache.spark.sql.connector.read.ScanBuilder;
import org.apache.spark.sql.execution.datasources.v2.csv.CSVTable;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.util.CaseInsensitiveStringMap;

import scala.Option;
import scala.collection.JavaConverters;
import scala.collection.Seq;

/**
 * A class that provides {@link ScanBuilder} instance.
 */
public final class ExasolScanBuilderProvider {
    private static final Logger LOGGER = Logger.getLogger(ExasolScanBuilderProvider.class.getName());

    private final ExasolOptions options;

    /**
     * Creates a new instance of {@link ExasolScanBuilderProvider}.
     *
     * @param options user provided options
     */
    public ExasolScanBuilderProvider(final ExasolOptions options) {
        this.options = options;
    }

    /**
     * Creates a {@link ScanBuilder} for reading from Exasol database.
     *
     * @param schema user-provided {@link StructType} schema
     * @param map    user-provided key-value options map
     */
    public ScanBuilder createScanBuilder(final StructType schema, final CaseInsensitiveStringMap map) {
        final SparkSession sparkSession = SparkSession.active();
        final String s3Bucket = this.options.getS3Bucket();
        final String s3BucketKey = UUID.randomUUID() + "-" + sparkSession.sparkContext().applicationId();
        LOGGER.info(() -> "Using S3 bucket '" + s3Bucket + "' with folder '" + s3BucketKey + "' for scan job data.");
        setupSparkCleanupJobListener(sparkSession, s3BucketKey);
        exportIntermediateData(s3BucketKey);
        return createCSVScanBuilder(sparkSession, schema, map, s3Bucket, s3BucketKey);
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
            throw new ExasolValidationException(ExaError.messageBuilder("E-EGC-16")
                    .message("Failed to run export query {{exportQuery}} into S3 path {{s3Path}} location.")
                    .parameter("exportQuery", exportQuery)
                    .parameter("s3Path", this.options.getS3Bucket() + "/" + s3BucketKey)
                    .mitigation("Please make sure that the query or table name is correct and obeys SQL syntax rules.")
                    .toString(), exception);
        }
    }

    private ScanBuilder createCSVScanBuilder(final SparkSession spark, final StructType schema,
            final CaseInsensitiveStringMap map, final String s3Bucket, final String s3BucketKey) {
        final CSVTable csvTable = new CSVTable("", spark, map, getS3ScanPath(s3Bucket, s3BucketKey),
                Option.apply(schema), null);
        return csvTable.newScanBuilder(getUpdatedMapWithCSVOptions(map));
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
