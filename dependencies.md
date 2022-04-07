<!-- @formatter:off -->
# Dependencies

## Compile Dependencies

| Dependency                                  | License                           |
| ------------------------------------------- | --------------------------------- |
| [Spark Project SQL][0]                      | [Apache 2.0 License][1]           |
| Apache Hadoop Common                        | [Apache License, Version 2.0][2]  |
| Apache Hadoop Amazon Web Services support   | [Apache License, Version 2.0][2]  |
| [ParaNamer Core][4]                         | [BSD][5]                          |
| [Guava: Google Core Libraries for Java][6]  | [Apache License, Version 2.0][7]  |
| [Netty/All-in-One][8]                       | [Apache License, Version 2.0][9]  |
| [AWS Java SDK :: Services :: Amazon S3][10] | [Apache License, Version 2.0][11] |
| [EXASolution JDBC Driver][12]               | [EXAClient License][13]           |
| [Exasol SQL Statement Builder][14]          | [MIT][15]                         |
| [error-reporting-java][16]                  | [MIT][15]                         |

## Test Dependencies

| Dependency                                      | License                                       |
| ----------------------------------------------- | --------------------------------------------- |
| [mockito-core][18]                              | [The MIT License][19]                         |
| [mockito-junit-jupiter][18]                     | [The MIT License][19]                         |
| [JUnit Jupiter (Aggregator)][22]                | [Eclipse Public License v2.0][23]             |
| [Hamcrest][24]                                  | [BSD License 3][25]                           |
| [Testcontainers :: JUnit Jupiter Extension][26] | [MIT][27]                                     |
| [Testcontainers :: Localstack][26]              | [MIT][27]                                     |
| [AWS Java SDK for Amazon S3][10]                | [Apache License, Version 2.0][11]             |
| [Test containers for Exasol on Docker][32]      | [MIT][15]                                     |
| [Test Database Builder for Java][34]            | [MIT][15]                                     |
| [Matcher for SQL Result Sets][36]               | [MIT][15]                                     |
| [EqualsVerifier | release normal jar][38]       | [Apache License, Version 2.0][2]              |
| [Apache Log4j][40]                              | [The Apache Software License, Version 2.0][7] |

## Plugin Dependencies

| Dependency                                              | License                               |
| ------------------------------------------------------- | ------------------------------------- |
| [Apache Maven Compiler Plugin][42]                      | [Apache License, Version 2.0][2]      |
| [Apache Maven Enforcer Plugin][44]                      | [Apache License, Version 2.0][2]      |
| [Maven Surefire Plugin][46]                             | [Apache License, Version 2.0][2]      |
| [Maven Failsafe Plugin][48]                             | [Apache License, Version 2.0][2]      |
| [JaCoCo :: Maven Plugin][50]                            | [Eclipse Public License 2.0][51]      |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][52] | [ASL2][7]                             |
| [Versions Maven Plugin][54]                             | [Apache License, Version 2.0][2]      |
| [Artifact reference checker and unifier][56]            | [MIT][15]                             |
| [Project keeper maven plugin][58]                       | [MIT][15]                             |
| [Apache Maven Assembly Plugin][60]                      | [Apache License, Version 2.0][2]      |
| [OpenFastTrace Maven Plugin][62]                        | [GNU General Public License v3.0][63] |
| [Reproducible Build Maven Plugin][64]                   | [Apache 2.0][7]                       |
| [Apache Maven JAR Plugin][66]                           | [Apache License, Version 2.0][2]      |
| [Apache Maven Clean Plugin][68]                         | [Apache License, Version 2.0][2]      |
| [Apache Maven Resources Plugin][70]                     | [Apache License, Version 2.0][2]      |
| [Apache Maven Install Plugin][72]                       | [Apache License, Version 2.0][7]      |
| [Apache Maven Deploy Plugin][74]                        | [Apache License, Version 2.0][7]      |
| [Apache Maven Site Plugin][76]                          | [Apache License, Version 2.0][2]      |

[58]: https://github.com/exasol/project-keeper-maven-plugin
[4]: https://github.com/paul-hammant/paranamer/paranamer
[16]: https://github.com/exasol/error-reporting-java
[7]: http://www.apache.org/licenses/LICENSE-2.0.txt
[46]: https://maven.apache.org/surefire/maven-surefire-plugin/
[10]: https://aws.amazon.com/sdkforjava
[13]: https://docs.exasol.com/connect_exasol/drivers/jdbc.htm
[8]: https://netty.io/netty-all/
[15]: https://opensource.org/licenses/MIT
[18]: https://github.com/mockito/mockito
[54]: http://www.mojohaus.org/versions-maven-plugin/
[25]: http://opensource.org/licenses/BSD-3-Clause
[42]: https://maven.apache.org/plugins/maven-compiler-plugin/
[70]: https://maven.apache.org/plugins/maven-resources-plugin/
[62]: https://github.com/itsallcode/openfasttrace-maven-plugin
[68]: https://maven.apache.org/plugins/maven-clean-plugin/
[51]: https://www.eclipse.org/legal/epl-2.0/
[9]: https://www.apache.org/licenses/LICENSE-2.0
[50]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[11]: https://aws.amazon.com/apache2.0
[19]: https://github.com/mockito/mockito/blob/main/LICENSE
[36]: https://github.com/exasol/hamcrest-resultset-matcher
[64]: http://zlika.github.io/reproducible-build-maven-plugin
[22]: https://junit.org/junit5/
[40]: http://logging.apache.org/log4j/1.2/
[5]: LICENSE.txt
[24]: http://hamcrest.org/JavaHamcrest/
[56]: https://github.com/exasol/artifact-reference-checker-maven-plugin
[66]: https://maven.apache.org/plugins/maven-jar-plugin/
[6]: https://github.com/google/guava
[1]: http://www.apache.org/licenses/LICENSE-2.0.html
[48]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[34]: https://github.com/exasol/test-db-builder-java
[14]: https://github.com/exasol/sql-statement-builder
[27]: http://opensource.org/licenses/MIT
[32]: https://github.com/exasol/exasol-testcontainers
[76]: https://maven.apache.org/plugins/maven-site-plugin/
[63]: https://www.gnu.org/licenses/gpl-3.0.html
[2]: https://www.apache.org/licenses/LICENSE-2.0.txt
[38]: https://www.jqno.nl/equalsverifier
[44]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[12]: http://www.exasol.com
[23]: https://www.eclipse.org/legal/epl-v20.html
[72]: http://maven.apache.org/plugins/maven-install-plugin/
[52]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[26]: https://testcontainers.org
[0]: http://spark.apache.org/
[74]: http://maven.apache.org/plugins/maven-deploy-plugin/
[60]: https://maven.apache.org/plugins/maven-assembly-plugin/
