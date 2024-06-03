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
| [Apache Commons Configuration][8]           | [Apache-2.0][3]                               |
| [aircompressor][9]                          | [Apache License 2.0][7]                       |
| [janino][10]                                | [BSD-3-Clause][11]                            |
| [commons-compiler][12]                      | [BSD-3-Clause][11]                            |
| Apache Hadoop Amazon Web Services support   | [Apache-2.0][3]                               |
| [ParaNamer Core][13]                        | [BSD][14]                                     |
| [Guava: Google Core Libraries for Java][15] | [Apache License, Version 2.0][16]             |
| [jackson-databind][17]                      | [The Apache Software License, Version 2.0][3] |
| [AWS Java SDK :: Services :: Amazon S3][18] | [Apache License, Version 2.0][19]             |
| [Exasol JDBC Driver][20]                    | [EXAClient License][21]                       |
| [Exasol SQL Statement Builder][22]          | [MIT License][23]                             |
| [spark-connector-common-java][24]           | [MIT License][25]                             |
| [error-reporting-java8][26]                 | [MIT License][27]                             |

## Test Dependencies

| Dependency                                      | License                              |
| ----------------------------------------------- | ------------------------------------ |
| [mockito-core][28]                              | [MIT][29]                            |
| [mockito-junit-jupiter][28]                     | [MIT][29]                            |
| [JUnit Jupiter (Aggregator)][30]                | [Eclipse Public License v2.0][31]    |
| [JUnit Jupiter API][30]                         | [Eclipse Public License v2.0][31]    |
| [Hamcrest][32]                                  | [BSD License 3][33]                  |
| [Testcontainers :: JUnit Jupiter Extension][34] | [MIT][35]                            |
| [Testcontainers :: Localstack][34]              | [MIT][35]                            |
| [AWS Java SDK for Amazon S3][18]                | [Apache License, Version 2.0][19]    |
| AWSGlueETL                                      | [Amazon Software License][36]        |
| [Ion Java][37]                                  | [The Apache License, Version 2.0][3] |
| [Test containers for Exasol on Docker][38]      | [MIT License][39]                    |
| [Test Database Builder for Java][40]            | [MIT License][41]                    |
| [Test utilities for `java.util.logging`][42]    | [MIT][29]                            |
| [Matcher for SQL Result Sets][43]               | [MIT License][44]                    |
| [EqualsVerifier \| release normal jar][45]      | [Apache License, Version 2.0][3]     |
| [Apache Log4j API][46]                          | [Apache-2.0][3]                      |
| [Apache Log4j Core][47]                         | [Apache-2.0][3]                      |

## Plugin Dependencies

| Dependency                                              | License                               |
| ------------------------------------------------------- | ------------------------------------- |
| [SonarQube Scanner for Maven][48]                       | [GNU LGPL 3][49]                      |
| [Apache Maven Toolchains Plugin][50]                    | [Apache-2.0][3]                       |
| [Apache Maven Compiler Plugin][51]                      | [Apache-2.0][3]                       |
| [Apache Maven Enforcer Plugin][52]                      | [Apache-2.0][3]                       |
| [Maven Flatten Plugin][53]                              | [Apache Software Licenese][3]         |
| [Apache Maven Shade Plugin][54]                         | [Apache-2.0][3]                       |
| [OpenFastTrace Maven Plugin][55]                        | [GNU General Public License v3.0][56] |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][57] | [ASL2][16]                            |
| [Maven Surefire Plugin][58]                             | [Apache-2.0][3]                       |
| [Versions Maven Plugin][59]                             | [Apache License, Version 2.0][3]      |
| [duplicate-finder-maven-plugin Maven Mojo][60]          | [Apache License 2.0][1]               |
| [Project Keeper Maven plugin][61]                       | [The MIT License][62]                 |
| [Apache Maven Assembly Plugin][63]                      | [Apache-2.0][3]                       |
| [Apache Maven JAR Plugin][64]                           | [Apache-2.0][3]                       |
| [Artifact reference checker and unifier][65]            | [MIT License][66]                     |
| [Maven Failsafe Plugin][67]                             | [Apache-2.0][3]                       |
| [JaCoCo :: Maven Plugin][68]                            | [EPL-2.0][69]                         |
| [error-code-crawler-maven-plugin][70]                   | [MIT License][71]                     |
| [Reproducible Build Maven Plugin][72]                   | [Apache 2.0][16]                      |

