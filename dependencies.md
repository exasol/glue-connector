<!-- @formatter:off -->
# Dependencies

## Compile Dependencies

| Dependency                                  | License                                       |
| ------------------------------------------- | --------------------------------------------- |
| [Spark Project SQL][0]                      | [Apache 2.0 License][1]                       |
| Apache Hadoop Client Aggregator             | [Apache License, Version 2.0][2]              |
| Apache Hadoop Amazon Web Services support   | [Apache License, Version 2.0][2]              |
| [ParaNamer Core][4]                         | [BSD][5]                                      |
| [Guava: Google Core Libraries for Java][6]  | [Apache License, Version 2.0][7]              |
| [Netty/All-in-One][8]                       | [Apache License, Version 2.0][9]              |
| [jackson-databind][10]                      | [The Apache Software License, Version 2.0][7] |
| [AWS Java SDK :: Services :: Amazon S3][12] | [Apache License, Version 2.0][13]             |
| [EXASolution JDBC Driver][14]               | [EXAClient License][15]                       |
| [Exasol SQL Statement Builder][16]          | [MIT][17]                                     |
| [error-reporting-java][18]                  | [MIT][17]                                     |

## Test Dependencies

| Dependency                                      | License                                       |
| ----------------------------------------------- | --------------------------------------------- |
| [mockito-core][20]                              | [The MIT License][21]                         |
| [mockito-junit-jupiter][20]                     | [The MIT License][21]                         |
| [JUnit Jupiter (Aggregator)][24]                | [Eclipse Public License v2.0][25]             |
| [Hamcrest][26]                                  | [BSD License 3][27]                           |
| [Testcontainers :: JUnit Jupiter Extension][28] | [MIT][29]                                     |
| [Testcontainers :: Localstack][28]              | [MIT][29]                                     |
| [AWS Java SDK for Amazon S3][12]                | [Apache License, Version 2.0][13]             |
| [Test containers for Exasol on Docker][34]      | [MIT][17]                                     |
| [Test Database Builder for Java][36]            | [MIT License][37]                             |
| [Matcher for SQL Result Sets][38]               | [MIT][17]                                     |
| [EqualsVerifier | release normal jar][40]       | [Apache License, Version 2.0][2]              |
| [Apache Log4j][42]                              | [The Apache Software License, Version 2.0][7] |

## Plugin Dependencies

| Dependency                                              | License                               |
| ------------------------------------------------------- | ------------------------------------- |
| [Apache Maven Compiler Plugin][44]                      | [Apache License, Version 2.0][2]      |
| [Apache Maven Enforcer Plugin][46]                      | [Apache License, Version 2.0][2]      |
| [Maven Surefire Plugin][48]                             | [Apache License, Version 2.0][2]      |
| [Maven Failsafe Plugin][50]                             | [Apache License, Version 2.0][2]      |
| [JaCoCo :: Maven Plugin][52]                            | [Eclipse Public License 2.0][53]      |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][54] | [ASL2][7]                             |
| [Versions Maven Plugin][56]                             | [Apache License, Version 2.0][2]      |
| [Artifact reference checker and unifier][58]            | [MIT][17]                             |
| [Project keeper maven plugin][60]                       | [MIT][17]                             |
| [Apache Maven JAR Plugin][62]                           | [Apache License, Version 2.0][2]      |
| [Apache Maven Shade Plugin][64]                         | [Apache License, Version 2.0][2]      |
| [OpenFastTrace Maven Plugin][66]                        | [GNU General Public License v3.0][67] |
| [Reproducible Build Maven Plugin][68]                   | [Apache 2.0][7]                       |
| [Apache Maven Clean Plugin][70]                         | [Apache License, Version 2.0][2]      |
| [Apache Maven Resources Plugin][72]                     | [Apache License, Version 2.0][2]      |
| [Apache Maven Install Plugin][74]                       | [Apache License, Version 2.0][7]      |
| [Apache Maven Deploy Plugin][76]                        | [Apache License, Version 2.0][7]      |
| [Apache Maven Site Plugin][78]                          | [Apache License, Version 2.0][2]      |

[60]: https://github.com/exasol/project-keeper-maven-plugin
[4]: https://github.com/paul-hammant/paranamer/paranamer
[18]: https://github.com/exasol/error-reporting-java
[7]: http://www.apache.org/licenses/LICENSE-2.0.txt
[48]: https://maven.apache.org/surefire/maven-surefire-plugin/
[12]: https://aws.amazon.com/sdkforjava
[15]: https://docs.exasol.com/connect_exasol/drivers/jdbc.htm
[8]: https://netty.io/netty-all/
[17]: https://opensource.org/licenses/MIT
[20]: https://github.com/mockito/mockito
[56]: http://www.mojohaus.org/versions-maven-plugin/
[64]: https://maven.apache.org/plugins/maven-shade-plugin/
[27]: http://opensource.org/licenses/BSD-3-Clause
[44]: https://maven.apache.org/plugins/maven-compiler-plugin/
[72]: https://maven.apache.org/plugins/maven-resources-plugin/
[37]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[66]: https://github.com/itsallcode/openfasttrace-maven-plugin
[70]: https://maven.apache.org/plugins/maven-clean-plugin/
[53]: https://www.eclipse.org/legal/epl-2.0/
[10]: http://github.com/FasterXML/jackson
[9]: https://www.apache.org/licenses/LICENSE-2.0
[52]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[13]: https://aws.amazon.com/apache2.0
[21]: https://github.com/mockito/mockito/blob/main/LICENSE
[38]: https://github.com/exasol/hamcrest-resultset-matcher
[68]: http://zlika.github.io/reproducible-build-maven-plugin
[24]: https://junit.org/junit5/
[42]: http://logging.apache.org/log4j/1.2/
[5]: LICENSE.txt
[26]: http://hamcrest.org/JavaHamcrest/
[58]: https://github.com/exasol/artifact-reference-checker-maven-plugin
[62]: https://maven.apache.org/plugins/maven-jar-plugin/
[36]: https://github.com/exasol/test-db-builder-java/
[6]: https://github.com/google/guava
[1]: http://www.apache.org/licenses/LICENSE-2.0.html
[50]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[16]: https://github.com/exasol/sql-statement-builder
[29]: http://opensource.org/licenses/MIT
[34]: https://github.com/exasol/exasol-testcontainers
[78]: https://maven.apache.org/plugins/maven-site-plugin/
[67]: https://www.gnu.org/licenses/gpl-3.0.html
[2]: https://www.apache.org/licenses/LICENSE-2.0.txt
[40]: https://www.jqno.nl/equalsverifier
[46]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[14]: http://www.exasol.com
[25]: https://www.eclipse.org/legal/epl-v20.html
[74]: http://maven.apache.org/plugins/maven-install-plugin/
[54]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[28]: https://testcontainers.org
[0]: http://spark.apache.org/
[76]: http://maven.apache.org/plugins/maven-deploy-plugin/
