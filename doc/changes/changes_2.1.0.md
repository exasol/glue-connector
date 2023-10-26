# Exasol AWS Glue Connector 2.1.0, released 2023-10-27

Code name: Fix vulnerabilities CVE-2023-43642, CVE-2023-39410 and CVE-2023-44981

## Summary

This release fixes vulnerabilities by upgrading transitive dependencies
* CVE-2023-43642 in `org.xerial.snappy:snappy-java`.
* CVE-2023-39410 in `org.apache.avro:avro:jar`.
* CVE-2023-44981 in `org.apache.zookeeper:zookeeper`.

Vulnerability CVE-2023-4586 is ignored for overriden transitive dependency `io.netty:netty-handler:jar:4.1.100.Final` originally via `org.apache.spark:spark-sql_2.12` and `org.apache.zookeeper` as `netty-handler` is not included in the released artifact, but 'provided' by the runtime Spark cluster.

## Features

* #77: Added `spark-connector-common-java` common library
* #80: Fixed vulnerabilities CVE-2023-43642, CVE-2023-39410, CVE-2023-44981

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:error-reporting-java8:0.4.1` to `1.0.1`
* Updated `com.exasol:exasol-jdbc:7.1.17` to `7.1.20`
* Added `com.exasol:spark-connector-common-java:2.0.2`
* Updated `com.exasol:sql-statement-builder-java8:4.5.0` to `4.5.4`
* Removed `com.fasterxml.woodstox:woodstox-core:6.5.0`
* Updated `org.apache.hadoop:hadoop-aws:3.3.4` to `3.3.6`
* Added `org.apache.zookeeper:zookeeper:3.9.1`
* Added `org.xerial.snappy:snappy-java:1.1.10.5`
* Updated `software.amazon.awssdk:s3:2.20.19` to `2.21.8`

### Test Dependency Updates

* Updated `com.amazonaws:aws-java-sdk-s3:1.12.422` to `1.12.574`
* Updated `com.exasol:exasol-testcontainers:6.5.1` to `6.6.2`
* Updated `com.exasol:hamcrest-resultset-matcher:1.5.2` to `1.6.1`
* Updated `com.exasol:test-db-builder-java:3.4.2` to `3.5.1`
* Updated `nl.jqno.equalsverifier:equalsverifier:3.14` to `3.15.2`
* Updated `org.apache.logging.log4j:log4j-api:2.20.0` to `2.21.1`
* Updated `org.apache.logging.log4j:log4j-core:2.20.0` to `2.21.1`
* Updated `org.junit.jupiter:junit-jupiter-api:5.9.2` to `5.10.0`
* Updated `org.junit.jupiter:junit-jupiter:5.9.2` to `5.10.0`
* Updated `org.mockito:mockito-core:5.1.1` to `5.6.0`
* Updated `org.mockito:mockito-junit-jupiter:5.1.1` to `5.6.0`
* Updated `org.testcontainers:junit-jupiter:1.17.6` to `1.19.1`
* Updated `org.testcontainers:localstack:1.17.6` to `1.19.1`

### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.2.2` to `1.3.1`
* Updated `com.exasol:project-keeper-maven-plugin:2.9.3` to `2.9.14`
* Updated `org.apache.maven.plugins:maven-assembly-plugin:3.4.2` to `3.6.0`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.10.1` to `3.11.0`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.1.0` to `3.4.1`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0-M8` to `3.1.2`
* Updated `org.apache.maven.plugins:maven-shade-plugin:3.4.1` to `3.5.0`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M8` to `3.1.2`
* Added `org.basepom.maven:duplicate-finder-maven-plugin:2.0.1`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.3.0` to `1.5.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.14.2` to `2.16.1`
* Updated `org.itsallcode:openfasttrace-maven-plugin:1.6.1` to `1.6.2`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.8` to `0.8.11`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.1.2184` to `3.10.0.2594`
