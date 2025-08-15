# Exasol AWS Glue Connector 2.1.12, released 2025-??-??

Code name: Fixed vulnerability CVE-2025-55163 in io.netty:netty-codec-http2:jar:4.2.1.Final:runtime

## Summary

This release fixes the following vulnerability:

### CVE-2025-55163 (CWE-770) in dependency `io.netty:netty-codec-http2:jar:4.2.1.Final:runtime`
Netty is an asynchronous, event-driven network application framework. Prior to versions 4.1.124.Final and 4.2.4.Final, Netty is vulnerable to MadeYouReset DDoS. This is a logical vulnerability in the HTTP/2 protocol, that uses malformed HTTP/2 control frames in order to break the max concurrent streams limit - which results in resource exhaustion and distributed denial of service. This issue has been patched in versions 4.1.124.Final and 4.2.4.Final.
#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2025-55163?component-type=maven&component-name=io.netty%2Fnetty-codec-http2&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2025-55163
* https://github.com/advisories/GHSA-prj3-ccx8-p6x4

## Security

* #134: Fixed vulnerability CVE-2025-55163 in dependency `io.netty:netty-codec-http2:jar:4.2.1.Final:runtime`

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:exasol-jdbc:25.2.3` to `25.2.4`
* Updated `com.exasol:spark-connector-common-java:2.0.11` to `2.0.13`
* Updated `org.xerial.snappy:snappy-java:1.1.10.7` to `1.1.10.8`
* Updated `software.amazon.awssdk:s3:2.31.52` to `2.32.23`

### Test Dependency Updates

* Updated `com.amazonaws:aws-java-sdk-s3:1.12.783` to `1.12.788`
* Updated `com.exasol:exasol-testcontainers:7.1.5` to `7.1.7`
* Updated `com.exasol:hamcrest-resultset-matcher:1.7.0` to `1.7.1`
* Updated `com.exasol:test-db-builder-java:3.6.1` to `3.6.3`
* Updated `nl.jqno.equalsverifier:equalsverifier:4.0` to `4.0.7`
* Updated `org.apache.logging.log4j:log4j-api:2.24.3` to `2.25.1`
* Updated `org.apache.logging.log4j:log4j-core:2.24.3` to `2.25.1`
* Updated `org.junit.jupiter:junit-jupiter-api:5.12.2` to `5.13.4`
* Updated `org.junit.jupiter:junit-jupiter:5.12.2` to `5.13.4`
* Updated `org.testcontainers:junit-jupiter:1.21.0` to `1.21.3`
* Updated `org.testcontainers:localstack:1.21.0` to `1.21.3`
