# Exasol AWS Glue Connector 1.0.2, released 2022-10-26

Code name: Dependency updates on top of 1.0.1

## Summary

The following vulnerabilities in dependencies were fixed by updating the dependencies:

* CVE-2022-42003: Uncontrolled Resource Consumption in `jackson-databind`

## Known Remaining Spark 3.3.4 Vulnerabilities and Sonatype Warnings

The following vulnerabilities are known in Spark 3.3.4, but no update is available at the time of this `spark-connector` update, so instead we evaluate the risks here.

* The command line tool `hdfs ec` has the known vulnerability sonatype-2022-5732, but the connector is not affected, since it does not use this tool. For more details see [HDFS-16766 on the Haddop issue tracker](https://issues.apache.org/jira/browse/HDFS-16766).
* Related to the vulnerability above the code creates `DocumentBuilderFactory` instances in various other locations, but the collection ticket [HADOOP-18469](https://issues.apache.org/jira/browse/HADOOP-18469) states that no additional issues are known as a result yet.

## Bugfixes

* #60: Updated dependencies to fix vulnerabilities

## Dependency Updates

### Compile Dependency Updates

* Added `org.apache.commons:commons-text:1.10.0`

### Test Dependency Updates

* Updated `org.testcontainers:junit-jupiter:1.17.3` to `1.17.5`
