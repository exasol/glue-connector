# Exasol AWS Glue Connector 2.1.10, released 2025-??-??

Code name: Fixed vulnerability CVE-2025-48734 in commons-beanutils:commons-beanutils:jar:1.9.4:provided

## Summary

This release fixes the following vulnerability:

### CVE-2025-48734 (CWE-284) in dependency `commons-beanutils:commons-beanutils:jar:1.9.4:provided`
commons-beanutils - Improper Access Control
#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2025-48734?component-type=maven&component-name=commons-beanutils%2Fcommons-beanutils&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2025-48734
* https://github.com/advisories/GHSA-wxr5-93ph-8wr9
* https://nvd.nist.gov/vuln/detail/CVE-2025-48734

## Security

* #128: Fixed vulnerability CVE-2025-48734 in dependency `commons-beanutils:commons-beanutils:jar:1.9.4:provided`

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:exasol-jdbc:25.2.2` to `25.2.3`
* Updated `com.exasol:spark-connector-common-java:2.0.10` to `2.0.11`
* Updated `com.thoughtworks.paranamer:paranamer:2.8` to `2.8.3`
* Updated `software.amazon.awssdk:s3:2.29.18` to `2.31.52`

### Test Dependency Updates

* Updated `com.amazon.ion:ion-java:1.11.9` to `1.11.10`
* Updated `com.amazonaws:aws-java-sdk-s3:1.12.778` to `1.12.783`
* Updated `com.exasol:exasol-testcontainers:7.1.3` to `7.1.5`
* Updated `com.exasol:test-db-builder-java:3.6.0` to `3.6.1`
* Updated `nl.jqno.equalsverifier:equalsverifier:3.17.3` to `4.0`
* Updated `org.apache.logging.log4j:log4j-api:2.24.1` to `2.24.3`
* Updated `org.apache.logging.log4j:log4j-core:2.24.1` to `2.24.3`
* Updated `org.junit.jupiter:junit-jupiter-api:5.11.3` to `5.12.2`
* Updated `org.junit.jupiter:junit-jupiter:5.11.3` to `5.12.2`
* Updated `org.mockito:mockito-core:5.14.2` to `5.18.0`
* Updated `org.mockito:mockito-junit-jupiter:5.14.2` to `5.18.0`
* Updated `org.slf4j:slf4j-jdk14:1.7.36` to `2.0.17`
* Updated `org.testcontainers:junit-jupiter:1.20.4` to `1.21.0`
* Updated `org.testcontainers:localstack:1.20.4` to `1.21.0`

### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:4.5.0` to `5.1.0`
