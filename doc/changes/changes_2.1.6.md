# Exasol AWS Glue Connector 2.1.6, released 2024-??-??

Code name: Fixed vulnerability CVE-2024-23080 in joda-time:joda-time:jar:2.8.1:provided

## Summary

This release fixes the following vulnerability:

### CVE-2024-23080 (CWE-476) in dependency `joda-time:joda-time:jar:2.8.1:provided`
Joda Time v2.12.5 was discovered to contain a NullPointerException via the component org.joda.time.format.PeriodFormat::wordBased(Locale).
#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2024-23080?component-type=maven&component-name=joda-time%2Fjoda-time&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2024-23080
* https://github.com/advisories/GHSA-gxgx-2mvf-9gh5

## Security

* #98: Fixed vulnerability CVE-2024-23080 in dependency `joda-time:joda-time:jar:2.8.1:provided`

## Dependency Updates

### Compile Dependency Updates

* Updated `software.amazon.awssdk:s3:2.25.29` to `2.25.30`

### Test Dependency Updates

* Updated `com.amazonaws:aws-java-sdk-s3:1.12.699` to `1.12.700`
