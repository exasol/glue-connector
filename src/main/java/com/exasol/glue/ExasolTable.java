package com.exasol.glue;

import static com.exasol.glue.Constants.*;

import java.util.Set;

import com.exasol.glue.reader.SourceScanBuilder;

import org.apache.spark.sql.connector.catalog.SupportsRead;
import org.apache.spark.sql.connector.catalog.TableCapability;
import org.apache.spark.sql.connector.read.ScanBuilder;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.util.CaseInsensitiveStringMap;

/**
 * Represents an instance of {@link ExasolTable}.
 */
public class ExasolTable implements SupportsRead {

    private final StructType schema;
    private Set<TableCapability> capabilities;

    /**
     * Creates an instance of {@link ExasolTable}.
     *
     * @param schema schema of table or final query result
     */
    public ExasolTable(final StructType schema) {
        this.schema = schema;
        this.capabilities = Set.of(TableCapability.BATCH_READ);
    }

    @Override
    public ScanBuilder newScanBuilder(final CaseInsensitiveStringMap options) {
        return new SourceScanBuilder(this.schema, getExasolOptions(options));
    }

    private ExasolOptions getExasolOptions(final CaseInsensitiveStringMap options) {
        final ExasolOptions.Builder builder = ExasolOptions.builder() //
                .jdbcUrl(options.get(JDBC_URL)) //
                .username(options.get(USERNAME)) //
                .password(options.get(PASSWORD));
        if (options.containsKey(TABLE)) {
            builder.table(options.get(TABLE));
        } else if (options.containsKey(QUERY)) {
            builder.query(options.get(QUERY));
        }
        return builder.build();
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
