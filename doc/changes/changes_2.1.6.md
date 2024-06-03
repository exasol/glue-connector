# Exasol AWS Glue Connector 2.1.6, released 2024-06-03

Code name: CVEs fixes

## Summary
Fixed CVE-2024-36114 in io.airlift:aircompressor (dependency of spark-sql).
Fixed CVE-2023-33546 in org.codehaus.janino:janino:jar:3.1.9 (previously excluded).
Fixed CVE-2023-52428 in com.nimbusds:nimbus-jose-jwt:jar:9.8.1 (previously excluded).
Bunch of CVEs in org.bouncycastle:bcprov-jdk15on were excluded.

## Features

* #106: CVE-2024-36114: io.airlift:aircompressor:jar:0.25:provided
* #100: CVE-2023-33201: org.bouncycastle:bcprov-jdk15on:jar:1.70:provided
* #101: CVE-2023-33202: org.bouncycastle:bcprov-jdk15on:jar:1.70:provided
* #102: CVE-2024-29857: org.bouncycastle:bcprov-jdk15on:jar:1.70:provided
* #103: CVE-2024-30171: org.bouncycastle:bcprov-jdk15on:jar:1.70:provided
* #104: CVE-2024-34447: org.bouncycastle:bcprov-jdk15on:jar:1.70:provided

## Dependency Updates

### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:2.0.2` to `2.0.3`
* Updated `com.exasol:project-keeper-maven-plugin:4.3.0` to `4.3.2`
* Updated `org.apache.maven.plugins:maven-enforcer-plugin:3.4.1` to `3.5.0`
* Updated `org.apache.maven.plugins:maven-jar-plugin:3.3.0` to `3.4.1`
* Updated `org.apache.maven.plugins:maven-toolchains-plugin:3.1.0` to `3.2.0`
* Updated `org.sonarsource.scanner.maven:sonar-maven-plugin:3.11.0.3922` to `4.0.0.4121`
