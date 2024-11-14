# Exasol AWS Glue Connector 2.1.8, released 2024-??-??

Code name: Fixed vulnerability CVE-2024-47535 in io.netty:netty-common:jar:4.1.105.Final:compile

## Summary

This release fixes the following vulnerability:

### CVE-2024-47535 (CWE-400) in dependency `io.netty:netty-common:jar:4.1.105.Final:compile`
Netty is an asynchronous event-driven network application framework for rapid development of maintainable high performance protocol servers & clients. An unsafe reading of environment file could potentially cause a denial of service in Netty. When loaded on an Windows application, Netty attempts to load a file that does not exist. If an attacker creates such a large file, the Netty application crashes. This vulnerability is fixed in 4.1.115.
#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2024-47535?component-type=maven&component-name=io.netty%2Fnetty-common&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2024-47535
* https://github.com/advisories/GHSA-xq3w-v528-46rv

## Security

* #118: Fixed vulnerability CVE-2024-47535 in dependency `io.netty:netty-common:jar:4.1.105.Final:compile`

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:exasol-jdbc:24.1.1` to `24.2.0`
* Updated `com.exasol:spark-connector-common-java:2.0.7` to `2.0.9`
* Updated `org.apache.hadoop:hadoop-aws:3.4.0` to `3.4.1`
* Updated `org.xerial.snappy:snappy-java:1.1.10.5` to `1.1.10.7`
* Updated `software.amazon.awssdk:s3:2.26.25` to `2.29.12`

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
* Updated `org.testcontainers:junit-jupiter:1.20.0` to `1.20.3`
* Updated `org.testcontainers:localstack:1.20.0` to `1.20.3`

### Plugin Dependency Updates

* Updated `com.exasol:project-keeper-maven-plugin:4.3.3` to `4.4.0`
