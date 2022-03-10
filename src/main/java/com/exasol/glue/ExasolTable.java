package com.exasol.glue;

import java.util.Set;

import org.apache.spark.sql.connector.catalog.SupportsRead;
import org.apache.spark.sql.connector.catalog.TableCapability;
import org.apache.spark.sql.connector.read.ScanBuilder;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.sql.util.CaseInsensitiveStringMap;

/**
 * Represents an instance of {@link ExasolTable}.
 */
// [impl->dsn~exasoltable-reads-and-writes~1]
// [impl->dsn~sourcescanbuilder-prunes-columns-and-pushes-filters~1]
public class ExasolTable implements SupportsRead {

    private final StructType schema;
    private final ExasolOptions options;
    private final Set<TableCapability> capabilities;

    public ExasolTable(final StructType schema, final ExasolOptions options) {
        this.schema = schema;
        this.options = options;
        this.capabilities = Set.of(TableCapability.BATCH_READ);
    }

    @Override
    public ScanBuilder newScanBuilder(final CaseInsensitiveStringMap options) {
        return null;
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
