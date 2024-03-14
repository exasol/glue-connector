<!-- @formatter:off -->
# Dependencies

## Compile Dependencies

| Dependency                                  | License                                       |
| ------------------------------------------- | --------------------------------------------- |
| [Spark Project SQL][0]                      | [Apache-2.0][1]                               |
| [Apache ZooKeeper - Server][2]              | [Apache License, Version 2.0][3]              |
| [Apache Avro][4]                            | [Apache-2.0][3]                               |
| [Apache Commons Compress][5]                | [Apache-2.0][3]                               |
| [snappy-java][6]                            | [Apache-2.0][7]                               |
| Apache Hadoop Client Aggregator             | [Apache License, Version 2.0][3]              |
| Apache Hadoop Amazon Web Services support   | [Apache License, Version 2.0][3]              |
| [ParaNamer Core][8]                         | [BSD][9]                                      |
| [Guava: Google Core Libraries for Java][10] | [Apache License, Version 2.0][11]             |
| [jackson-databind][12]                      | [The Apache Software License, Version 2.0][3] |
| [AWS Java SDK :: Services :: Amazon S3][13] | [Apache License, Version 2.0][14]             |
| [Exasol JDBC Driver][15]                    | [EXAClient License][16]                       |
| [Exasol SQL Statement Builder][17]          | [MIT License][18]                             |
| [spark-connector-common-java][19]           | [MIT License][20]                             |
| [error-reporting-java8][21]                 | [MIT License][22]                             |

## Test Dependencies

| Dependency                                      | License                              |
| ----------------------------------------------- | ------------------------------------ |
| [mockito-core][23]                              | [MIT][24]                            |
| [mockito-junit-jupiter][23]                     | [MIT][24]                            |
| [JUnit Jupiter (Aggregator)][25]                | [Eclipse Public License v2.0][26]    |
| [JUnit Jupiter API][25]                         | [Eclipse Public License v2.0][26]    |
| [Hamcrest][27]                                  | [BSD License 3][28]                  |
| [Testcontainers :: JUnit Jupiter Extension][29] | [MIT][30]                            |
| [Testcontainers :: Localstack][29]              | [MIT][30]                            |
| [AWS Java SDK for Amazon S3][13]                | [Apache License, Version 2.0][14]    |
| AWSGlueETL                                      | [Amazon Software License][31]        |
| [Ion Java][32]                                  | [The Apache License, Version 2.0][3] |
| [Test containers for Exasol on Docker][33]      | [MIT License][34]                    |
| [Test Database Builder for Java][35]            | [MIT License][36]                    |
| [Test utilities for `java.util.logging`][37]    | [MIT][24]                            |
| [Matcher for SQL Result Sets][38]               | [MIT License][39]                    |
| [EqualsVerifier \| release normal jar][40]      | [Apache License, Version 2.0][3]     |
| [Apache Log4j API][41]                          | [Apache-2.0][3]                      |
| [Apache Log4j Core][42]                         | [Apache-2.0][3]                      |

## Plugin Dependencies

| Dependency                                              | License                               |
| ------------------------------------------------------- | ------------------------------------- |
| [SonarQube Scanner for Maven][43]                       | [GNU LGPL 3][44]                      |
| [Apache Maven Toolchains Plugin][45]                    | [Apache License, Version 2.0][3]      |
| [Apache Maven Compiler Plugin][46]                      | [Apache-2.0][3]                       |
| [Apache Maven Enforcer Plugin][47]                      | [Apache-2.0][3]                       |
| [Maven Flatten Plugin][48]                              | [Apache Software Licenese][3]         |
| [Apache Maven Shade Plugin][49]                         | [Apache-2.0][3]                       |
| [OpenFastTrace Maven Plugin][50]                        | [GNU General Public License v3.0][51] |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][52] | [ASL2][11]                            |
| [Maven Surefire Plugin][53]                             | [Apache-2.0][3]                       |
| [Versions Maven Plugin][54]                             | [Apache License, Version 2.0][3]      |
| [duplicate-finder-maven-plugin Maven Mojo][55]          | [Apache License 2.0][1]               |
| [Project Keeper Maven plugin][56]                       | [The MIT License][57]                 |
| [Apache Maven Assembly Plugin][58]                      | [Apache-2.0][3]                       |
| [Apache Maven JAR Plugin][59]                           | [Apache License, Version 2.0][3]      |
| [Artifact reference checker and unifier][60]            | [MIT License][61]                     |
| [Maven Failsafe Plugin][62]                             | [Apache-2.0][3]                       |
| [JaCoCo :: Maven Plugin][63]                            | [Eclipse Public License 2.0][64]      |
| [error-code-crawler-maven-plugin][65]                   | [MIT License][66]                     |
| [Reproducible Build Maven Plugin][67]                   | [Apache 2.0][11]                      |

