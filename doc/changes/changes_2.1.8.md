# Exasol AWS Glue Connector 2.1.8, released 2024-??-??

Code name: Fixed vulnerabilities CVE-2024-47561, CVE-2024-47554

## Summary

This release fixes the following 2 vulnerabilities:

### CVE-2024-47561 (CWE-502) in dependency `org.apache.avro:avro:jar:1.11.3:provided`
Schema parsing in the Java SDK of Apache Avro 1.11.3 and previous versions allows bad actors to execute arbitrary code.
Users are recommended to upgrade to version 1.11.4Â  or 1.12.0, which fix this issue.
#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2024-47561?component-type=maven&component-name=org.apache.avro%2Favro&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2024-47561
* https://lists.apache.org/thread/c2v7mhqnmq0jmbwxqq3r5jbj1xg43h5x

### CVE-2024-47554 (CWE-400) in dependency `commons-io:commons-io:jar:2.11.0:provided`
Uncontrolled Resource Consumption vulnerability in Apache Commons IO.

The org.apache.commons.io.input.XmlStreamReader class may excessively consume CPU resources when processing maliciously crafted input.

This issue affects Apache Commons IO: from 2.0 before 2.14.0.

Users are recommended to upgrade to version 2.14.0 or later, which fixes the issue.
#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2024-47554?component-type=maven&component-name=commons-io%2Fcommons-io&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2024-47554
* https://lists.apache.org/thread/6ozr91rr9cj5lm0zyhv30bsp317hk5z1

## Security

* #113: Fixed vulnerability CVE-2024-47561 in dependency `org.apache.avro:avro:jar:1.11.3:provided`
* #114: Fixed vulnerability CVE-2024-47554 in dependency `commons-io:commons-io:jar:2.11.0:provided`

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:exasol-jdbc:24.1.1` to `24.1.2`
* Updated `com.exasol:spark-connector-common-java:2.0.7` to `2.0.8`
* Updated `org.xerial.snappy:snappy-java:1.1.10.5` to `1.1.10.7`
* Updated `software.amazon.awssdk:s3:2.26.25` to `2.28.16`

### Test Dependency Updates

* Updated `com.amazonaws:aws-java-sdk-s3:1.12.765` to `1.12.773`
* Updated `com.exasol:exasol-testcontainers:7.1.0` to `7.1.1`
* Updated `com.exasol:hamcrest-resultset-matcher:1.6.5` to `1.7.0`
* Updated `com.exasol:test-db-builder-java:3.5.4` to `3.6.0`
* Updated `nl.jqno.equalsverifier:equalsverifier:3.16.1` to `3.17.1`
* Updated `org.apache.logging.log4j:log4j-api:2.23.1` to `2.24.1`
* Updated `org.apache.logging.log4j:log4j-core:2.23.1` to `2.24.1`
* Updated `org.hamcrest:hamcrest:2.2` to `3.0`
* Updated `org.junit.jupiter:junit-jupiter-api:5.10.3` to `5.11.2`
* Updated `org.junit.jupiter:junit-jupiter:5.10.3` to `5.11.2`
* Updated `org.mockito:mockito-core:5.12.0` to `5.14.1`
* Updated `org.mockito:mockito-junit-jupiter:5.12.0` to `5.14.1`
* Updated `org.testcontainers:junit-jupiter:1.20.0` to `1.20.2`
* Updated `org.testcontainers:localstack:1.20.0` to `1.20.2`
