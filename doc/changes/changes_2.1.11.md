# Exasol AWS Glue Connector 2.1.11, released 2025-08-12

Code name: Fixes for vulnerabilities CVE-2025-48924 and CVE-2025-53864

## Summary

This release fixes the following vulnerabilities:

### CVE-2025-53864 (CWE-121) in dependency `com.google.code.gson:gson:jar:2.9.0:provided`

github.com/sigstore/sigstore-java (gson) - Stack-based Buffer Overflow [CVE-2025-53864]

A stack-based buffer overflow condition is a condition where the buffer being overwritten is allocated on the stack (i.e., is a local variable or, rarely, a parameter to a function).

CVE: CVE-2025-53864
CWE: CWE-121

#### References

- https://ossindex.sonatype.org/vulnerability/CVE-2025-53864?component-type=maven&component-name=com.google.code.gson%2Fgson&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
- https://issues.oss-fuzz.com/issues/384541935

### CVE-2025-48924 (CWE-674) in dependency `org.apache.commons:commons-lang3:jar:3.16.0:provided`

Uncontrolled Recursion vulnerability in Apache Commons Lang.

This issue affects Apache Commons Lang: Starting with commons-lang:commons-lang 2.0 to 2.6, and, from org.apache.commons:commons-lang3 3.0 before 3.18.0.

The methods ClassUtils.getClass(...) can throw StackOverflowError on very long inputs. Because an Error is usually not handled by applications and libraries, a 
StackOverflowError could cause an application to stop.

Users are recommended to upgrade to version 3.18.0, which fixes the issue.

CVE: CVE-2025-48924
CWE: CWE-674

#### References

- https://ossindex.sonatype.org/vulnerability/CVE-2025-48924?component-type=maven&component-name=org.apache.commons%2Fcommons-lang3&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
- http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2025-48924
- https://github.com/advisories/GHSA-j288-q9x7-2f5v

## Security

* #132: Fixed vulnerability CVE-2025-53864 in dependency `com.google.code.gson:gson:jar:2.9.0:provided`
* #131: Fixed vulnerability CVE-2025-48924 in dependency `org.apache.commons:commons-lang3:jar:3.16.0:provided`

## Dependency Updates

### Plugin Dependency Updates

* Updated `com.exasol:error-code-crawler-maven-plugin:2.0.3` to `2.0.4`
* Updated `com.exasol:project-keeper-maven-plugin:5.2.2` to `5.2.3`
