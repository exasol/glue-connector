# Exasol AWS Glue Connector 2.1.13, released 2025-??-??

Code name: Fixed vulnerability CVE-2025-58057 in io.netty:netty-codec-compression:jar:4.2.4.Final:runtime

## Summary

This release fixes the following vulnerability:

### CVE-2025-58057 (CWE-409) in dependency `io.netty:netty-codec-compression:jar:4.2.4.Final:runtime`
netty-codec - Improper Handling of Highly Compressed Data (Data Amplification)
#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2025-58057?component-type=maven&component-name=io.netty%2Fnetty-codec-compression&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2025-58057
* https://github.com/netty/netty/security/advisories/GHSA-3p8m-j85q-pgmj

## Security

* #137: Fixed vulnerability CVE-2025-58057 in dependency `io.netty:netty-codec-compression:jar:4.2.4.Final:runtime`

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:exasol-jdbc:25.2.3` to `25.2.5`
* Updated `com.exasol:spark-connector-common-java:2.0.11` to `2.0.14`
* Updated `org.apache.hadoop:hadoop-aws:3.4.1` to `3.4.2`
* Updated `org.xerial.snappy:snappy-java:1.1.10.7` to `1.1.10.8`
* Updated `software.amazon.awssdk:s3:2.31.52` to `2.33.3`

### Test Dependency Updates

* Updated `com.amazonaws:aws-java-sdk-s3:1.12.783` to `1.12.790`
* Updated `com.exasol:exasol-testcontainers:7.1.5` to `7.1.7`
* Updated `com.exasol:hamcrest-resultset-matcher:1.7.0` to `1.7.2`
* Updated `com.exasol:test-db-builder-java:3.6.1` to `3.6.3`
* Updated `nl.jqno.equalsverifier:equalsverifier:4.0` to `4.1`
* Updated `org.apache.logging.log4j:log4j-api:2.24.3` to `2.25.1`
* Updated `org.apache.logging.log4j:log4j-core:2.24.3` to `2.25.1`
* Updated `org.junit.jupiter:junit-jupiter-api:5.12.2` to `5.13.4`
* Updated `org.junit.jupiter:junit-jupiter:5.12.2` to `5.13.4`
* Updated `org.mockito:mockito-core:5.18.0` to `5.19.0`
* Updated `org.mockito:mockito-junit-jupiter:5.18.0` to `5.19.0`
* Updated `org.testcontainers:junit-jupiter:1.21.0` to `1.21.3`
* Updated `org.testcontainers:localstack:1.21.0` to `1.21.3`
