# Exasol AWS Glue Connector 1.1.0, released 2023-??-??

Code name: AWS Glue Version 4.0.0

## Summary

## Refactorings

- #66: Updated to AWS Glue version `4.0.0`
- #64: Updated dependencies and removed references to `maven.exasol.com` repository

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:exasol-jdbc:7.1.11` to `7.1.17`
* Added `com.fasterxml.woodstox:woodstox-core:6.5.0`
* Updated `software.amazon.awssdk:s3:2.18.4` to `2.20.14`

### Test Dependency Updates

* Updated `com.amazonaws:AWSGlueETL:3.0.0` to `4.0.0`
* Updated `com.amazonaws:aws-java-sdk-s3:1.12.329` to `1.12.417`
* Updated `com.exasol:exasol-testcontainers:6.3.0` to `6.5.1`
* Updated `com.exasol:java-util-logging-testing:2.0.2` to `2.0.3`
* Updated `com.exasol:test-db-builder-java:3.4.1` to `3.4.2`
* Removed `log4j:log4j:1.2.17`
* Updated `nl.jqno.equalsverifier:equalsverifier:3.10.1` to `3.14`
* Added `org.apache.logging.log4j:log4j-api:2.19.0`
* Added `org.apache.logging.log4j:log4j-core:2.19.0`
* Updated `org.junit.jupiter:junit-jupiter-api:5.9.1` to `5.9.2`
* Updated `org.junit.jupiter:junit-jupiter:5.9.1` to `5.9.2`
* Updated `org.mockito:mockito-core:4.8.1` to `5.1.1`
* Updated `org.mockito:mockito-junit-jupiter:4.8.1` to `5.1.1`
* Updated `org.testcontainers:junit-jupiter:1.17.5` to `1.17.6`
* Updated `org.testcontainers:localstack:1.17.5` to `1.17.6`

### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.1.2` to `1.2.2`
* Updated `com.exasol:project-keeper-maven-plugin:2.8.0` to `2.9.3`
* Updated `io.github.zlika:reproducible-build-maven-plugin:0.15` to `0.16`
* Updated `org.apache.maven.plugins:maven-assembly-plugin:3.3.0` to `3.4.2`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0-M5` to `3.0.0-M8`
* Updated `org.apache.maven.plugins:maven-jar-plugin:3.2.2` to `3.3.0`
* Updated `org.apache.maven.plugins:maven-shade-plugin:3.4.0` to `3.4.1`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M5` to `3.0.0-M8`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.2.7` to `1.3.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.10.0` to `2.14.2`
