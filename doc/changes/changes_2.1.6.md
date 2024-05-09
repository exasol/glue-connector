# Exasol AWS Glue Connector 2.1.6, released 2024-??-??

Code name: Fixed vulnerabilities CVE-2023-33201, CVE-2023-33202, CVE-2024-29857, CVE-2024-30171, CVE-2024-34447

## Summary

This release fixes the following 5 vulnerabilities:

### CVE-2023-33201 (CWE-295) in dependency `org.bouncycastle:bcprov-jdk15on:jar:1.70:provided`
Bouncy Castle For Java before 1.74 is affected by an LDAP injection vulnerability. The vulnerability only affects applications that use an LDAP CertStore from Bouncy Castle to validate X.509 certificates. During the certificate validation process, Bouncy Castle inserts the certificate's Subject Name into an LDAP search filter without any escaping, which leads to an LDAP injection vulnerability.
#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2023-33201?component-type=maven&component-name=org.bouncycastle%2Fbcprov-jdk15on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2023-33201
* https://github.com/bcgit/bc-java/wiki/CVE-2023-33201

### CVE-2023-33202 (CWE-400) in dependency `org.bouncycastle:bcprov-jdk15on:jar:1.70:provided`
Bouncy Castle for Java before 1.73 contains a potential Denial of Service (DoS) issue within the Bouncy Castle org.bouncycastle.openssl.PEMParser class. This class parses OpenSSL PEM encoded streams containing X.509 certificates, PKCS8 encoded keys, and PKCS7 objects. Parsing a file that has crafted ASN.1 data through the PEMParser causes an OutOfMemoryError, which can enable a denial of service attack. (For users of the FIPS Java API: BC-FJA 1.0.2.3 and earlier are affected; BC-FJA 1.0.2.4 is fixed.)
#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2023-33202?component-type=maven&component-name=org.bouncycastle%2Fbcprov-jdk15on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2023-33202
* https://github.com/bcgit/bc-java/wiki/CVE-2023-33202

### CVE-2024-29857 (CWE-400) in dependency `org.bouncycastle:bcprov-jdk15on:jar:1.70:provided`
bouncycastle - Denial of Service (DoS)
#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2024-29857?component-type=maven&component-name=org.bouncycastle%2Fbcprov-jdk15on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2024-29857
* https://www.bouncycastle.org/releasenotes.html#:~:text=the%20following%20CVEs%3A-,CVE%2D2024%2D29857,-%2D%20Importing%20an%20EC

### CVE-2024-30171 (CWE-208) in dependency `org.bouncycastle:bcprov-jdk15on:jar:1.70:provided`
bouncycastle - Observable Timing Discrepancy [ aka CVE-2024-20952 ]
#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2024-30171?component-type=maven&component-name=org.bouncycastle%2Fbcprov-jdk15on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2024-30171
* https://github.com/bcgit/bc-java/issues/1528
* https://www.bouncycastle.org/releasenotes.html#:~:text=during%20parameter%20evaluation.-,CVE%2D2024%2D30171,-%2D%20Possible%20timing%20based

### CVE-2024-34447 (CWE-297) in dependency `org.bouncycastle:bcprov-jdk15on:jar:1.70:provided`
bouncycastle - Improper Validation of Certificate with Host Mismatch

The software communicates with a host that provides a certificate, but the software does not properly ensure that the certificate is actually associated with that host.
#### References
* https://ossindex.sonatype.org/vulnerability/CVE-2024-34447?component-type=maven&component-name=org.bouncycastle%2Fbcprov-jdk15on&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
* https://www.bouncycastle.org/releasenotes.html#:~:text=CVE%2D2024%2D301XX%20%2D%20When%20endpoint%20identification%20is%20enabled%20in%20the%20BCJSSE%20and%20an%20SSL%20socket%20is%20not%20created%20with%20an%20explicit%20hostname%20(as%20happens%20with%20HttpsURLConnection)%2C%20hostname%20verification%20could%20be%20performed%20against%20a%20DNS%2Dresolved%20IP%20address.%20This%20has%20been%20fixed.

## Security

* #100: Fixed vulnerability CVE-2023-33201 in dependency `org.bouncycastle:bcprov-jdk15on:jar:1.70:provided`
* #101: Fixed vulnerability CVE-2023-33202 in dependency `org.bouncycastle:bcprov-jdk15on:jar:1.70:provided`
* #102: Fixed vulnerability CVE-2024-29857 in dependency `org.bouncycastle:bcprov-jdk15on:jar:1.70:provided`
* #103: Fixed vulnerability CVE-2024-30171 in dependency `org.bouncycastle:bcprov-jdk15on:jar:1.70:provided`
* #104: Fixed vulnerability CVE-2024-34447 in dependency `org.bouncycastle:bcprov-jdk15on:jar:1.70:provided`

## Dependency Updates

### Compile Dependency Updates

* Updated `com.exasol:spark-connector-common-java:2.0.4` to `2.0.5`
* Updated `software.amazon.awssdk:s3:2.25.29` to `2.25.48`

### Test Dependency Updates

* Updated `com.amazon.ion:ion-java:1.11.4` to `1.11.7`
* Updated `com.amazonaws:aws-java-sdk-s3:1.12.699` to `1.12.718`
* Updated `com.exasol:exasol-testcontainers:7.0.1` to `7.1.0`
* Updated `org.testcontainers:junit-jupiter:1.19.7` to `1.19.8`
* Updated `org.testcontainers:localstack:1.19.7` to `1.19.8`
