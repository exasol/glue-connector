<!-- @formatter:off -->
# Dependencies

## Compile Dependencies

| Dependency                                  | License                                       |
| ------------------------------------------- | --------------------------------------------- |
| [Spark Project SQL][0]                      | [Apache 2.0 License][1]                       |
| Apache Hadoop Client Aggregator             | [Apache License, Version 2.0][2]              |
| Apache Hadoop Amazon Web Services support   | [Apache License, Version 2.0][2]              |
| [ParaNamer Core][3]                         | [BSD][4]                                      |
| [Guava: Google Core Libraries for Java][5]  | [Apache License, Version 2.0][6]              |
| [Netty/All-in-One][7]                       | [Apache License, Version 2.0][8]              |
| [jackson-databind][9]                       | [The Apache Software License, Version 2.0][6] |
| [AWS Java SDK :: Services :: Amazon S3][10] | [Apache License, Version 2.0][11]             |
| [EXASolution JDBC Driver][12]               | [EXAClient License][13]                       |
| [Exasol SQL Statement Builder][14]          | [MIT][15]                                     |
| [error-reporting-java][16]                  | [MIT][15]                                     |

## Test Dependencies

| Dependency                                      | License                                       |
| ----------------------------------------------- | --------------------------------------------- |
| [mockito-core][17]                              | [The MIT License][18]                         |
| [mockito-junit-jupiter][17]                     | [The MIT License][18]                         |
| [JUnit Jupiter (Aggregator)][19]                | [Eclipse Public License v2.0][20]             |
| [JUnit Jupiter API][19]                         | [Eclipse Public License v2.0][20]             |
| [Hamcrest][21]                                  | [BSD License 3][22]                           |
| [Testcontainers :: JUnit Jupiter Extension][23] | [MIT][24]                                     |
| [Testcontainers :: Localstack][23]              | [MIT][24]                                     |
| [AWS Java SDK for Amazon S3][10]                | [Apache License, Version 2.0][11]             |
| AWSGlueETL                                      | [Amazon Software License][25]                 |
| [Test containers for Exasol on Docker][26]      | [MIT License][27]                             |
| [Test Database Builder for Java][28]            | [MIT License][29]                             |
| [Test utilities for `java.util.logging`][30]    | [MIT][15]                                     |
| [Matcher for SQL Result Sets][31]               | [MIT License][32]                             |
| [EqualsVerifier | release normal jar][33]       | [Apache License, Version 2.0][2]              |
| [Apache Log4j][34]                              | [The Apache Software License, Version 2.0][6] |

## Plugin Dependencies

| Dependency                                              | License                                       |
| ------------------------------------------------------- | --------------------------------------------- |
| [SonarQube Scanner for Maven][35]                       | [GNU LGPL 3][36]                              |
| [Apache Maven Compiler Plugin][37]                      | [Apache License, Version 2.0][2]              |
| [Apache Maven Enforcer Plugin][38]                      | [Apache License, Version 2.0][2]              |
| [Maven Flatten Plugin][39]                              | [Apache Software Licenese][6]                 |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][40] | [ASL2][6]                                     |
| [Maven Surefire Plugin][41]                             | [Apache License, Version 2.0][2]              |
| [Versions Maven Plugin][42]                             | [Apache License, Version 2.0][2]              |
| [Apache Maven Assembly Plugin][43]                      | [Apache License, Version 2.0][2]              |
| [Apache Maven JAR Plugin][44]                           | [Apache License, Version 2.0][2]              |
| [Artifact reference checker and unifier][45]            | [MIT][15]                                     |
| [Maven Failsafe Plugin][46]                             | [Apache License, Version 2.0][2]              |
| [JaCoCo :: Maven Plugin][47]                            | [Eclipse Public License 2.0][48]              |
| [Project keeper maven plugin][49]                       | [The MIT License][50]                         |
| [error-code-crawler-maven-plugin][51]                   | [MIT License][52]                             |
| [Reproducible Build Maven Plugin][53]                   | [Apache 2.0][6]                               |
| [Apache Maven Shade Plugin][54]                         | [Apache License, Version 2.0][2]              |
| [OpenFastTrace Maven Plugin][55]                        | [GNU General Public License v3.0][56]         |
| [Maven Clean Plugin][57]                                | [The Apache Software License, Version 2.0][6] |
| [Maven Resources Plugin][58]                            | [The Apache Software License, Version 2.0][6] |
| [Maven Install Plugin][59]                              | [The Apache Software License, Version 2.0][6] |
| [Maven Deploy Plugin][60]                               | [The Apache Software License, Version 2.0][6] |
| [Maven Site Plugin 3][61]                               | [The Apache Software License, Version 2.0][6] |

