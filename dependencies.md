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
| [Nimbus JOSE+JWT][10]                       | [The Apache Software License, Version 2.0][3] |
| [janino][11]                                | [BSD-3-Clause][12]                            |
| [commons-compiler][13]                      | [BSD-3-Clause][12]                            |
| Apache Hadoop Amazon Web Services support   | [Apache-2.0][3]                               |
| [ParaNamer Core][14]                        | [BSD][15]                                     |
| [Guava: Google Core Libraries for Java][16] | [Apache License, Version 2.0][17]             |
| [jackson-databind][18]                      | [The Apache Software License, Version 2.0][3] |
| [AWS Java SDK :: Services :: Amazon S3][19] | [Apache License, Version 2.0][20]             |
| [Exasol JDBC Driver][21]                    | [EXAClient License][22]                       |
| [Exasol SQL Statement Builder][23]          | [MIT License][24]                             |
| [spark-connector-common-java][25]           | [MIT License][26]                             |
| [error-reporting-java8][27]                 | [MIT License][28]                             |

## Test Dependencies

| Dependency                                      | License                              |
| ----------------------------------------------- | ------------------------------------ |
| [mockito-core][29]                              | [MIT][30]                            |
| [mockito-junit-jupiter][29]                     | [MIT][30]                            |
| [JUnit Jupiter (Aggregator)][31]                | [Eclipse Public License v2.0][32]    |
| [JUnit Jupiter API][31]                         | [Eclipse Public License v2.0][32]    |
| [Hamcrest][33]                                  | [BSD License 3][34]                  |
| [Testcontainers :: JUnit Jupiter Extension][35] | [MIT][36]                            |
| [Testcontainers :: Localstack][35]              | [MIT][36]                            |
| [AWS Java SDK for Amazon S3][19]                | [Apache License, Version 2.0][20]    |
| AWSGlueETL                                      | [Amazon Software License][37]        |
| [Ion Java][38]                                  | [The Apache License, Version 2.0][3] |
| [Test containers for Exasol on Docker][39]      | [MIT License][40]                    |
| [Test Database Builder for Java][41]            | [MIT License][42]                    |
| [Test utilities for `java.util.logging`][43]    | [MIT][30]                            |
| [Matcher for SQL Result Sets][44]               | [MIT License][45]                    |
| [EqualsVerifier \| release normal jar][46]      | [Apache License, Version 2.0][3]     |
| [Apache Log4j API][47]                          | [Apache-2.0][3]                      |
| [Apache Log4j Core][48]                         | [Apache-2.0][3]                      |

## Plugin Dependencies

