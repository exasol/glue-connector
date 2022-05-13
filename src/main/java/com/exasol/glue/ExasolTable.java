package com.exasol.glue;

import static com.exasol.glue.Constants.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

import com.exasol.errorreporting.ExaError;
import com.exasol.glue.connection.ExasolConnectionFactory;
import com.exasol.glue.listener.ExasolJobEndListener;
import com.exasol.glue.reader.ExportQueryGenerator;
import com.exasol.glue.reader.ExportQueryRunner;

import org.apache.hadoop.conf.Configuration;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.connector.catalog.SupportsRead;
import org.apache.spark.sql.connector.catalog.TableCapability;
import org.apache.spark.sql.connector.read.ScanBuilder;
import org.apache.spark.sql.execution.datasources.v2.csv.CSVTable;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.util.CaseInsensitiveStringMap;

import scala.Option;
import scala.collection.JavaConverters;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;

/**
 * Represents an instance of {@link ExasolTable}.
 */
// [impl->dsn~exasoltable-reads-and-writes~1]
// [impl->dsn~sourcescanbuilder-prunes-columns-and-pushes-filters~1]
public class ExasolTable implements SupportsRead {
    private static final Logger LOGGER = Logger.getLogger(ExasolTable.class.getName());
    private static final int MAX_ALLOWED_NUMBER_OF_PARTITIONS = 1000;

    private final StructType schema;
    private final Set<TableCapability> capabilities;

    /**
     * Creates a new instance of {@link ExasolOptions}.
     *
     * @param schema a user provided schema
     */
    public ExasolTable(final StructType schema) {
        this.schema = schema;
        this.capabilities = new HashSet<>(Arrays.asList(TableCapability.BATCH_READ));
    }

    @Override
    public ScanBuilder newScanBuilder(final CaseInsensitiveStringMap map) {
        final ExasolOptions options = getExasolOptions(map);
        final S3ClientFactory s3ClientFactory = new S3ClientFactory(options);
        final SparkSession sparkSession = SparkSession.active();
        final String s3Bucket = options.getS3Bucket();
        final String s3BucketKey = UUID.randomUUID() + "-" + sparkSession.sparkContext().applicationId();
        LOGGER.info(() -> "Using bucket '" + s3Bucket + "' with folder '" + s3BucketKey + "' for job data.");
        validateS3BucketExists(s3ClientFactory, s3Bucket);
        runExportQuery(options, s3BucketKey);
        setupSparkContextForS3(sparkSession, options);
        setupSparkContextForJobListener(sparkSession, options, s3BucketKey);
        final List<String> path = Arrays.asList(getS3Path(s3Bucket, s3BucketKey));
        final CSVTable csvTable = new CSVTable("", //
                sparkSession, //
                map, //
                JavaConverters.asScalaIteratorConverter(path.iterator()).asScala().toSeq(), //
                Option.apply(this.schema), //
                null);
        return csvTable.newScanBuilder(updateMapWithCSVOptions(map));
    }

    private String getS3Path(final String s3Bucket, final String s3BucketKey) {
        return "s3a://" + s3Bucket + "/" + s3BucketKey + "/*.csv";
    }

    private void validateS3BucketExists(final S3ClientFactory s3ClientFactory, final String s3Bucket) {
        try (final S3Client s3Client = s3ClientFactory.getS3Client()) {
            s3Client.headBucket(HeadBucketRequest.builder().bucket(s3Bucket).build());
        } catch (final NoSuchBucketException exception) {
            throw new ExasolValidationException(
                    ExaError.messageBuilder("E-EGC-15")
                            .message("Provided S3 bucket {{s3Bucket}} is not available.", s3Bucket)
                            .mitigation("Please create a bucket or provide an existing bucket name.").toString(),
                    exception);

        }
    }

    private int runExportQuery(final ExasolOptions options, final String s3BucketKey) {
        final int numberOfPartitions = options.getNumberOfPartitions();
        validateNumberOfPartitions(numberOfPartitions);
        final String exportQuery = new ExportQueryGenerator(options).generateExportQuery(s3BucketKey,
                numberOfPartitions);
        try (final Connection connection = new ExasolConnectionFactory(options).getConnection()) {
            return new ExportQueryRunner(connection).runExportQuery(exportQuery);
        } catch (final SQLException exception) {
            throw new ExasolValidationException(ExaError.messageBuilder("E-EGC-16")
                    .message("Failed to run export query {{exportQuery}} into S3 path {{s3Path}} location.")
                    .parameter("exportQuery", exportQuery)
                    .parameter("s3Path", options.getS3Bucket() + "/" + s3BucketKey)
                    .mitigation("Please make sure that the query or table name is correct and obeys SQL syntax rules.")
                    .toString(), exception);
        }
    }

    private void validateNumberOfPartitions(final int numberOfPartitions) {
        if (numberOfPartitions > MAX_ALLOWED_NUMBER_OF_PARTITIONS) {
            throw new ExasolValidationException(ExaError.messageBuilder("E-EGC-21")
                    .message("The number of partitions is larger than maximum allowed {{MAXPARTITIONS}} value.",
                            String.valueOf(MAX_ALLOWED_NUMBER_OF_PARTITIONS))
                    .mitigation("Please set the number of partitions parameter to lower value.").toString());
        }
    }

    private void setupSparkContextForS3(final SparkSession sparkSession, final ExasolOptions options) {
        final Configuration conf = sparkSession.sparkContext().hadoopConfiguration();
        if (options.hasEnabled(CI_ENABLED)) {
            conf.set("fs.s3a.aws.credentials.provider", "org.apache.hadoop.fs.s3a.SimpleAWSCredentialsProvider");
            conf.set("fs.s3a.access.key", options.get(AWS_ACCESS_KEY_ID));
            conf.set("fs.s3a.secret.key", options.get(AWS_SECRET_ACCESS_KEY));
        }
        if (options.containsKey(S3_ENDPOINT_OVERRIDE)) {
            conf.set("fs.s3a.endpoint", "http://" + options.get(S3_ENDPOINT_OVERRIDE));
        }
        if (options.hasEnabled(S3_PATH_STYLE_ACCESS)) {
            conf.set("fs.s3a.path.style.access", "true");
        }
    }

    private void setupSparkContextForJobListener(final SparkSession sparkSession, final ExasolOptions options,
            final String bucketKey) {
        sparkSession.sparkContext().addSparkListener(new ExasolJobEndListener(options, bucketKey));
    }

    private ExasolOptions getExasolOptions(final CaseInsensitiveStringMap options) {
        final ExasolOptions.Builder builder = ExasolOptions.builder() //
                .jdbcUrl(options.get(JDBC_URL)) //
                .username(options.get(USERNAME)) //
                .password(options.get(PASSWORD)) //
                .s3Bucket(options.get(S3_BUCKET));
        if (options.containsKey(TABLE)) {
            builder.table(options.get(TABLE));
        } else if (options.containsKey(QUERY)) {
            builder.query(options.get(QUERY));
        }
        return builder.withOptionsMap(options.asCaseSensitiveMap()).build();
    }

    private CaseInsensitiveStringMap updateMapWithCSVOptions(final CaseInsensitiveStringMap map) {
        final Map<String, String> updatedMap = new HashMap<>(map.asCaseSensitiveMap());
        updatedMap.put("header", "true");
        updatedMap.put("delimiter", ",");
        return new CaseInsensitiveStringMap(updatedMap);
    }

    @Override
    public String name() {
        return "ExasolTable";
    }

    @Override
    public StructType schema() {
        return schema;
    }

    @Override
    public Set<TableCapability> capabilities() {
        return capabilities;
    }

}
