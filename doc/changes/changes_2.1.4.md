# Exasol AWS Glue Connector 2.1.4, released 2024-03-14

Code name: Fixed vulnerabilities CVE-2024-25710 and CVE-2024-26308 in test dependencies

## Summary

This is a security release in which we updated test dependency `com.exasol:exasol-test-setup-abstraction-java` to fix vulnerabilities CVE-2024-25710 and CVE-2024-26308 in its transitive dependencies.

Vulnerability CVE-2023-52428 reported for transitive dependency `com.nimbusds:nimbus-jose-jwt:jar:9.8.1` via `org.apache.hadoop:hadoop-client:jar:3.3.6` has been excluded in file `pom.xml` as `nimbus-jose-jwt` is not included in the released artifact, but 'provided' by the runtime Spark cluster.

## Security

* #90: Ignored vulnerability CVE-2023-52428
* #91: Fixed vulnerability CVE-2024-25710 in test dependencies
* #92: Fixed vulnerability CVE-2024-26308 in test dependencies

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:exasol-jdbc:7.1.20` to `24.0.0`
* Updated `software.amazon.awssdk:s3:2.23.6` to `2.25.9`

### Test Dependency Updates

* Updated `com.amazon.ion:ion-java:1.11.1` to `1.11.4`
* Updated `com.amazonaws:aws-java-sdk-s3:1.12.640` to `1.12.679`
* Updated `com.exasol:exasol-testcontainers:7.0.0` to `7.0.1`
* Updated `com.exasol:hamcrest-resultset-matcher:1.6.4` to `1.6.5`
* Updated `com.exasol:test-db-builder-java:3.5.3` to `3.5.4`
* Updated `nl.jqno.equalsverifier:equalsverifier:3.15.6` to `3.15.8`
* Updated `org.apache.logging.log4j:log4j-api:2.22.1` to `2.23.1`
* Updated `org.apache.logging.log4j:log4j-core:2.22.1` to `2.23.1`
* Updated `org.junit.jupiter:junit-jupiter-api:5.10.1` to `5.10.2`
* Updated `org.junit.jupiter:junit-jupiter:5.10.1` to `5.10.2`
* Updated `org.mockito:mockito-core:5.9.0` to `5.11.0`
* Updated `org.mockito:mockito-junit-jupiter:5.9.0` to `5.11.0`
* Updated `org.testcontainers:junit-jupiter:1.19.3` to `1.19.7`
* Updated `org.testcontainers:localstack:1.19.3` to `1.19.7`

### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:1.3.1` to `2.0.1`
* Updated `com.exasol:project-keeper-maven-plugin:3.0.0` to `4.2.0`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.11.0` to `3.12.1`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.2.3` to `3.2.5`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.2.3` to `3.2.5`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.5.0` to `1.6.0`
