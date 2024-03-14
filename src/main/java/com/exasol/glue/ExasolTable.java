package com.exasol.glue;

import java.util.*;

import org.apache.hadoop.conf.Configuration;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.connector.catalog.*;
import org.apache.spark.sql.connector.read.ScanBuilder;
import org.apache.spark.sql.connector.write.LogicalWriteInfo;
import org.apache.spark.sql.connector.write.WriteBuilder;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.util.CaseInsensitiveStringMap;

import com.exasol.errorreporting.ExaError;
import com.exasol.glue.filesystem.S3FileSystem;
import com.exasol.glue.reader.ExasolScanBuilder;
import com.exasol.glue.writer.ExasolWriteBuilderProvider;
import com.exasol.spark.common.*;

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
     * @param schema user provided schema
     */
    public ExasolTable(final StructType schema) {
        this.schema = schema;
        this.capabilities = new HashSet<>(Arrays.asList(TableCapability.BATCH_READ, TableCapability.BATCH_WRITE));
    }

    @Override
    // [impl->dsn~sourcescanbuilder-prunes-columns-and-pushes-filters~1]
    public ScanBuilder newScanBuilder(final CaseInsensitiveStringMap map) {
        final ExasolOptions options = new ExasolOptionsProvider().fromJdbcUrl(map);
        validate(options);
        updateSparkConfigurationForS3(options);
        return new ExasolScanBuilder(options, this.schema, map);
    }

    @Override
    public WriteBuilder newWriteBuilder(final LogicalWriteInfo defaultInfo) {
        final ExasolOptions options = new ExasolOptionsProvider().fromJdbcUrl(defaultInfo.options());
        validate(options);
        validateHasTableForWrite(options);
        updateSparkConfigurationForS3(options);
        return new ExasolWriteBuilderProvider(options).createWriteBuilder(this.schema, defaultInfo);
    }

    @Override
    public String name() {
        return "ExasolTable";
    }

    @SuppressWarnings("deprecation")
    @Override
    public StructType schema() {
        return this.schema;
    }

    @Override
    public Column[] columns() {
        return CatalogV2Util.structTypeToV2Columns(this.schema);
    }

    @Override
    public Set<TableCapability> capabilities() {
        return this.capabilities;
    }

    private void validate(final ExasolOptions options) {
        validateS3BucketExists(options);
        validateNumberOfPartitions(options);
    }

    private void validateHasTableForWrite(final ExasolOptions options) {
        if (!options.hasTable()) {
            throw new ExasolValidationException(ExaError.messageBuilder("E-EGC-21")
                    .message("Missing 'table' option when writing into Exasol database.")
                    .mitigation("Please set 'table' property with fully qualified "
                            + "(e.g. 'schema_name.table_name') Exasol table name.")
                    .toString());
        }
    }

    private void validateS3BucketExists(final ExasolOptions options) {
        final String s3Bucket = options.getS3Bucket();
        try (final S3FileSystem s3FileSystem = new S3FileSystem(options)) {
            if (!s3FileSystem.doesBucketExist(s3Bucket)) {
                throw new ExasolValidationException(ExaError.messageBuilder("E-EGC-28")
                        .message("Provided S3 bucket {{s3Bucket}} is not available.", s3Bucket)
                        .mitigation("Please create a bucket or provide an existing bucket name.").toString());

            }
        }
    }

    private void validateNumberOfPartitions(final ExasolOptions options) {
        final int numberOfPartitions = options.getNumberOfPartitions();
        if (numberOfPartitions > MAX_ALLOWED_NUMBER_OF_PARTITIONS) {
            throw new ExasolValidationException(ExaError.messageBuilder("E-EGC-23")
                    .message("The number of partitions is larger than maximum allowed {{MAXPARTITIONS}} value.",
                            String.valueOf(MAX_ALLOWED_NUMBER_OF_PARTITIONS))
                    .mitigation("Please set the number of partitions parameter to a lower value.").toString());
        }
    }

    private void updateSparkConfigurationForS3(final ExasolOptions options) {
        final SparkSession sparkSession = SparkSession.active();
        synchronized (sparkSession.sparkContext().hadoopConfiguration()) {
            final Configuration conf = sparkSession.sparkContext().hadoopConfiguration();
            conf.set("fs.s3a.access.key", options.get(Option.AWS_ACCESS_KEY_ID.key()));
            conf.set("fs.s3a.secret.key", options.get(Option.AWS_SECRET_ACCESS_KEY.key()));
            if (options.containsKey(Option.AWS_CREDENTIALS_PROVIDER.key())) {
                conf.set("fs.s3a.aws.credentials.provider", options.get(Option.AWS_CREDENTIALS_PROVIDER.key()));
            } else {
                conf.set("fs.s3a.aws.credentials.provider", "org.apache.hadoop.fs.s3a.SimpleAWSCredentialsProvider");
            }
            if (options.containsKey(Option.S3_ENDPOINT_OVERRIDE.key())) {
                conf.set("fs.s3a.endpoint", getEndpointOverride(options));
            }
            if (options.hasEnabled(Option.S3_PATH_STYLE_ACCESS.key())) {
                conf.set("fs.s3a.path.style.access", "true");
            }
        }
    }

    private String getEndpointOverride(final ExasolOptions options) {
        String scheme = "https";
        if (options.containsKey(Option.AWS_USE_SSL.key()) && !options.hasEnabled(Option.AWS_USE_SSL.key())) {
            scheme = "http";
        }
        return scheme + "://" + options.get(Option.S3_ENDPOINT_OVERRIDE.key());
    }

}
