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
| Apache Hadoop Amazon Web Services support   | [Apache-2.0][3]                               |
| [ParaNamer Core][9]                         | [BSD][10]                                     |
| [Guava: Google Core Libraries for Java][11] | [Apache License, Version 2.0][12]             |
| [jackson-databind][13]                      | [The Apache Software License, Version 2.0][3] |
| [AWS Java SDK :: Services :: Amazon S3][14] | [Apache License, Version 2.0][15]             |
| [Exasol JDBC Driver][16]                    | [EXAClient License][17]                       |
| [Exasol SQL Statement Builder][18]          | [MIT License][19]                             |
| [spark-connector-common-java][20]           | [MIT License][21]                             |
| [error-reporting-java8][22]                 | [MIT License][23]                             |

## Test Dependencies

| Dependency                                      | License                              |
| ----------------------------------------------- | ------------------------------------ |
| [mockito-core][24]                              | [MIT][25]                            |
| [mockito-junit-jupiter][24]                     | [MIT][25]                            |
| [JUnit Jupiter (Aggregator)][26]                | [Eclipse Public License v2.0][27]    |
| [JUnit Jupiter API][26]                         | [Eclipse Public License v2.0][27]    |
| [Hamcrest][28]                                  | [BSD License 3][29]                  |
| [Testcontainers :: JUnit Jupiter Extension][30] | [MIT][31]                            |
| [Testcontainers :: Localstack][30]              | [MIT][31]                            |
| [AWS Java SDK for Amazon S3][14]                | [Apache License, Version 2.0][15]    |
| AWSGlueETL                                      | [Amazon Software License][32]        |
| [Ion Java][33]                                  | [The Apache License, Version 2.0][3] |
| [Test containers for Exasol on Docker][34]      | [MIT License][35]                    |
| [Test Database Builder for Java][36]            | [MIT License][37]                    |
| [Test utilities for `java.util.logging`][38]    | [MIT][25]                            |
| [Matcher for SQL Result Sets][39]               | [MIT License][40]                    |
| [EqualsVerifier \| release normal jar][41]      | [Apache License, Version 2.0][3]     |
| [Apache Log4j API][42]                          | [Apache-2.0][3]                      |
| [Apache Log4j Core][43]                         | [Apache-2.0][3]                      |

## Plugin Dependencies

| Dependency                                              | License                               |
| ------------------------------------------------------- | ------------------------------------- |
| [SonarQube Scanner for Maven][44]                       | [GNU LGPL 3][45]                      |
| [Apache Maven Toolchains Plugin][46]                    | [Apache License, Version 2.0][3]      |
| [Apache Maven Compiler Plugin][47]                      | [Apache-2.0][3]                       |
| [Apache Maven Enforcer Plugin][48]                      | [Apache-2.0][3]                       |
| [Maven Flatten Plugin][49]                              | [Apache Software Licenese][3]         |
| [Apache Maven Shade Plugin][50]                         | [Apache-2.0][3]                       |
| [OpenFastTrace Maven Plugin][51]                        | [GNU General Public License v3.0][52] |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][53] | [ASL2][12]                            |
| [Maven Surefire Plugin][54]                             | [Apache-2.0][3]                       |
| [Versions Maven Plugin][55]                             | [Apache License, Version 2.0][3]      |
| [duplicate-finder-maven-plugin Maven Mojo][56]          | [Apache License 2.0][1]               |
| [Project Keeper Maven plugin][57]                       | [The MIT License][58]                 |
| [Apache Maven Assembly Plugin][59]                      | [Apache-2.0][3]                       |
| [Apache Maven JAR Plugin][60]                           | [Apache License, Version 2.0][3]      |
| [Artifact reference checker and unifier][61]            | [MIT License][62]                     |
| [Maven Failsafe Plugin][63]                             | [Apache-2.0][3]                       |
| [JaCoCo :: Maven Plugin][64]                            | [EPL-2.0][65]                         |
| [error-code-crawler-maven-plugin][66]                   | [MIT License][67]                     |
| [Reproducible Build Maven Plugin][68]                   | [Apache 2.0][12]                      |

