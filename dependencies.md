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
| Apache Hadoop Client Aggregator             | [Apache-2.0][3]                               |
| [dnsjava][8]                                | [BSD-3-Clause][9]                             |
| [Apache Commons Configuration][10]          | [Apache-2.0][3]                               |
| [aircompressor][11]                         | [Apache License 2.0][7]                       |
| [Nimbus JOSE+JWT][12]                       | [The Apache Software License, Version 2.0][3] |
| [janino][13]                                | [BSD-3-Clause][14]                            |
| [commons-compiler][15]                      | [BSD-3-Clause][14]                            |
| Apache Hadoop Amazon Web Services support   | [Apache-2.0][3]                               |
| [ParaNamer Core][16]                        | [BSD][17]                                     |
| [Guava: Google Core Libraries for Java][18] | [Apache License, Version 2.0][19]             |
| [jackson-databind][20]                      | [The Apache Software License, Version 2.0][3] |
| [AWS Java SDK :: Services :: Amazon S3][21] | [Apache License, Version 2.0][22]             |
| [Exasol JDBC Driver][23]                    | [EXAClient License][24]                       |
| [Exasol SQL Statement Builder][25]          | [MIT License][26]                             |
| [spark-connector-common-java][27]           | [MIT License][28]                             |
| [error-reporting-java8][29]                 | [MIT License][30]                             |

## Test Dependencies

| Dependency                                      | License                              |
| ----------------------------------------------- | ------------------------------------ |
| [mockito-core][31]                              | [MIT][32]                            |
| [mockito-junit-jupiter][31]                     | [MIT][32]                            |
| [JUnit Jupiter (Aggregator)][33]                | [Eclipse Public License v2.0][34]    |
| [JUnit Jupiter API][33]                         | [Eclipse Public License v2.0][34]    |
| [Hamcrest][35]                                  | [BSD License 3][36]                  |
| [Testcontainers :: JUnit Jupiter Extension][37] | [MIT][38]                            |
| [Testcontainers :: Localstack][37]              | [MIT][38]                            |
| [AWS Java SDK for Amazon S3][21]                | [Apache License, Version 2.0][22]    |
| AWSGlueETL                                      | [Amazon Software License][39]        |
| [snappy][40]                                    | [Apache License 2.0][1]              |
| [Ion Java][41]                                  | [The Apache License, Version 2.0][3] |
| [Test containers for Exasol on Docker][42]      | [MIT License][43]                    |
| [Test Database Builder for Java][44]            | [MIT License][45]                    |
| [Test utilities for `java.util.logging`][46]    | [MIT][32]                            |
| [Matcher for SQL Result Sets][47]               | [MIT License][48]                    |
| [EqualsVerifier \| release normal jar][49]      | [Apache License, Version 2.0][3]     |
| [Apache Log4j API][50]                          | [Apache-2.0][3]                      |
| [Apache Log4j Core][51]                         | [Apache-2.0][3]                      |

## Plugin Dependencies

| Dependency                                              | License                               |
| ------------------------------------------------------- | ------------------------------------- |
| [SonarQube Scanner for Maven][52]                       | [GNU LGPL 3][53]                      |
| [Apache Maven Toolchains Plugin][54]                    | [Apache-2.0][3]                       |
| [Apache Maven Compiler Plugin][55]                      | [Apache-2.0][3]                       |
| [Apache Maven Enforcer Plugin][56]                      | [Apache-2.0][3]                       |
| [Maven Flatten Plugin][57]                              | [Apache Software Licenese][3]         |
| [Apache Maven Shade Plugin][58]                         | [Apache-2.0][3]                       |
| [OpenFastTrace Maven Plugin][59]                        | [GNU General Public License v3.0][60] |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][61] | [ASL2][19]                            |
| [Maven Surefire Plugin][62]                             | [Apache-2.0][3]                       |
| [Versions Maven Plugin][63]                             | [Apache License, Version 2.0][3]      |
| [duplicate-finder-maven-plugin Maven Mojo][64]          | [Apache License 2.0][1]               |
| [Project Keeper Maven plugin][65]                       | [The MIT License][66]                 |
| [Apache Maven Assembly Plugin][67]                      | [Apache-2.0][3]                       |
| [Apache Maven JAR Plugin][68]                           | [Apache-2.0][3]                       |
| [Artifact reference checker and unifier][69]            | [MIT License][70]                     |
| [Maven Failsafe Plugin][71]                             | [Apache-2.0][3]                       |
| [JaCoCo :: Maven Plugin][72]                            | [EPL-2.0][73]                         |
| [error-code-crawler-maven-plugin][74]                   | [MIT License][75]                     |
| [Reproducible Build Maven Plugin][76]                   | [Apache 2.0][19]                      |

