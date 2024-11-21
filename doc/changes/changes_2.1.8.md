# Exasol AWS Glue Connector 2.1.8, released 2024-11-21

Code name: Fixed vulnerabilities CVE-2024-47535, CVE-2024-51504, CVE-2024-47554, CVE-2024-47561

## Summary

This release fixes the following vulnerabilities:
* CVE-2024-47535 in `io.netty:netty-common:jar:4.1.105.Final:compile`
* CVE-2024-51504 in `org.apache.zookeeper:zookeeper:jar:3.9.2:provided`
* CVE-2024-47554 in `commons-io:commons-io:jar:2.11.0:provided`
* CVE-2024-47561 in `org.apache.avro:avro:jar:1.11.3:provided`

## Security

* #118: Fixed CVE-2024-47535 in `io.netty:netty-common:jar:4.1.105.Final:compile`
* #116: Fixed CVE-2024-51504 in `org.apache.zookeeper:zookeeper:jar:3.9.2:provided`
* #114: Fixed CVE-2024-47554 in `commons-io:commons-io:jar:2.11.0:provided`
* #113: Fixed CVE-2024-47561 in `org.apache.avro:avro:jar:1.11.3:provided`

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:exasol-jdbc:24.1.1` to `24.2.0`
* Updated `com.exasol:spark-connector-common-java:2.0.7` to `2.0.10`
* Updated `org.apache.hadoop:hadoop-aws:3.4.0` to `3.4.1`
* Updated `org.xerial.snappy:snappy-java:1.1.10.5` to `1.1.10.7`
* Updated `software.amazon.awssdk:s3:2.26.25` to `2.29.18`

### Test Dependency Updates

* Updated `com.amazonaws:aws-java-sdk-s3:1.12.765` to `1.12.778`
* Updated `com.exasol:exasol-testcontainers:7.1.0` to `7.1.1`
* Updated `com.exasol:hamcrest-resultset-matcher:1.6.5` to `1.7.0`
* Updated `com.exasol:test-db-builder-java:3.5.4` to `3.6.0`
* Updated `nl.jqno.equalsverifier:equalsverifier:3.16.1` to `3.17.3`
* Updated `org.apache.logging.log4j:log4j-api:2.23.1` to `2.24.1`
* Updated `org.apache.logging.log4j:log4j-core:2.23.1` to `2.24.1`
* Updated `org.hamcrest:hamcrest:2.2` to `3.0`
* Updated `org.junit.jupiter:junit-jupiter-api:5.10.3` to `5.11.3`
* Updated `org.junit.jupiter:junit-jupiter:5.10.3` to `5.11.3`
* Updated `org.mockito:mockito-core:5.12.0` to `5.14.2`
* Updated `org.mockito:mockito-junit-jupiter:5.12.0` to `5.14.2`
* Updated `org.testcontainers:junit-jupiter:1.20.0` to `1.20.4`
* Updated `org.testcontainers:localstack:1.20.0` to `1.20.4`

### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:4.3.3` to `4.4.0`
* Added `com.exasol:quality-summarizer-maven-plugin:0.2.0`
* Updated `io.github.zlika:reproducible-build-maven-plugin:0.16` to `0.17`
* Updated `org.apache.maven.plugins:maven-clean-plugin:2.5` to `3.4.0`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.2.5` to `3.5.1`
* Updated `org.apache.maven.plugins:maven-install-plugin:2.4` to `3.1.3`
* Updated `org.apache.maven.plugins:maven-jar-plugin:3.4.1` to `3.4.2`
* Updated `org.apache.maven.plugins:maven-resources-plugin:2.6` to `3.3.1`
* Updated `org.apache.maven.plugins:maven-shade-plugin:3.5.2` to `3.6.0`
* Updated `org.apache.maven.plugins:maven-site-plugin:3.3` to `3.9.1`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.2.5` to `3.5.1`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.16.2` to `2.17.1`
* Updated `org.itsallcode:openfasttrace-maven-plugin:1.8.0` to `2.3.0`
