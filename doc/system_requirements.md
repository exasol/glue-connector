# System Requirement Specification for Exasol Glue Connector

## Introduction

Exasol Glue Connector (EGC) is a connector for AWS Glue Studio. It provides an extended connector for AWS Glue Studio with Exasol capabilities to read data from and write data to an Exasol database.

## About This Document

### Target Audience

The target audience are users, software engineers, data engineers and quality assurance engineers that build data pipelines and transform data using AWS Glue Studio. See section ["Stakeholders"](#stakeholders) for more details.

### Goal

The goal of EGC is to provide a connector so that users can integrate Exasol database into their AWS Glue Studio workflows. It enables reading data from Exasol as a source and loading data from other sources supported by AWS Glue Studio into an Exasol database.

## Stakeholders

### Data Engineers

Data Engineers are users who create and maintain AWS Glue Studio workflows.

### Quality Assurance Engineers

Quality Assurance (QA) Engineers are users who monitor and validate results of AWS Glue Studio workflows.

### Software Engineers

Software Engineers are users who implement, test and deploy AWS Glue Studio workflows.

## Terms and Abbreviations

The following list gives you an overview of terms and abbreviations commonly used in the EGC documents.

<dl>
<dt>EGC</dt><dd>Exasol Glue Connector</dd>
<dt>ETL</dt><dd>Extraction, Transformation, Load &mdash; process of getting data from a source, converting the data and saving it into a sink.</dd>
<dt>Schema</dt><dd>Data describing the structure of data in source or sink with name and type of columns.</dd>
<dt>Apache Spark</dt><dd>An open-source unified analytics engine for large-scale data processing that is used by AWS Glue Studio.</dd>
</dl>

## Features

Features are the highest level requirements in this document that describe the main functionality of Exasol AWS Glue Connector.

### Schema Inference
`feat~schema-inference~1`

EGC allows schema inference of an Exasol table or query.

Rationale:

Users of AWS Glue Studio should be able to define the structure of an Exasol table or of a requested query.

Needs: req

### Reading Data
`feat~reading-data~1`

EGC allows efficiently reading data from an Exasol database.

Needs: req

### Writing Data
`feat~writing-data~1`

EGC allows saving data from AWS Glue Studio into Exasol tables.

Needs: req

### ETL Transformation
`feat~etl-transformation~1`

EGC supports pushing ETL transformation into an Exasol database.

Rationale:

By pushing certain transformations into an Exasol database, we remove unnecessary ETL work later on in AWS Glue Studio which improves efficiency of workflows.

For example:

- pushing down column selection, e.g, `.select(col("c1"))`
- pushing down filters, e.g, `.where(col("c1") > 10)`

## Functional Requirements

This section lists functional requirements from a user's perspective. Each functional requirement belongs to a single feature.

### Inferring Schema of a Table or Query
`req~inferring-schema-of-table-or-query~1`

EGC converts table or query metadata into an AWS Glue Studio schema.

Covers:

- [`feat~schema-inference~1`](#schema-inference)

Needs: dsn

### Converting Exasol Data Type to Spark Data Type
`req~converting-exasol-datatype-to-spark-datatype~1`

EGC converts Exasol datatypes into Apache Spark datatypes.

Covers:

- [`feat~schema-inference~1`](#schema-inference)

Needs: dsn

### Reading From Exasol Table
`req~reading-from-exasol-table~1`

EGC reads data of the provided Exasol table.

Covers:

- [`feat~reading-data~1`](#reading-data)

Needs: dsn

### Reading From Exasol Query
`req~reading-from-exasol-query~1`

EGC reads data of the provided Exasol query.

Rationale:

This allows users to provide an Exasol query as a basis for the further ETL processing.

For example:

```
.option("query", "SELECT PRODUCT_ID, SALE_DATE FROM RETAIL.SALES")
```

Covers:

- [`feat~reading-data~1`](#reading-data)

Needs: dsn

### Saving Data Into Exasol Table
`req~saving-data-into-exasol-table~1`

EGC writes data from AWS Glue Studio into an Exasol table.

Covers:

- [`feat~writing-data~1`](#writing-data)

Needs: dsn

### Pruning Columns
`req~pruning-columns~1`

EGC pushes column selections from ETL transformations into an Exasol database.

Rationale:

This avoids transferring unnecessary data through the network. It allows efficient network utilization and saves Random Access Memory (RAM) space.

Covers:

- [`feat~etl-transformation~1`](#etl-transformation)

Needs: dsn

### Pushing Filters
`req~pushing-filters~1`

EGC pushes filter conditions into an Exasol database when reading data from it.

Rationale:

This avoids transferring unnecessary data through the network. It allows efficient network utilization and saves Random Access Memory (RAM) space.

Covers:

- [`feat~etl-transformation~1`](#etl-transformation)

Needs: dsn