[0]: https://spark.apache.org/
[1]: http://www.apache.org/licenses/LICENSE-2.0.html
[2]: http://zookeeper.apache.org/zookeeper
[3]: https://www.apache.org/licenses/LICENSE-2.0.txt
[4]: https://avro.apache.org
[5]: https://commons.apache.org/proper/commons-compress/
[6]: https://github.com/xerial/snappy-java
[7]: https://www.apache.org/licenses/LICENSE-2.0.html
[8]: https://github.com/dnsjava/dnsjava
[9]: https://opensource.org/licenses/BSD-3-Clause
[10]: https://commons.apache.org/proper/commons-configuration/
[11]: https://github.com/airlift/aircompressor
[12]: https://bitbucket.org/connect2id/nimbus-jose-jwt
[13]: http://janino-compiler.github.io/janino/
[14]: https://spdx.org/licenses/BSD-3-Clause.html
[15]: http://janino-compiler.github.io/commons-compiler/
[16]: https://github.com/paul-hammant/paranamer/paranamer
[17]: LICENSE.txt
[18]: https://github.com/google/guava
[19]: http://www.apache.org/licenses/LICENSE-2.0.txt
[20]: https://github.com/FasterXML/jackson
[21]: https://aws.amazon.com/sdkforjava
[22]: https://aws.amazon.com/apache2.0
[23]: http://www.exasol.com/
[24]: https://repo1.maven.org/maven2/com/exasol/exasol-jdbc/24.1.1/exasol-jdbc-24.1.1-license.txt
[25]: https://github.com/exasol/sql-statement-builder/
[26]: https://github.com/exasol/sql-statement-builder/blob/main/LICENSE
[27]: https://github.com/exasol/spark-connector-common-java/
[28]: https://github.com/exasol/spark-connector-common-java/blob/main/LICENSE
[29]: https://github.com/exasol/error-reporting-java/
[30]: https://github.com/exasol/error-reporting-java/blob/main/LICENSE
[31]: https://github.com/mockito/mockito
[32]: https://opensource.org/licenses/MIT
[33]: https://junit.org/junit5/
[34]: https://www.eclipse.org/legal/epl-v20.html
[35]: http://hamcrest.org/JavaHamcrest/
[36]: http://opensource.org/licenses/BSD-3-Clause
[37]: https://java.testcontainers.org
[38]: http://opensource.org/licenses/MIT
[39]: http://aws.amazon.com/asl/
[40]: http://github.com/dain/snappy
[41]: https://github.com/amazon-ion/ion-java/
[42]: https://github.com/exasol/exasol-testcontainers/
[43]: https://github.com/exasol/exasol-testcontainers/blob/main/LICENSE
[44]: https://github.com/exasol/test-db-builder-java/
[45]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[46]: https://github.com/exasol/java-util-logging-testing/
[47]: https://github.com/exasol/hamcrest-resultset-matcher/
[48]: https://github.com/exasol/hamcrest-resultset-matcher/blob/main/LICENSE
[49]: https://www.jqno.nl/equalsverifier
[50]: https://logging.apache.org/log4j/2.x/log4j/log4j-api/
[51]: https://logging.apache.org/log4j/2.x/log4j/log4j-core/
[52]: http://sonarsource.github.io/sonar-scanner-maven/
[53]: http://www.gnu.org/licenses/lgpl.txt
[54]: https://maven.apache.org/plugins/maven-toolchains-plugin/
[55]: https://maven.apache.org/plugins/maven-compiler-plugin/
[56]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[57]: https://www.mojohaus.org/flatten-maven-plugin/
[58]: https://maven.apache.org/plugins/maven-shade-plugin/
[59]: https://github.com/itsallcode/openfasttrace-maven-plugin
[60]: https://www.gnu.org/licenses/gpl-3.0.html
[61]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[62]: https://maven.apache.org/surefire/maven-surefire-plugin/
[63]: https://www.mojohaus.org/versions/versions-maven-plugin/
[64]: https://basepom.github.io/duplicate-finder-maven-plugin
[65]: https://github.com/exasol/project-keeper/
[66]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[67]: https://maven.apache.org/plugins/maven-assembly-plugin/
[68]: https://maven.apache.org/plugins/maven-jar-plugin/
[69]: https://github.com/exasol/artifact-reference-checker-maven-plugin/
[70]: https://github.com/exasol/artifact-reference-checker-maven-plugin/blob/main/LICENSE
[71]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[72]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[73]: https://www.eclipse.org/legal/epl-2.0/
[74]: https://github.com/exasol/error-code-crawler-maven-plugin/
[75]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[76]: http://zlika.github.io/reproducible-build-maven-plugin
