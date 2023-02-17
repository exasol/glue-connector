# Exasol AWS Glue Connector 1.0.2, released 2022-10-28

Code name: Dependency updates on top of 1.0.1

## Summary

The following vulnerabilities in dependencies were fixed by updating the dependencies:

* [CVE-2022-42889](https://www.cve.org/CVERecord?id=CVE-2022-42889): Remote Code Execution (RCE) when applied to untrusted input

## Known Remaining Spark Vulnerabilities and Sonatype Warnings

The latest [AWS Glue version 3.0 uses Spark 3.1.1](https://docs.aws.amazon.com/glue/latest/dg/release-notes.html), this means that we cannot update Spark dependency without breaking compatibility. Additionally, this version of Spark depends transitively on the `jackson-databind` versions `>= 2.10.0` and `< 2.11.0`.

This versions of `jackson-databind` includes these vulnerabilities:

* [CVE-2022-42003](https://www.cve.org/CVERecord?id=CVE-2022-42003): Possible resource exhaustion in `jackson-databind` before `2.14.0-rc1`
* [CVE-2022-42004](https://www.cve.org/CVERecord?id=CVE-2022-42004): Possible resource exhaustion in `jackson-databind` before `2.13.4`

However, Exasol `glue-connector` depends on Spark library with scope `provided`, meaning that we are not shipping this dependency with our release artifact, but assume that it is available at the runtime environment.

Users should be aware that these libraries are provided by the AWS Glue service when using the `glue-connector`.

## Bugfixes

* #60: Updated dependencies to fix vulnerabilities

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:exasol-jdbc:7.1.11` to `7.1.17`
* Updated `org.apache.hadoop:hadoop-aws:3.3.2` to `3.3.4`
* Updated `software.amazon.awssdk:s3:2.17.283` to `2.20.6`

### Test Dependency Updates

* Updated `com.amazonaws:AWSGlueETL:3.0.0` to `4.0.0`
* Updated `com.amazonaws:aws-java-sdk-s3:1.12.312` to `1.12.409`
* Updated `com.exasol:exasol-testcontainers:6.2.0` to `6.5.1`
* Updated `com.exasol:java-util-logging-testing:2.0.2` to `2.0.3`
* Updated `com.exasol:test-db-builder-java:3.3.4` to `3.4.2`
* Updated `nl.jqno.equalsverifier:equalsverifier:3.10.1` to `3.13.2`
* Updated `org.junit.jupiter:junit-jupiter-api:5.9.1` to `5.9.2`
* Updated `org.junit.jupiter:junit-jupiter:5.9.1` to `5.9.2`
* Updated `org.mockito:mockito-core:4.8.0` to `5.1.1`
* Updated `org.mockito:mockito-junit-jupiter:4.8.0` to `5.1.1`
* Updated `org.testcontainers:junit-jupiter:1.17.3` to `1.17.6`
* Updated `org.testcontainers:localstack:1.17.3` to `1.17.6`

### Plugin Dependency Updates

* Updated `com.exasol:artifact-reference-checker-maven-plugin:0.4.0` to `0.4.2`
* Updated `com.exasol:error-code-crawler-maven-plugin:1.1.2` to `1.2.2`
* Updated `com.exasol:project-keeper-maven-plugin:2.8.0` to `2.9.3`
* Updated `io.github.zlika:reproducible-build-maven-plugin:0.15` to `0.16`
* Updated `org.apache.maven.plugins:maven-assembly-plugin:3.3.0` to `3.4.2`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.0.0-M5` to `3.0.0-M8`
* Updated `org.apache.maven.plugins:maven-jar-plugin:3.2.2` to `3.3.0`
* Updated `org.apache.maven.plugins:maven-shade-plugin:3.3.0` to `3.4.1`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.0.0-M5` to `3.0.0-M8`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.2.7` to `1.3.0`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.10.0` to `2.14.2`
