# Exasol AWS Glue Connector 2.1.8, released 2024-??-??

Code name: Fixed vulnerability CVE-2024-51504 in org.apache.zookeeper:zookeeper:jar:3.9.2:provided

## Summary

This release fixes the following vulnerability:

### CVE-2024-51504 (CWE-290) in dependency `org.apache.zookeeper:zookeeper:jar:3.9.2:provided`
When using IPAuthenticationProvider in ZooKeeper Admin Server there is a possibility of Authentication Bypass by Spoofing -- this only impacts IP based authentication implemented in ZooKeeper Admin Server. Default configuration of client's IP address detection inÂ IPAuthenticationProvider, which uses HTTP request headers, is weakÂ and allows an attacker to bypass authentication via spoofing client's IP address in request headers. Default configuration honors X-Forwarded-For HTTP header to read client's IP address. X-Forwarded-For request header is mainly used by proxy servers to identify the client and can be easily spoofed by an attacker pretending that the request comes from a different IP address. Admin Server commands, such as snapshot and restore arbitrarily can be executed on successful exploitation which could potentially lead to information leakage or service availability issues. Users are recommended to upgrade to version 3.9.3, which fixes this issue.
#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2024-51504?component-type=maven&component-name=org.apache.zookeeper%2Fzookeeper&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2024-51504
* https://lists.apache.org/thread/b3qrmpkto5r6989qr61fw9y2x646kqlh

## Security

* #116: Fixed vulnerability CVE-2024-51504 in dependency `org.apache.zookeeper:zookeeper:jar:3.9.2:provided`

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:exasol-jdbc:24.1.1` to `24.1.2`
* Updated `com.exasol:spark-connector-common-java:2.0.7` to `2.0.9`
* Updated `org.apache.hadoop:hadoop-aws:3.4.0` to `3.4.1`
* Updated `org.xerial.snappy:snappy-java:1.1.10.5` to `1.1.10.7`
* Updated `software.amazon.awssdk:s3:2.26.25` to `2.29.9`

### Test Dependency Updates

* Updated `com.amazonaws:aws-java-sdk-s3:1.12.765` to `1.12.777`
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
* Updated `org.testcontainers:junit-jupiter:1.20.0` to `1.20.3`
* Updated `org.testcontainers:localstack:1.20.0` to `1.20.3`

### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:4.3.3` to `4.4.0`
