# Exasol AWS Glue Connector

[![Build Status](https://github.com/exasol/glue-connector/actions/workflows/ci-build.yml/badge.svg)](https://github.com/exasol/glue-connector/actions/workflows/ci-build.yml)

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aglue-connector&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.exasol%3Aglue-connector)

[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aglue-connector&metric=security_rating)](https://sonarcloud.io/dashboard?id=com.exasol%3Aglue-connector)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aglue-connector&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=com.exasol%3Aglue-connector)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aglue-connector&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=com.exasol%3Aglue-connector)
[![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aglue-connector&metric=sqale_index)](https://sonarcloud.io/dashboard?id=com.exasol%3Aglue-connector)

[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aglue-connector&metric=code_smells)](https://sonarcloud.io/dashboard?id=com.exasol%3Aglue-connector)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aglue-connector&metric=coverage)](https://sonarcloud.io/dashboard?id=com.exasol%3Aglue-connector)
[![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aglue-connector&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=com.exasol%3Aglue-connector)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=com.exasol%3Aglue-connector&metric=ncloc)](https://sonarcloud.io/dashboard?id=com.exasol%3Aglue-connector)

An AWS Glue connector that allow accessing Exasol database in Glue Studio.

## Features

* Allows mapping Exasol table or query metadata to AWS Glue Studio Spark schema
* Allows reading from an Exasol table or query
* Allows pushing pruning of columns from Glue dataframe to Exasol database
* Allows pushing filters from Glue dataframe to Exasol database
* Allows saving AWS Glue Studio dataframe into an Exasol table

## Information for Users

* [User Guide](doc/user_guide/user_guide.md)
* [Changelog](doc/changes/changelog.md)

## Information for Developers

* [Developers Guide](doc/developers_guide/developers_guide.md)
* [Dependencies](dependencies.md)