[0]: https://spark.apache.org/
[1]: http://www.apache.org/licenses/LICENSE-2.0.html
[2]: http://zookeeper.apache.org/zookeeper
[3]: https://www.apache.org/licenses/LICENSE-2.0.txt
[4]: https://avro.apache.org
[5]: https://commons.apache.org/proper/commons-compress/
[6]: https://github.com/xerial/snappy-java
[7]: https://www.apache.org/licenses/LICENSE-2.0.html
[8]: https://commons.apache.org/proper/commons-configuration/
[9]: https://github.com/paul-hammant/paranamer/paranamer
[10]: LICENSE.txt
[11]: https://github.com/google/guava
[12]: http://www.apache.org/licenses/LICENSE-2.0.txt
[13]: https://github.com/FasterXML/jackson
[14]: https://aws.amazon.com/sdkforjava
[15]: https://aws.amazon.com/apache2.0
[16]: http://www.exasol.com/
[17]: https://repo1.maven.org/maven2/com/exasol/exasol-jdbc/24.1.0/exasol-jdbc-24.1.0-license.txt
[18]: https://github.com/exasol/sql-statement-builder/
[19]: https://github.com/exasol/sql-statement-builder/blob/main/LICENSE
[20]: https://github.com/exasol/spark-connector-common-java/
[21]: https://github.com/exasol/spark-connector-common-java/blob/main/LICENSE
[22]: https://github.com/exasol/error-reporting-java/
[23]: https://github.com/exasol/error-reporting-java/blob/main/LICENSE
[24]: https://github.com/mockito/mockito
[25]: https://opensource.org/licenses/MIT
[26]: https://junit.org/junit5/
[27]: https://www.eclipse.org/legal/epl-v20.html
[28]: http://hamcrest.org/JavaHamcrest/
[29]: http://opensource.org/licenses/BSD-3-Clause
[30]: https://java.testcontainers.org
[31]: http://opensource.org/licenses/MIT
[32]: http://aws.amazon.com/asl/
[33]: https://github.com/amazon-ion/ion-java/
[34]: https://github.com/exasol/exasol-testcontainers/
[35]: https://github.com/exasol/exasol-testcontainers/blob/main/LICENSE
[36]: https://github.com/exasol/test-db-builder-java/
[37]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[38]: https://github.com/exasol/java-util-logging-testing/
[39]: https://github.com/exasol/hamcrest-resultset-matcher/
[40]: https://github.com/exasol/hamcrest-resultset-matcher/blob/main/LICENSE
[41]: https://www.jqno.nl/equalsverifier
[42]: https://logging.apache.org/log4j/2.x/log4j/log4j-api/
[43]: https://logging.apache.org/log4j/2.x/log4j/log4j-core/
[44]: http://sonarsource.github.io/sonar-scanner-maven/
[45]: http://www.gnu.org/licenses/lgpl.txt
[46]: https://maven.apache.org/plugins/maven-toolchains-plugin/
[47]: https://maven.apache.org/plugins/maven-compiler-plugin/
[48]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[49]: https://www.mojohaus.org/flatten-maven-plugin/
[50]: https://maven.apache.org/plugins/maven-shade-plugin/
[51]: https://github.com/itsallcode/openfasttrace-maven-plugin
[52]: https://www.gnu.org/licenses/gpl-3.0.html
[53]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[54]: https://maven.apache.org/surefire/maven-surefire-plugin/
[55]: https://www.mojohaus.org/versions/versions-maven-plugin/
[56]: https://basepom.github.io/duplicate-finder-maven-plugin
[57]: https://github.com/exasol/project-keeper/
[58]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[59]: https://maven.apache.org/plugins/maven-assembly-plugin/
[60]: https://maven.apache.org/plugins/maven-jar-plugin/
[61]: https://github.com/exasol/artifact-reference-checker-maven-plugin/
[62]: https://github.com/exasol/artifact-reference-checker-maven-plugin/blob/main/LICENSE
[63]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[64]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[65]: https://www.eclipse.org/legal/epl-2.0/
[66]: https://github.com/exasol/error-code-crawler-maven-plugin/
[67]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[68]: http://zlika.github.io/reproducible-build-maven-plugin
