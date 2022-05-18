package com.exasol.glue.writer;

import static com.exasol.glue.Constants.PATH;

import java.util.*;
import java.util.logging.Logger;

import com.exasol.glue.ExasolOptions;

import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.connector.write.LogicalWriteInfo;
import org.apache.spark.sql.connector.write.WriteBuilder;
import org.apache.spark.sql.execution.datasources.v2.csv.CSVTable;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.util.CaseInsensitiveStringMap;

import scala.Option;
import scala.collection.JavaConverters;
import scala.collection.Seq;

public final class ExasolWriteBuilderProvider {
    private static final Logger LOGGER = Logger.getLogger(ExasolWriteBuilderProvider.class.getName());
    private static final String TEMP_DIR = "tempdir";

    private final ExasolOptions options;

    public ExasolWriteBuilderProvider(final ExasolOptions options) {
        this.options = options;
    }

    public WriteBuilder createWriteBuilder(final StructType schema, final LogicalWriteInfo defaultInfo) {
        final SparkSession sparkSession = SparkSession.active();
        final LogicalWriteInfo info = getUpdatedLogicalWriteInfo(defaultInfo, sparkSession);
        final ExasolOptions options = ExasolOptions.builder().from(this.options).withOptionsMap(info.options()).build();
        final String path = options.get(PATH);
        LOGGER.info(() -> "Writing intermediate data to the '" + path + "' path for write job.");
        final CSVTable csvTable = new CSVTable("", sparkSession, info.options(), getS3WritePath(path),
                Option.apply(schema), null);
        return new DelegatingWriteBuilder(options, csvTable.newWriteBuilder(info));
    }

    private LogicalWriteInfo getUpdatedLogicalWriteInfo(final LogicalWriteInfo defaultInfo,
            final SparkSession sparkSession) {
        final Map<String, String> options = new HashMap<>(defaultInfo.options().asCaseSensitiveMap());
        options.put("header", "true");
        options.put("delimiter", ",");
        options.put("mapreduce.fileoutputcommitter.marksuccessfuljobs", "false");
        final String s3Bucket = this.options.getS3Bucket();
        final String s3BucketKey = UUID.randomUUID() + "-" + sparkSession.sparkContext().applicationId();
        final String tempDir = "s3a://" + s3Bucket + "/" + s3BucketKey + "/";
        options.put(TEMP_DIR, tempDir);
        if (tempDir.endsWith("/")) {
            options.put(PATH, tempDir + defaultInfo.queryId());
        } else {
            options.put(PATH, tempDir + "/" + defaultInfo.queryId());
        }

        return new LogicalWriteInfo() {
            @Override
            public String queryId() {
                return defaultInfo.queryId();
            }

            @Override
            public StructType schema() {
                return defaultInfo.schema();
            }

            @Override
            public CaseInsensitiveStringMap options() {
                return new CaseInsensitiveStringMap(options);
            }
        };
    }

    private Seq<String> getS3WritePath(final String path) {
        return JavaConverters.asScalaIteratorConverter(Arrays.asList(path).iterator()).asScala().toSeq();
    }

}
