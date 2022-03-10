# Software Architectural Design -- Exasol Glue Connector

## Introduction

### Terms and Abbreviations

<dl>
    <dt>EGC</dt><dd>Exasol Glue Connector</dd>
    <dt>Apache Spark</dt><dd>An open-source unified analytics engine for large-scale data processing that is used by AWS Glue Studio.</dd>
</dl>

### Requirement Overview

Please refer to the [System Requirement Specification](system_requirements.md) for user-level requirements.

## Solution Strategy

AWS Glue Studio provides an [API](https://docs.aws.amazon.com/glue/latest/ug/connectors-chapter.html#developing-custom-connectors) for building connectors. EGC is an implementation of the [Apache Spark connector](https://github.com/aws-samples/aws-glue-samples/blob/master/GlueCustomConnectors/development/Spark/README.md) for Glue version above `3.0.0`.

## Building Blocks

This section introduces the building blocks of the software. Together those building blocks make up the big picture of the software structure.

### `DefaultSource`

The `DefaultSource` is an entry class that controls interaction with AWS Glue Studio. It creates Spark dataframe and infers its schema.

### `SchemaConverter`

The `SchemaConverter` is a building block that converts data types from Exasol JDBC into Spark data types.

## Runtime View

This section describes the runtime behavior of the software.

### `DefaultSource` infers schema

Covers:

* `req~inferring-schema-of-table-or-query~1`

Needs: impl, utest, itest

### `SchemaConverter` converts data types

Covers:

* `req~converting-exasol-datatype-to-spark-datatype~1`

Needs: impl, utest

## Acknowledgments

This document's section structure is derived from the "[arc42](https://arc42.org/)" architectural template by Dr. Gernot Starke, Dr. Peter Hruschka.