| Dependency                                              | License                               |
| ------------------------------------------------------- | ------------------------------------- |
| [SonarQube Scanner for Maven][49]                       | [GNU LGPL 3][50]                      |
| [Apache Maven Toolchains Plugin][51]                    | [Apache-2.0][3]                       |
| [Apache Maven Compiler Plugin][52]                      | [Apache-2.0][3]                       |
| [Apache Maven Enforcer Plugin][53]                      | [Apache-2.0][3]                       |
| [Maven Flatten Plugin][54]                              | [Apache Software Licenese][3]         |
| [Apache Maven Shade Plugin][55]                         | [Apache-2.0][3]                       |
| [OpenFastTrace Maven Plugin][56]                        | [GNU General Public License v3.0][57] |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][58] | [ASL2][17]                            |
| [Maven Surefire Plugin][59]                             | [Apache-2.0][3]                       |
| [Versions Maven Plugin][60]                             | [Apache License, Version 2.0][3]      |
| [duplicate-finder-maven-plugin Maven Mojo][61]          | [Apache License 2.0][1]               |
| [Project Keeper Maven plugin][62]                       | [The MIT License][63]                 |
| [Apache Maven Assembly Plugin][64]                      | [Apache-2.0][3]                       |
| [Apache Maven JAR Plugin][65]                           | [Apache-2.0][3]                       |
| [Artifact reference checker and unifier][66]            | [MIT License][67]                     |
| [Maven Failsafe Plugin][68]                             | [Apache-2.0][3]                       |
| [JaCoCo :: Maven Plugin][69]                            | [EPL-2.0][70]                         |
| [error-code-crawler-maven-plugin][71]                   | [MIT License][72]                     |
| [Reproducible Build Maven Plugin][73]                   | [Apache 2.0][17]                      |

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
[10]: https://bitbucket.org/connect2id/nimbus-jose-jwt
[11]: http://janino-compiler.github.io/janino/
[12]: https://spdx.org/licenses/BSD-3-Clause.html
[13]: http://janino-compiler.github.io/commons-compiler/
[14]: https://github.com/paul-hammant/paranamer/paranamer
[15]: LICENSE.txt
[16]: https://github.com/google/guava
[17]: http://www.apache.org/licenses/LICENSE-2.0.txt
[18]: https://github.com/FasterXML/jackson
[19]: https://aws.amazon.com/sdkforjava
[20]: https://aws.amazon.com/apache2.0
[21]: http://www.exasol.com/
[22]: https://repo1.maven.org/maven2/com/exasol/exasol-jdbc/24.1.0/exasol-jdbc-24.1.0-license.txt
[23]: https://github.com/exasol/sql-statement-builder/
[24]: https://github.com/exasol/sql-statement-builder/blob/main/LICENSE
[25]: https://github.com/exasol/spark-connector-common-java/
[26]: https://github.com/exasol/spark-connector-common-java/blob/main/LICENSE
[27]: https://github.com/exasol/error-reporting-java/
[28]: https://github.com/exasol/error-reporting-java/blob/main/LICENSE
[29]: https://github.com/mockito/mockito
[30]: https://opensource.org/licenses/MIT
[31]: https://junit.org/junit5/
[32]: https://www.eclipse.org/legal/epl-v20.html
[33]: http://hamcrest.org/JavaHamcrest/
[34]: http://opensource.org/licenses/BSD-3-Clause
[35]: https://java.testcontainers.org
[36]: http://opensource.org/licenses/MIT
[37]: http://aws.amazon.com/asl/
[38]: https://github.com/amazon-ion/ion-java/
[39]: https://github.com/exasol/exasol-testcontainers/
[40]: https://github.com/exasol/exasol-testcontainers/blob/main/LICENSE
[41]: https://github.com/exasol/test-db-builder-java/
[42]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[43]: https://github.com/exasol/java-util-logging-testing/
[44]: https://github.com/exasol/hamcrest-resultset-matcher/
[45]: https://github.com/exasol/hamcrest-resultset-matcher/blob/main/LICENSE
[46]: https://www.jqno.nl/equalsverifier
[47]: https://logging.apache.org/log4j/2.x/log4j/log4j-api/
[48]: https://logging.apache.org/log4j/2.x/log4j/log4j-core/
[49]: http://sonarsource.github.io/sonar-scanner-maven/
[50]: http://www.gnu.org/licenses/lgpl.txt
[51]: https://maven.apache.org/plugins/maven-toolchains-plugin/
[52]: https://maven.apache.org/plugins/maven-compiler-plugin/
[53]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[54]: https://www.mojohaus.org/flatten-maven-plugin/
[55]: https://maven.apache.org/plugins/maven-shade-plugin/
[56]: https://github.com/itsallcode/openfasttrace-maven-plugin
[57]: https://www.gnu.org/licenses/gpl-3.0.html
[58]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[59]: https://maven.apache.org/surefire/maven-surefire-plugin/
[60]: https://www.mojohaus.org/versions/versions-maven-plugin/
[61]: https://basepom.github.io/duplicate-finder-maven-plugin
[62]: https://github.com/exasol/project-keeper/
[63]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[64]: https://maven.apache.org/plugins/maven-assembly-plugin/
[65]: https://maven.apache.org/plugins/maven-jar-plugin/
[66]: https://github.com/exasol/artifact-reference-checker-maven-plugin/
[67]: https://github.com/exasol/artifact-reference-checker-maven-plugin/blob/main/LICENSE
[68]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[69]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[70]: https://www.eclipse.org/legal/epl-2.0/
[71]: https://github.com/exasol/error-code-crawler-maven-plugin/
[72]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[73]: http://zlika.github.io/reproducible-build-maven-plugin
