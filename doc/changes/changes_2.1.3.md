# Exasol AWS Glue Connector 2.1.3, released 2024-01-19

Code name: Fix CVE-2024-21634 in `ion-java`

## Summary

This release fixes vulnerability CVE-2024-21634 in transitive test dependencies `com.amazon.ion:ion-java` and `software.amazon.ion:ion-java`.

## Security

* #88: Fixed CVE-2024-21634 in `ion-java`

## Dependency Updates

### Compile Dependency Updates

* Updated `software.amazon.awssdk:s3:2.22.0` to `2.23.6`

### Test Dependency Updates

* Added `com.amazon.ion:ion-java:1.11.1`
* Updated `com.amazonaws:aws-java-sdk-s3:1.12.620` to `1.12.640`
* Updated `com.exasol:hamcrest-resultset-matcher:1.6.3` to `1.6.4`
* Updated `nl.jqno.equalsverifier:equalsverifier:3.15.4` to `3.15.6`
* Updated `org.apache.logging.log4j:log4j-api:2.22.0` to `2.22.1`
* Updated `org.apache.logging.log4j:log4j-core:2.22.0` to `2.22.1`
* Updated `org.mockito:mockito-core:5.8.0` to `5.9.0`
* Updated `org.mockito:mockito-junit-jupiter:5.8.0` to `5.9.0`
* Removed `org.slf4j:slf4j-jdk14:2.0.9`

### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:2.9.17` to `3.0.0`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.2.2` to `3.2.3`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.2.2` to `3.2.3`
* Added `org.apache.maven.plugins:maven-toolchains-plugin:3.1.0`
