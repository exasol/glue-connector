# Exasol AWS Glue Connector 2.1.7, released 2024-07-30

Code name: Fix CVE-2024-25638 in `dnsjava:dnsjava:jar:3.4.0:provided`

## Summary

This release fixes vulnerability CVE-2024-25638 in `dnsjava:dnsjava:jar:3.4.0:provided`.

## Security

* #109: Fixed vulnerability CVE-2024-25638 in `dnsjava:dnsjava:jar:3.4.0:provided`
* #108: Fixed vulnerability CVE-2024-36124 in `org.iq80.snappy:snappy:jar:0.3:test`

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:exasol-jdbc:24.1.0` to `24.1.1`
* Updated `com.exasol:spark-connector-common-java:2.0.4` to `2.0.7`
* Updated `software.amazon.awssdk:s3:2.25.29` to `2.26.25`

### Test Dependency Updates

* Updated `com.amazon.ion:ion-java:1.11.4` to `1.11.9`
* Updated `com.amazonaws:aws-java-sdk-s3:1.12.699` to `1.12.765`
* Updated `com.exasol:exasol-testcontainers:7.0.1` to `7.1.0`
* Added `org.iq80.snappy:snappy:0.5`
* Updated `org.junit.jupiter:junit-jupiter-api:5.10.2` to `5.10.3`
* Updated `org.junit.jupiter:junit-jupiter:5.10.2` to `5.10.3`
* Updated `org.mockito:mockito-core:5.11.0` to `5.12.0`
* Updated `org.mockito:mockito-junit-jupiter:5.11.0` to `5.12.0`
* Updated `org.testcontainers:junit-jupiter:1.19.7` to `1.20.0`
* Updated `org.testcontainers:localstack:1.19.7` to `1.20.0`

### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:4.3.2` to `4.3.3`
