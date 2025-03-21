# Exasol AWS Glue Connector 2.1.10, released 2025-??-??

Code name: Fixed vulnerability CVE-2024-55551 in com.exasol:exasol-jdbc:jar:25.2.2:compile

## Summary

This release fixes the following vulnerability:

### CVE-2024-55551 (CWE-94) in dependency `com.exasol:exasol-jdbc:jar:25.2.2:compile`
An issue was discovered in Exasol jdbc driver 24.2.0. Attackers can inject malicious parameters into the JDBC URL, triggering JNDI injection during the process when the JDBC Driver uses this URL to connect to the database. This can further lead to remote code execution vulnerability.
#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2024-55551?component-type=maven&component-name=com.exasol%2Fexasol-jdbc&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2024-55551
* https://gist.github.com/azraelxuemo/9565ec9219e0c3e9afd5474904c39d0f

## Security

* #124: Fixed vulnerability CVE-2024-55551 in dependency `com.exasol:exasol-jdbc:jar:25.2.2:compile`

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:spark-connector-common-java:2.0.10` to `2.0.11`
* Updated `com.thoughtworks.paranamer:paranamer:2.8` to `2.8.3`
* Updated `software.amazon.awssdk:s3:2.29.18` to `2.31.5`

### Test Dependency Updates

* Updated `com.amazon.ion:ion-java:1.11.9` to `1.11.10`
* Updated `com.amazonaws:aws-java-sdk-s3:1.12.778` to `1.12.782`
* Updated `com.exasol:exasol-testcontainers:7.1.3` to `7.1.4`
* Updated `nl.jqno.equalsverifier:equalsverifier:3.17.3` to `3.19.2`
* Updated `org.apache.logging.log4j:log4j-api:2.24.1` to `2.24.3`
* Updated `org.apache.logging.log4j:log4j-core:2.24.1` to `2.24.3`
* Updated `org.junit.jupiter:junit-jupiter-api:5.11.3` to `5.12.1`
* Updated `org.junit.jupiter:junit-jupiter:5.11.3` to `5.12.1`
* Updated `org.mockito:mockito-core:5.14.2` to `5.16.1`
* Updated `org.mockito:mockito-junit-jupiter:5.14.2` to `5.16.1`
* Updated `org.slf4j:slf4j-jdk14:1.7.36` to `2.0.17`
* Updated `org.testcontainers:junit-jupiter:1.20.4` to `1.20.6`
* Updated `org.testcontainers:localstack:1.20.4` to `1.20.6`

### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:4.5.0` to `5.0.0`