[0]: https://spark.apache.org/
[1]: http://www.apache.org/licenses/LICENSE-2.0.html
[2]: http://zookeeper.apache.org/zookeeper
[3]: https://www.apache.org/licenses/LICENSE-2.0.txt
[4]: https://avro.apache.org
[5]: https://commons.apache.org/proper/commons-compress/
[6]: https://github.com/xerial/snappy-java
[7]: https://www.apache.org/licenses/LICENSE-2.0.html
[8]: https://commons.apache.org/proper/commons-configuration/
[9]: https://github.com/airlift/aircompressor
[10]: http://janino-compiler.github.io/janino/
[11]: https://spdx.org/licenses/BSD-3-Clause.html
[12]: http://janino-compiler.github.io/commons-compiler/
[13]: https://github.com/paul-hammant/paranamer/paranamer
[14]: LICENSE.txt
[15]: https://github.com/google/guava
[16]: http://www.apache.org/licenses/LICENSE-2.0.txt
[17]: https://github.com/FasterXML/jackson
[18]: https://aws.amazon.com/sdkforjava
[19]: https://aws.amazon.com/apache2.0
[20]: http://www.exasol.com/
[21]: https://repo1.maven.org/maven2/com/exasol/exasol-jdbc/24.1.0/exasol-jdbc-24.1.0-license.txt
[22]: https://github.com/exasol/sql-statement-builder/
[23]: https://github.com/exasol/sql-statement-builder/blob/main/LICENSE
[24]: https://github.com/exasol/spark-connector-common-java/
[25]: https://github.com/exasol/spark-connector-common-java/blob/main/LICENSE
[26]: https://github.com/exasol/error-reporting-java/
[27]: https://github.com/exasol/error-reporting-java/blob/main/LICENSE
[28]: https://github.com/mockito/mockito
[29]: https://opensource.org/licenses/MIT
[30]: https://junit.org/junit5/
[31]: https://www.eclipse.org/legal/epl-v20.html
[32]: http://hamcrest.org/JavaHamcrest/
[33]: http://opensource.org/licenses/BSD-3-Clause
[34]: https://java.testcontainers.org
[35]: http://opensource.org/licenses/MIT
[36]: http://aws.amazon.com/asl/
[37]: https://github.com/amazon-ion/ion-java/
[38]: https://github.com/exasol/exasol-testcontainers/
[39]: https://github.com/exasol/exasol-testcontainers/blob/main/LICENSE
[40]: https://github.com/exasol/test-db-builder-java/
[41]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[42]: https://github.com/exasol/java-util-logging-testing/
[43]: https://github.com/exasol/hamcrest-resultset-matcher/
[44]: https://github.com/exasol/hamcrest-resultset-matcher/blob/main/LICENSE
[45]: https://www.jqno.nl/equalsverifier
[46]: https://logging.apache.org/log4j/2.x/log4j/log4j-api/
[47]: https://logging.apache.org/log4j/2.x/log4j/log4j-core/
[48]: http://sonarsource.github.io/sonar-scanner-maven/
[49]: http://www.gnu.org/licenses/lgpl.txt
[50]: https://maven.apache.org/plugins/maven-toolchains-plugin/
[51]: https://maven.apache.org/plugins/maven-compiler-plugin/
[52]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[53]: https://www.mojohaus.org/flatten-maven-plugin/
[54]: https://maven.apache.org/plugins/maven-shade-plugin/
[55]: https://github.com/itsallcode/openfasttrace-maven-plugin
[56]: https://www.gnu.org/licenses/gpl-3.0.html
[57]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[58]: https://maven.apache.org/surefire/maven-surefire-plugin/
[59]: https://www.mojohaus.org/versions/versions-maven-plugin/
[60]: https://basepom.github.io/duplicate-finder-maven-plugin
[61]: https://github.com/exasol/project-keeper/
[62]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[63]: https://maven.apache.org/plugins/maven-assembly-plugin/
[64]: https://maven.apache.org/plugins/maven-jar-plugin/
[65]: https://github.com/exasol/artifact-reference-checker-maven-plugin/
[66]: https://github.com/exasol/artifact-reference-checker-maven-plugin/blob/main/LICENSE
[67]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[68]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[69]: https://www.eclipse.org/legal/epl-2.0/
[70]: https://github.com/exasol/error-code-crawler-maven-plugin/
[71]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[72]: http://zlika.github.io/reproducible-build-maven-plugin
