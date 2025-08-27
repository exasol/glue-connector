# Exasol AWS Glue Connector 2.1.12, released 2025-08-27

Code name: Fixes for vulnerability CVE-2025-55163

## Summary

This release fixes the following vulnerability:

### CVE-2025-55163 (CWE-770) in dependency `io.netty:netty-codec-http2:jar:4.2.1.Final:runtime`

Netty is an asynchronous, event-driven network application framework. Prior to versions 4.1.124.Final and 4.2.4.Final, Netty is vulnerable to MadeYouReset DDoS. This is a logical vulnerability in the HTTP/2 protocol, that uses malformed HTTP/2 control frames in order to break the max concurrent streams limit - which results in resource exhaustion and distributed denial of service. This issue has been patched in versions 4.1.124.Final and 4.2.4.Final.

CVE: CVE-2025-55163
CWE: CWE-770

#### References

- https://ossindex.sonatype.org/vulnerability/CVE-2025-55163?component-type=maven&component-name=io.netty%2Fnetty-codec-http2&utm_source=ossindex-client&utm_medium=integration&utm_content=1.8.1
- http://web.nvd.nist.gov/view/vuln/detail?vulnId=CVE-2025-55163
- https://github.com/advisories/GHSA-prj3-ccx8-p6x4

## Security

* #134: Fixed vulnerability CVE-2025-55163 in dependency `io.netty:netty-codec-http2:jar:4.2.1.Final:runtime`

