package com.exasol.glue;

import static com.exasol.glue.Constants.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.exasol.errorreporting.ExaError;
import com.exasol.glue.reader.ExasolScanBuilderProvider;
import com.exasol.glue.writer.ExasolWriteBuilderProvider;

import org.apache.hadoop.conf.Configuration;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.connector.catalog.SupportsRead;
import org.apache.spark.sql.connector.catalog.SupportsWrite;
import org.apache.spark.sql.connector.catalog.TableCapability;
import org.apache.spark.sql.connector.read.ScanBuilder;
import org.apache.spark.sql.connector.write.LogicalWriteInfo;
import org.apache.spark.sql.connector.write.WriteBuilder;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.util.CaseInsensitiveStringMap;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.NoSuchBucketException;

/**
 * Represents an instance of {@link ExasolTable}.
 */
// [impl->dsn~exasoltable-reads-and-writes~1]
public class ExasolTable implements SupportsRead, SupportsWrite {
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
        this.capabilities = new HashSet<>(Arrays.asList(TableCapability.BATCH_READ, TableCapability.BATCH_WRITE));
    }

    @Override
    // [impl->dsn~sourcescanbuilder-prunes-columns-and-pushes-filters~1]
    public ScanBuilder newScanBuilder(final CaseInsensitiveStringMap map) {
        final ExasolOptions options = getExasolOptions(map);
        validate(options);
        updateSparkConfigurationForS3(options);
        return new ExasolScanBuilderProvider(options).createScanBuilder(this.schema, map);
    }

    @Override
    public WriteBuilder newWriteBuilder(final LogicalWriteInfo defaultInfo) {
        final ExasolOptions options = getExasolOptions(defaultInfo.options());
        validate(options);
        validateHasTable(options);
        updateSparkConfigurationForS3(options);
        return new ExasolWriteBuilderProvider(options).createWriteBuilder(this.schema, defaultInfo);
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

    private void validate(final ExasolOptions options) {
        validateS3BucketExists(options);
        validateNumberOfPartitions(options);
    }

    private void validateHasTable(final ExasolOptions options) {
        if (!options.hasTable()) {
            throw new ExasolValidationException(ExaError.messageBuilder("E-EGC-22")
                    .message("Missing 'table' option when writing into Exasol database.")
                    .mitigation("Please set 'table' property with fully qualified "
                            + "(e.g. 'schema_name.table_name') Exasol table name.")
                    .toString());
        }
    }

    private void validateS3BucketExists(final ExasolOptions options) {
        final String s3Bucket = options.getS3Bucket();
        final S3ClientFactory s3ClientFactory = new S3ClientFactory(options);
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

    private void validateNumberOfPartitions(final ExasolOptions options) {
        final int numberOfPartitions = options.getNumberOfPartitions();
        if (numberOfPartitions > MAX_ALLOWED_NUMBER_OF_PARTITIONS) {
            throw new ExasolValidationException(ExaError.messageBuilder("E-EGC-21")
                    .message("The number of partitions is larger than maximum allowed {{MAXPARTITIONS}} value.",
                            String.valueOf(MAX_ALLOWED_NUMBER_OF_PARTITIONS))
                    .mitigation("Please set the number of partitions parameter to a lower value.").toString());
        }
    }

    private void updateSparkConfigurationForS3(final ExasolOptions options) {
        final SparkSession sparkSession = SparkSession.active();
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

}