[0]: http://spark.apache.org/
[1]: http://www.apache.org/licenses/LICENSE-2.0.html
[2]: https://www.apache.org/licenses/LICENSE-2.0.txt
[3]: https://github.com/paul-hammant/paranamer
[4]: LICENSE
[5]: https://github.com/google/guava
[6]: http://www.apache.org/licenses/LICENSE-2.0.txt
[7]: https://netty.io
[8]: https://www.apache.org/licenses/LICENSE-2.0
[9]: http://github.com/FasterXML/jackson
[10]: https://aws.amazon.com/sdkforjava
[11]: https://aws.amazon.com/apache2.0
[12]: http://www.exasol.com
[13]: https://docs.exasol.com/connect_exasol/drivers/jdbc.htm
[14]: https://github.com/exasol/sql-statement-builder
[15]: https://opensource.org/licenses/MIT
[16]: https://github.com/exasol/error-reporting-java
[17]: https://github.com/mockito/mockito
[18]: https://github.com/mockito/mockito/blob/main/LICENSE
[19]: https://junit.org/junit5/
[20]: https://www.eclipse.org/legal/epl-v20.html
[21]: http://hamcrest.org/JavaHamcrest/
[22]: http://opensource.org/licenses/BSD-3-Clause
[23]: https://testcontainers.org
[24]: http://opensource.org/licenses/MIT
[25]: http://aws.amazon.com/asl/
[26]: https://github.com/exasol/exasol-testcontainers/
[27]: https://github.com/exasol/exasol-testcontainers/blob/main/LICENSE
[28]: https://github.com/exasol/test-db-builder-java/
[29]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[30]: https://github.com/exasol/java-util-logging-testing/
[31]: https://github.com/exasol/hamcrest-resultset-matcher/
[32]: https://github.com/exasol/hamcrest-resultset-matcher/blob/main/LICENSE
[33]: https://www.jqno.nl/equalsverifier
[34]: http://logging.apache.org/log4j/1.2/
[35]: http://sonarsource.github.io/sonar-scanner-maven/
[36]: http://www.gnu.org/licenses/lgpl.txt
[37]: https://maven.apache.org/plugins/maven-compiler-plugin/
[38]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[39]: https://www.mojohaus.org/flatten-maven-plugin
[40]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[41]: https://maven.apache.org/surefire/maven-surefire-plugin/
[42]: http://www.mojohaus.org/versions-maven-plugin/
[43]: https://maven.apache.org/plugins/maven-assembly-plugin/
[44]: https://maven.apache.org/plugins/maven-jar-plugin/
[45]: https://github.com/exasol/artifact-reference-checker-maven-plugin
[46]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[47]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[48]: https://www.eclipse.org/legal/epl-2.0/
[49]: https://github.com/exasol/project-keeper/
[50]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[51]: https://github.com/exasol/error-code-crawler-maven-plugin/
[52]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[53]: http://zlika.github.io/reproducible-build-maven-plugin
[54]: https://maven.apache.org/plugins/maven-shade-plugin/
[55]: https://github.com/itsallcode/openfasttrace-maven-plugin
[56]: https://www.gnu.org/licenses/gpl-3.0.html
[57]: http://maven.apache.org/plugins/maven-clean-plugin/
[58]: http://maven.apache.org/plugins/maven-resources-plugin/
[59]: http://maven.apache.org/plugins/maven-install-plugin/
[60]: http://maven.apache.org/plugins/maven-deploy-plugin/
[61]: http://maven.apache.org/plugins/maven-site-plugin/
