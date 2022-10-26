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
| [Apache Commons Text][17]                   | [Apache License, Version 2.0][2]              |

## Test Dependencies

| Dependency                                      | License                                       |
| ----------------------------------------------- | --------------------------------------------- |
| [mockito-core][18]                              | [The MIT License][19]                         |
| [mockito-junit-jupiter][18]                     | [The MIT License][19]                         |
| [JUnit Jupiter (Aggregator)][20]                | [Eclipse Public License v2.0][21]             |
| [JUnit Jupiter API][20]                         | [Eclipse Public License v2.0][21]             |
| [Hamcrest][22]                                  | [BSD License 3][23]                           |
| [Testcontainers :: JUnit Jupiter Extension][24] | [MIT][25]                                     |
| [Testcontainers :: Localstack][24]              | [MIT][25]                                     |
| [AWS Java SDK for Amazon S3][10]                | [Apache License, Version 2.0][11]             |
| AWSGlueETL                                      | [Amazon Software License][26]                 |
| [Test containers for Exasol on Docker][27]      | [MIT License][28]                             |
| [Test Database Builder for Java][29]            | [MIT License][30]                             |
| [Test utilities for `java.util.logging`][31]    | [MIT][15]                                     |
| [Matcher for SQL Result Sets][32]               | [MIT License][33]                             |
| [EqualsVerifier | release normal jar][34]       | [Apache License, Version 2.0][2]              |
| [Apache Log4j][35]                              | [The Apache Software License, Version 2.0][6] |

## Plugin Dependencies

| Dependency                                              | License                                       |
| ------------------------------------------------------- | --------------------------------------------- |
| [SonarQube Scanner for Maven][36]                       | [GNU LGPL 3][37]                              |
| [Apache Maven Compiler Plugin][38]                      | [Apache License, Version 2.0][2]              |
| [Apache Maven Enforcer Plugin][39]                      | [Apache License, Version 2.0][2]              |
| [Maven Flatten Plugin][40]                              | [Apache Software Licenese][6]                 |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][41] | [ASL2][6]                                     |
| [Maven Surefire Plugin][42]                             | [Apache License, Version 2.0][2]              |
| [Versions Maven Plugin][43]                             | [Apache License, Version 2.0][2]              |
| [Apache Maven Assembly Plugin][44]                      | [Apache License, Version 2.0][2]              |
| [Apache Maven JAR Plugin][45]                           | [Apache License, Version 2.0][2]              |
| [Artifact reference checker and unifier][46]            | [MIT][15]                                     |
| [Maven Failsafe Plugin][47]                             | [Apache License, Version 2.0][2]              |
| [JaCoCo :: Maven Plugin][48]                            | [Eclipse Public License 2.0][49]              |
| [Project keeper maven plugin][50]                       | [The MIT License][51]                         |
| [error-code-crawler-maven-plugin][52]                   | [MIT License][53]                             |
| [Reproducible Build Maven Plugin][54]                   | [Apache 2.0][6]                               |
| [Apache Maven Shade Plugin][55]                         | [Apache License, Version 2.0][2]              |
| [OpenFastTrace Maven Plugin][56]                        | [GNU General Public License v3.0][57]         |
| [Maven Clean Plugin][58]                                | [The Apache Software License, Version 2.0][6] |
| [Maven Resources Plugin][59]                            | [The Apache Software License, Version 2.0][6] |
| [Maven Install Plugin][60]                              | [The Apache Software License, Version 2.0][6] |
| [Maven Deploy Plugin][61]                               | [The Apache Software License, Version 2.0][6] |
| [Maven Site Plugin 3][62]                               | [The Apache Software License, Version 2.0][6] |

[0]: https://spark.apache.org/
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
[17]: https://commons.apache.org/proper/commons-text
[18]: https://github.com/mockito/mockito
[19]: https://github.com/mockito/mockito/blob/main/LICENSE
[20]: https://junit.org/junit5/
[21]: https://www.eclipse.org/legal/epl-v20.html
[22]: http://hamcrest.org/JavaHamcrest/
[23]: http://opensource.org/licenses/BSD-3-Clause
[24]: https://testcontainers.org
[25]: http://opensource.org/licenses/MIT
[26]: http://aws.amazon.com/asl/
[27]: https://github.com/exasol/exasol-testcontainers/
[28]: https://github.com/exasol/exasol-testcontainers/blob/main/LICENSE
[29]: https://github.com/exasol/test-db-builder-java/
[30]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[31]: https://github.com/exasol/java-util-logging-testing/
[32]: https://github.com/exasol/hamcrest-resultset-matcher/
[33]: https://github.com/exasol/hamcrest-resultset-matcher/blob/main/LICENSE
[34]: https://www.jqno.nl/equalsverifier
[35]: http://logging.apache.org/log4j/1.2/
[36]: http://sonarsource.github.io/sonar-scanner-maven/
[37]: http://www.gnu.org/licenses/lgpl.txt
[38]: https://maven.apache.org/plugins/maven-compiler-plugin/
[39]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[40]: https://www.mojohaus.org/flatten-maven-plugin
[41]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[42]: https://maven.apache.org/surefire/maven-surefire-plugin/
[43]: http://www.mojohaus.org/versions-maven-plugin/
[44]: https://maven.apache.org/plugins/maven-assembly-plugin/
[45]: https://maven.apache.org/plugins/maven-jar-plugin/
[46]: https://github.com/exasol/artifact-reference-checker-maven-plugin
[47]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[48]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[49]: https://www.eclipse.org/legal/epl-2.0/
[50]: https://github.com/exasol/project-keeper/
[51]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[52]: https://github.com/exasol/error-code-crawler-maven-plugin/
[53]: https://github.com/exasol/error-code-crawler-maven-plugin/blob/main/LICENSE
[54]: http://zlika.github.io/reproducible-build-maven-plugin
[55]: https://maven.apache.org/plugins/maven-shade-plugin/
[56]: https://github.com/itsallcode/openfasttrace-maven-plugin
[57]: https://www.gnu.org/licenses/gpl-3.0.html
[58]: http://maven.apache.org/plugins/maven-clean-plugin/
[59]: http://maven.apache.org/plugins/maven-resources-plugin/
[60]: http://maven.apache.org/plugins/maven-install-plugin/
[61]: http://maven.apache.org/plugins/maven-deploy-plugin/
[62]: http://maven.apache.org/plugins/maven-site-plugin/
