# Exasol AWS Glue Connector 2.1.2, released 2023-12-18

Code name: Fix CVE-2023-6378 in `logback` dependencies

## Summary

This release fixes CVE-2023-6378 in dependencies `ch.qos.logback/logback-core@1.2.10` and `ch.qos.logback/logback-classic@1.2.10` with scope `provided`.

## Security

* #84: Fixed CVE-2023-6378 in `ch.qos.logback/logback-core@1.2.10`
* #85: Fixed CVE-2023-6378 in `ch.qos.logback/logback-classic@1.2.10`

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:spark-connector-common-java:2.0.2` to `2.0.3`
* Updated `software.amazon.awssdk:s3:2.21.26` to `2.22.0`

### Test Dependency Updates

* Updated `com.amazonaws:aws-java-sdk-s3:1.12.592` to `1.12.620`
* Updated `com.exasol:exasol-testcontainers:6.6.3` to `7.0.0`
* Updated `com.exasol:hamcrest-resultset-matcher:1.6.2` to `1.6.3`
* Updated `com.exasol:test-db-builder-java:3.5.2` to `3.5.3`
* Updated `nl.jqno.equalsverifier:equalsverifier:3.15.3` to `3.15.4`
* Updated `org.mockito:mockito-core:5.7.0` to `5.8.0`
* Updated `org.mockito:mockito-junit-jupiter:5.7.0` to `5.8.0`
* Updated `org.testcontainers:junit-jupiter:1.19.2` to `1.19.3`
* Updated `org.testcontainers:localstack:1.19.2` to `1.19.3`

### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:2.9.16` to `2.9.17`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.16.1` to `2.16.2`
