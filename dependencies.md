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
| AWSGlueETL                                      | [Amazon Software License][34]                 |
| [Test containers for Exasol on Docker][35]      | [MIT][17]                                     |
| [Test Database Builder for Java][37]            | [MIT License][38]                             |
| [Test utilities for `java.util.logging`][39]    | [MIT][17]                                     |
| [Matcher for SQL Result Sets][41]               | [MIT][17]                                     |
| [EqualsVerifier | release normal jar][43]       | [Apache License, Version 2.0][2]              |
| [Apache Log4j][45]                              | [The Apache Software License, Version 2.0][7] |

## Plugin Dependencies

| Dependency                                              | License                                       |
| ------------------------------------------------------- | --------------------------------------------- |
| [SonarQube Scanner for Maven][47]                       | [GNU LGPL 3][48]                              |
| [Apache Maven Compiler Plugin][49]                      | [Apache License, Version 2.0][2]              |
| [Apache Maven Enforcer Plugin][51]                      | [Apache License, Version 2.0][2]              |
| [Maven Flatten Plugin][53]                              | [Apache Software Licenese][7]                 |
| [org.sonatype.ossindex.maven:ossindex-maven-plugin][55] | [ASL2][7]                                     |
| [Reproducible Build Maven Plugin][57]                   | [Apache 2.0][7]                               |
| [Maven Surefire Plugin][59]                             | [Apache License, Version 2.0][2]              |
| [Versions Maven Plugin][61]                             | [Apache License, Version 2.0][2]              |
| [Apache Maven Assembly Plugin][63]                      | [Apache License, Version 2.0][2]              |
| [Apache Maven JAR Plugin][65]                           | [Apache License, Version 2.0][2]              |
| [Artifact reference checker and unifier][67]            | [MIT][17]                                     |
| [Maven Failsafe Plugin][69]                             | [Apache License, Version 2.0][2]              |
| [JaCoCo :: Maven Plugin][71]                            | [Eclipse Public License 2.0][72]              |
| [Project keeper maven plugin][73]                       | [The MIT License][74]                         |
| [error-code-crawler-maven-plugin][75]                   | [MIT][17]                                     |
| [Apache Maven Shade Plugin][77]                         | [Apache License, Version 2.0][2]              |
| [OpenFastTrace Maven Plugin][79]                        | [GNU General Public License v3.0][80]         |
| [Maven Clean Plugin][81]                                | [The Apache Software License, Version 2.0][7] |
| [Maven Resources Plugin][83]                            | [The Apache Software License, Version 2.0][7] |
| [Maven Install Plugin][85]                              | [The Apache Software License, Version 2.0][7] |
| [Maven Deploy Plugin][87]                               | [The Apache Software License, Version 2.0][7] |
| [Maven Site Plugin 3][89]                               | [The Apache Software License, Version 2.0][7] |

[18]: https://github.com/exasol/error-reporting-java
[7]: http://www.apache.org/licenses/LICENSE-2.0.txt
[59]: https://maven.apache.org/surefire/maven-surefire-plugin/
[81]: http://maven.apache.org/plugins/maven-clean-plugin/
[34]: http://aws.amazon.com/asl/
[12]: https://aws.amazon.com/sdkforjava
[15]: https://docs.exasol.com/connect_exasol/drivers/jdbc.htm
[17]: https://opensource.org/licenses/MIT
[20]: https://github.com/mockito/mockito
[61]: http://www.mojohaus.org/versions-maven-plugin/
[73]: https://github.com/exasol/project-keeper/
[77]: https://maven.apache.org/plugins/maven-shade-plugin/
[27]: http://opensource.org/licenses/BSD-3-Clause
[49]: https://maven.apache.org/plugins/maven-compiler-plugin/
[38]: https://github.com/exasol/test-db-builder-java/blob/main/LICENSE
[79]: https://github.com/itsallcode/openfasttrace-maven-plugin
[72]: https://www.eclipse.org/legal/epl-2.0/
[10]: http://github.com/FasterXML/jackson
[48]: http://www.gnu.org/licenses/lgpl.txt
[5]: LICENSE
[9]: https://www.apache.org/licenses/LICENSE-2.0
[39]: https://github.com/exasol/java-util-logging-testing
[71]: https://www.jacoco.org/jacoco/trunk/doc/maven.html
[13]: https://aws.amazon.com/apache2.0
[21]: https://github.com/mockito/mockito/blob/main/LICENSE
[41]: https://github.com/exasol/hamcrest-resultset-matcher
[57]: http://zlika.github.io/reproducible-build-maven-plugin
[47]: http://sonarsource.github.io/sonar-scanner-maven/
[24]: https://junit.org/junit5/
[45]: http://logging.apache.org/log4j/1.2/
[26]: http://hamcrest.org/JavaHamcrest/
[83]: http://maven.apache.org/plugins/maven-resources-plugin/
[67]: https://github.com/exasol/artifact-reference-checker-maven-plugin
[65]: https://maven.apache.org/plugins/maven-jar-plugin/
[37]: https://github.com/exasol/test-db-builder-java/
[6]: https://github.com/google/guava
[1]: http://www.apache.org/licenses/LICENSE-2.0.html
[8]: https://netty.io
[69]: https://maven.apache.org/surefire/maven-failsafe-plugin/
[16]: https://github.com/exasol/sql-statement-builder
[4]: https://github.com/paul-hammant/paranamer
[29]: http://opensource.org/licenses/MIT
[35]: https://github.com/exasol/exasol-testcontainers
[74]: https://github.com/exasol/project-keeper/blob/main/LICENSE
[80]: https://www.gnu.org/licenses/gpl-3.0.html
[2]: https://www.apache.org/licenses/LICENSE-2.0.txt
[43]: https://www.jqno.nl/equalsverifier
[51]: https://maven.apache.org/enforcer/maven-enforcer-plugin/
[14]: http://www.exasol.com
[25]: https://www.eclipse.org/legal/epl-v20.html
[53]: https://www.mojohaus.org/flatten-maven-plugin
[85]: http://maven.apache.org/plugins/maven-install-plugin/
[55]: https://sonatype.github.io/ossindex-maven/maven-plugin/
[28]: https://testcontainers.org
[0]: http://spark.apache.org/
[87]: http://maven.apache.org/plugins/maven-deploy-plugin/
[89]: http://maven.apache.org/plugins/maven-site-plugin/
[75]: https://github.com/exasol/error-code-crawler-maven-plugin
[63]: https://maven.apache.org/plugins/maven-assembly-plugin/
