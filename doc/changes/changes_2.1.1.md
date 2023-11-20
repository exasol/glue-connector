# Exasol AWS Glue Connector 2.1.1, released 2023-11-20

Code name: Fix CVE-2023-4043 in test dependency `org.eclipse.parsson:parsson`

## Summary

This release fixes vulnerability CVE-2023-4043 in test dependency `org.eclipse.parsson:parsson`.

## Security

* #82: Fixed CVE-2023-4043 in test dependency `org.eclipse.parsson:parsson`

## Dependency Updates

### Compile Dependency Updates

* Updated `software.amazon.awssdk:s3:2.21.8` to `2.21.26`

### Test Dependency Updates

* Updated `com.amazonaws:aws-java-sdk-s3:1.12.574` to `1.12.592`
* Updated `com.exasol:exasol-testcontainers:6.6.2` to `6.6.3`
* Updated `com.exasol:hamcrest-resultset-matcher:1.6.1` to `1.6.2`
* Updated `com.exasol:test-db-builder-java:3.5.1` to `3.5.2`
* Updated `nl.jqno.equalsverifier:equalsverifier:3.15.2` to `3.15.3`
* Updated `org.apache.logging.log4j:log4j-api:2.21.1` to `2.22.0`
* Updated `org.apache.logging.log4j:log4j-core:2.21.1` to `2.22.0`
* Updated `org.junit.jupiter:junit-jupiter-api:5.10.0` to `5.10.1`
* Updated `org.junit.jupiter:junit-jupiter:5.10.0` to `5.10.1`
* Updated `org.mockito:mockito-core:5.6.0` to `5.7.0`
* Updated `org.mockito:mockito-junit-jupiter:5.6.0` to `5.7.0`
* Updated `org.testcontainers:junit-jupiter:1.19.1` to `1.19.2`
* Updated `org.testcontainers:localstack:1.19.1` to `1.19.2`

### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:2.9.14` to `2.9.16`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.1.2` to `3.2.2`
* Updated `org.apache.maven.plugins:maven-shade-plugin:3.5.0` to `3.5.1`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.1.2` to `3.2.2`
