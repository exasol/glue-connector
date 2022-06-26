# Exasol AWS Glue Connector 0.1.0, released 2022-??-??

Code name:

## Features

* #2: Added requirements and design documentation
* #5: Added initial project setup
* #6: Added schema inference
* #7: Added reading from Exasol table or query
* #9: Added support for column projection
* #10: Added support for pushing predicates
* #11: Added support using reserved identifiers in queries
* #12: Added writing into Exasol table

## Documentation

* #3: Added user guide

## Refactoring

* #14: Migrated to project-keeper version 2
* #17: Refactored to use sql-statement-builder for generating queries
* #25: Added AWS Glue local validation tests
* #35: Refactored error codes, removed duplicated error tags
* #36: Added unified S3 filesystem operations interface

## Dependency Updates

### Compile Dependency Updates

* Added `com.exasol:error-reporting-java8:0.4.1`
* Added `com.exasol:exasol-jdbc:7.1.11`
* Added `com.exasol:sql-statement-builder-java8:4.5.0`
* Added `com.thoughtworks.paranamer:paranamer:2.8`
* Added `org.apache.hadoop:hadoop-aws:3.3.2`
* Added `software.amazon.awssdk:s3:2.17.219`

### Test Dependency Updates

* Added `com.amazonaws:AWSGlueETL:3.0.0`
* Added `com.amazonaws:aws-java-sdk-s3:1.12.248`
* Added `com.exasol:exasol-testcontainers:6.1.2`
* Added `com.exasol:hamcrest-resultset-matcher:1.5.1`
* Added `com.exasol:java-util-logging-testing:2.0.1`
* Added `com.exasol:test-db-builder-java:3.3.3`
* Added `log4j:log4j:1.2.17`
* Added `nl.jqno.equalsverifier:equalsverifier:3.10`
* Added `org.hamcrest:hamcrest:2.2`
* Added `org.junit.jupiter:junit-jupiter:5.8.2`
* Added `org.mockito:mockito-core:4.6.1`
* Added `org.mockito:mockito-junit-jupiter:4.6.1`
* Added `org.testcontainers:junit-jupiter:1.17.2`
* Added `org.testcontainers:localstack:1.17.2`

### Plugin Dependency Updates

* Added `com.exasol:artifact-reference-checker-maven-plugin:0.4.0`
* Added `com.exasol:error-code-crawler-maven-plugin:1.1.1`
* Added `com.exasol:project-keeper-maven-plugin:2.4.6`
* Added `io.github.zlika:reproducible-build-maven-plugin:0.15`
* Added `org.apache.maven.plugins:maven-assembly-plugin:3.3.0`
* Added `org.apache.maven.plugins:maven-clean-plugin:2.5`
* Added `org.apache.maven.plugins:maven-compiler-plugin:3.10.1`
* Added `org.apache.maven.plugins:maven-deploy-plugin:2.7`
* Added `org.apache.maven.plugins:maven-enforcer-plugin:3.0.0`
* Added `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0-M5`
* Added `org.apache.maven.plugins:maven-install-plugin:2.4`
* Added `org.apache.maven.plugins:maven-jar-plugin:3.2.2`
* Added `org.apache.maven.plugins:maven-resources-plugin:2.6`
* Added `org.apache.maven.plugins:maven-shade-plugin:3.3.0`
* Added `org.apache.maven.plugins:maven-site-plugin:3.3`
* Added `org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M5`
* Added `org.codehaus.mojo:flatten-maven-plugin:1.2.7`
* Added `org.codehaus.mojo:versions-maven-plugin:2.10.0`
* Added `org.itsallcode:openfasttrace-maven-plugin:1.5.0`
* Added `org.jacoco:jacoco-maven-plugin:0.8.8`
* Added `org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184`
* Added `org.sonatype.ossindex.maven:ossindex-maven-plugin:3.2.0`
