# Exasol AWS Glue Connector 2.1.5, released 2024-04-10

Code name: Fixed vulnerabilities CVE-2024-29025, CVE-2024-29133 & CVE-2024-29133

## Summary

This release fixes vulnerabilities CVE-2024-29025, CVE-2024-29133 & CVE-2024-29133 in dependencies.

**Excluded vulnerabilities:** This release uses transitive dependency `org.bouncycastle:bcprov-jdk15on:jar:1.70:provided` via `org.apache.hadoop:hadoop-client` which contains the following vulnerabilities:
* CVE-2023-33201 
* CVE-2023-33202
* CVE-2024-29857
* CVE-2024-30171
* CVE-2024-30172

The dependency is a "provided" library that is not included in the built JAR and must be fixed in the runtime environment

## Security

* #96: Fixed CVE-2024-29025 in `io.netty:netty-codec-http:jar:4.1.107.Final:runtime`
* #95: Excluded CVE-2024-29133 in `org.apache.commons:commons-configuration2:jar:2.8.0:provided`
* #94: Excluded CVE-2024-29133 in `org.apache.commons:commons-configuration2:jar:2.8.0:provided`

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:exasol-jdbc:7.1.20` to `24.1.0`
* Updated `com.exasol:spark-connector-common-java:2.0.3` to `2.0.4`
* Updated `org.apache.hadoop:hadoop-aws:3.3.6` to `3.4.0`
* Updated `software.amazon.awssdk:s3:2.25.9` to `2.25.29`

### Test Dependency Updates

* Updated `com.amazonaws:aws-java-sdk-s3:1.12.679` to `1.12.699`
* Updated `nl.jqno.equalsverifier:equalsverifier:3.15.8` to `3.16.1`

### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:2.0.1` to `2.0.2`
* Updated `com.exasol:project-keeper-maven-plugin:4.2.0` to `4.3.0`
* Updated `org.apache.maven.plugins:maven-assembly-plugin:3.6.0` to `3.7.1`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.12.1` to `3.13.0`
* Updated `org.apache.maven.plugins:maven-shade-plugin:3.5.1` to `3.5.2`
* Updated `org.itsallcode:openfasttrace-maven-plugin:1.6.2` to `1.8.0`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.11` to `0.8.12`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:3.10.0.2594` to `3.11.0.3922`
