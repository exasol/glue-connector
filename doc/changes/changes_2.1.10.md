# Exasol AWS Glue Connector 2.1.10, released 2025-07-01

Code name: Fixed vulnerabilities

## Summary

This release fixes the following vulnerability:

### CVE-2025-48734 (CWE-284) in dependency `commons-beanutils:commons-beanutils:jar:1.9.4:provided`
commons-beanutils - Improper Access Control
#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2025-48734?component-type=maven&component-name=commons-beanutils%2Fcommons-beanutils&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2025-48734
* https://github.com/advisories/GHSA-wxr5-93ph-8wr9
* https://nvd.nist.gov/vuln/detail/CVE-2025-48734

### CVE-2025-47436 (CWE-122) in dependency `org.apache.orc:orc-core:jar:shaded-protobuf:1.9.4:provided`
Heap-based Buffer Overflow vulnerability in Apache ORC.

A vulnerability has been identified in the ORC C++ LZO decompression logic, where specially crafted malformed ORC files can cause the decompressor toÂ allocate a 250-byte buffer but then attempts to copy 295 bytes into it. It causes memory corruption.

This issue affects Apache ORC C++ library: through 1.8.8, from 1.9.0 through 1.9.5, from 2.0.0 through 2.0.4, from 2.1.0 through 2.1.1.

Users are recommended to upgrade to version 1.8.9, 1.9.6, 2.0.5, and 2.1.2, which fix the issue.

#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2025-47436?component-type=maven&component-name=org.apache.orc%2Forc-core&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2025-47436
* https://orc.apache.org/security/CVE-2025-47436/

### CVE-2024-55551 (CWE-94) in dependency `com.exasol:exasol-jdbc:jar:25.2.2:compile`
An issue was discovered in Exasol jdbc driver 24.2.0. Attackers can inject malicious parameters into the JDBC URL, triggering JNDI injection during the process when the JDBC Driver uses this URL to connect to the database. This can further lead to remote code execution vulnerability.
#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2024-55551?component-type=maven&component-name=com.exasol%2Fexasol-jdbc&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2024-55551
* https://gist.github.com/azraelxuemo/9565ec9219e0c3e9afd5474904c39d0f

## Security

* #128: Fixed vulnerability CVE-2025-48734 in dependency `commons-beanutils:commons-beanutils:jar:1.9.4:provided`
* #126: Fixed vulnerability CVE-2025-47436 in dependency `org.apache.orc:orc-core:jar:shaded-protobuf:1.9.4:provided`
* #124: Fixed vulnerability CVE-2024-55551 in dependency `com.exasol:exasol-jdbc:jar:25.2.2:compile`

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

* Updated `com.exasol:artifact-reference-checker-maven-plugin:0.4.2` to `0.4.3`
* Updated `com.exasol:project-keeper-maven-plugin:4.5.0` to `5.2.2`
* Added `io.github.git-commit-id:git-commit-id-maven-plugin:9.0.1`
* Removed `io.github.zlika:reproducible-build-maven-plugin:0.17`
* Added `org.apache.maven.plugins:maven-artifact-plugin:3.6.0`
* Updated `org.apache.maven.plugins:maven-clean-plugin:3.4.0` to `3.4.1`
* Updated `org.apache.maven.plugins:maven-compiler-plugin:3.13.0` to `3.14.0`
* Updated `org.apache.maven.plugins:maven-failsafe-plugin:3.5.2` to `3.5.3`
* Updated `org.apache.maven.plugins:maven-install-plugin:3.1.3` to `3.1.4`
* Updated `org.apache.maven.plugins:maven-surefire-plugin:3.5.2` to `3.5.3`
* Updated `org.codehaus.mojo:flatten-maven-plugin:1.6.0` to `1.7.0`
* Updated `org.jacoco:jacoco-maven-plugin:0.8.12` to `0.8.13`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:5.0.0.4389` to `5.1.0.4751`
