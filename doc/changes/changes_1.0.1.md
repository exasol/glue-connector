# Exasol AWS Glue Connector 1.0.1, released 2022-09-28

Code name: Updated dependencies

## Summary

## Refactoring

* PR #42: Fixed docker image preparation Github action issues

## Bugfixes

* #55: Fixed vulnerabilities in dependencies

## Dependency Updates

### Compile Dependency Updates

* Updated `software.amazon.awssdk:s3:2.17.243` to `2.17.283`

### Test Dependency Updates

* Updated `com.amazonaws:aws-java-sdk-s3:1.12.272` to `1.12.312`
* Updated `com.exasol:exasol-testcontainers:6.1.2` to `6.2.0`
* Updated `com.exasol:hamcrest-resultset-matcher:1.5.1` to `1.5.2`
* Updated `com.exasol:java-util-logging-testing:2.0.1` to `2.0.2`
* Updated `com.exasol:test-db-builder-java:3.3.3` to `3.3.4`
* Added `org.junit.jupiter:junit-jupiter-api:5.9.1`
* Updated `org.junit.jupiter:junit-jupiter:5.8.2` to `5.9.1`
* Updated `org.mockito:mockito-core:4.6.1` to `4.8.0`
* Updated `org.mockito:mockito-junit-jupiter:4.6.1` to `4.8.0`

### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.1.1` to `1.1.2`
* Updated `com.exasol:project-keeper-maven-plugin:2.5.0` to `2.8.0`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.0.0` to `3.1.0`
* Updated `org.itsallcode:openfasttrace-maven-plugin:1.5.0` to `1.6.1`
