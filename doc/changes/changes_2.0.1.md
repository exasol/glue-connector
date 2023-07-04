# Exasol AWS Glue Connector 2.0.1, released 2023-??-??

Code name:

## Summary

This release fixes the following vulnerabilities by updating dependencies:
* `io.netty:netty-handler:jar:4.1.92.Final` (provided)
  * CVE-2023-34462, severity CWE-770: Allocation of Resources Without Limits or Throttling (6.5)
* `org.codehaus.janino:janino:jar:3.0.16` (provided)
  * CVE-2023-33546, severity CWE-787: Out-of-bounds Write (5.5)
* `org.xerial.snappy:snappy-java:jar:1.1.2.4` (provided)
  * CVE-2023-34454, severity CWE-190: Integer Overflow or Wraparound (7.5)
  * CVE-2023-34455, severity CWE-770: Allocation of Resources Without Limits or Throttling (7.5)
* `com.google.guava:guava:jar:31.1-jre` (provided)
  * CVE-2023-2976, severity CWE-552: Files or Directories Accessible to External Parties (7.1)

## Refactoring

* #69: Updated issue template for reminding to publish to AWS Marketplace

## Security

* #71: Fixed dependency check vulnerability findings
* #75: Fixed dependency check vulnerability findings

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:error-reporting-java8:0.4.1` to `1.0.1`
* Updated `com.exasol:exasol-jdbc:7.1.17` to `7.1.20`
* Updated `com.exasol:sql-statement-builder-java8:4.5.0` to `4.5.4`
* Removed `com.fasterxml.woodstox:woodstox-core:6.5.0`
* Updated `org.apache.hadoop:hadoop-aws:3.3.4` to `3.3.6`
* Added `org.xerial.snappy:snappy-java:1.1.10.1`
* Updated `software.amazon.awssdk:s3:2.20.19` to `2.20.98`

### Test Dependency Updates

* Updated `com.amazonaws:aws-java-sdk-s3:1.12.422` to `1.12.501`
* Updated `com.exasol:exasol-testcontainers:6.5.1` to `6.6.0`
* Updated `com.exasol:hamcrest-resultset-matcher:1.5.2` to `1.6.0`
* Updated `nl.jqno.equalsverifier:equalsverifier:3.14` to `3.14.3`
* Updated `org.junit.jupiter:junit-jupiter-api:5.9.2` to `5.9.3`
* Updated `org.junit.jupiter:junit-jupiter:5.9.2` to `5.9.3`
* Updated `org.mockito:mockito-core:5.1.1` to `5.4.0`
* Updated `org.mockito:mockito-junit-jupiter:5.1.1` to `5.4.0`
* Updated `org.testcontainers:junit-jupiter:1.17.6` to `1.18.3`
* Updated `org.testcontainers:localstack:1.17.6` to `1.18.3`

### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.2.2` to `1.2.3`
* Updated `com.exasol:project-keeper-maven-plugin:2.9.3` to `2.9.7`
* Updated `org.apache.maven.plugins:maven-assembly-plugin:3.4.2` to `3.5.0`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.10.1` to `3.11.0`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.1.0` to `3.3.0`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0-M8` to `3.0.0`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M8` to `3.0.0`
* Added `org.basepom.maven:duplicate-finder-maven-plugin:1.5.1`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.3.0` to `1.4.1`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.14.2` to `2.15.0`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.8` to `0.8.9`