[0]: https://spark.apache.org/
[1]: http://www.apache.org/licenses/LICENSE-2.0.html
[2]: http://zookeeper.apache.org/zookeeper
[3]: https://www.apache.org/licenses/LICENSE-2.0.txt
[4]: https://avro.apache.org
[5]: https://commons.apache.org/proper/commons-compress/
[6]: https://github.com/xerial/snappy-java
[7]: https://www.apache.org/licenses/LICENSE-2.0.html
[8]: https://github.com/paul-hammant/paranamer/paranamer
[9]: LICENSE.txt
[10]: https://github.com/google/guava
[11]: http://www.apache.org/licenses/LICENSE-2.0.txt
[12]: https://github.com/FasterXML/jackson
[13]: https://aws.amazon.com/sdkforjava
[14]: https://aws.amazon.com/apache2.0
[15]: http://www.exasol.com/
[16]: https://repo1.maven.org/maven2/com/exasol/exasol-jdbc/24.0.0/exasol-jdbc-24.0.0-license.txt
[17]: https://github.com/exasol/sql-statement-builder/
[18]: https://github.com/exasol/sql-statement-builder/blob/main/LICENSE
[19]: https://github.com/exasol/spark-connector-common-java/
[20]: https://github.com/exasol/spark-connector-common-java/blob/main/LICENSE
[21]: https://github.com/exasol/error-reporting-java/
[22]: https://github.com/exasol/error-reporting-java/blob/main/LICENSE
[23]: https://github.com/mockito/mockito
[24]: https://opensource.org/licenses/MIT
[25]: https://junit.org/junit5/
[26]: https://www.eclipse.org/legal/epl-v20.html
[27]: http://hamcrest.org/JavaHamcrest/
[28]: http://opensource.org/licenses/BSD-3-Clause
[29]: https://java.testcontainers.org
[30]: http://opensource.org/licenses/MIT
[31]: http://aws.amazon.com/asl/
[32]: https://github.com/amazon-ion/ion-java/
[33]: https://github.com/exasol/exasol-testcontainers/
[34]: https://github.com/exasol/exasol-testcontainers/blob/main/LICENSE
[35]: https://github.com/exasol/test-db-builder-java/
[36]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[37]: https://github.com/exasol/java-util-logging-testing/
[38]: https://github.com/exasol/hamcrest-resultset-matcher/
[39]: https://github.com/exasol/hamcrest-resultset-matcher/blob/main/LICENSE
[40]: https://www.jqno.nl/equalsverifier
[41]: https://logging.apache.org/log4j/2.x/log4j/log4j-api/
[42]: https://logging.apache.org/log4j/2.x/log4j/log4j-core/
[43]: http://sonarsource.github.io/sonar-scanner-maven/
[44]: http://www.gnu.org/licenses/lgpl.txt
[45]: https://maven.apache.org/plugins/maven-toolchains-plugin/
[46]: https://maven.apache.org/plugins/maven-compiler-plugin/
[47]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[48]: https://www.mojohaus.org/flatten-maven-plugin/
[49]: https://maven.apache.org/plugins/maven-shade-plugin/
[50]: https://github.com/itsallcode/openfasttrace-maven-plugin
[51]: https://www.gnu.org/licenses/gpl-3.0.html
[52]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[53]: https://maven.apache.org/surefire/maven-surefire-plugin/
[54]: https://www.mojohaus.org/versions/versions-maven-plugin/
[55]: https://basepom.github.io/duplicate-finder-maven-plugin
[56]: https://github.com/exasol/project-keeper/
[57]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[58]: https://maven.apache.org/plugins/maven-assembly-plugin/
[59]: https://maven.apache.org/plugins/maven-jar-plugin/
[60]: https://github.com/exasol/artifact-reference-checker-maven-plugin/
[61]: https://github.com/exasol/artifact-reference-checker-maven-plugin/blob/main/LICENSE
[62]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[63]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[64]: https://www.eclipse.org/legal/epl-2.0/
[65]: https://github.com/exasol/error-code-crawler-maven-plugin/
[66]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[67]: http://zlika.github.io/reproducible-build-maven-plugin
