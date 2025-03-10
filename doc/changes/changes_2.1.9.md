# Exasol AWS Glue Connector 2.1.9, released 2025-03-10

Code name: Fixed CVE-2025-24970 and CVE-2025-25193

## Summary

This update fixes CVE-2025-24970 and CVE-2025-25193 in transitive `netty` dependency.

It also sets up the `SECURITY.md` file for the repository.

## Security

* #120: CVE-2025-24970
* #121: CVE-2025-25193

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:exasol-jdbc:24.2.0` to `25.2.2`

### Test Dependency Updates

* Updated `com.exasol:exasol-testcontainers:7.1.1` to `7.1.3`
* Added `org.slf4j:slf4j-jdk14:1.7.36`

### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:4.4.0` to `4.5.0`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.5.1` to `3.5.2`
* Updated `org.apache.maven.plugins:maven-site-plugin:3.9.1` to `3.21.0`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.5.1` to `3.5.2`
* Updated `org.codehaus.mojo:versions-maven-plugin:2.17.1` to `2.18.0`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:4.0.0.4121` to `5.0.0.4389`
