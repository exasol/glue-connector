package com.exasol.glue.ittests;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Logger;

import com.exasol.containers.ExasolContainer;
import com.exasol.dbbuilder.dialects.exasol.ExasolObjectFactory;
import com.exasol.dbbuilder.dialects.exasol.ExasolSchema;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.junit.jupiter.Container;

public class BaseIntegrationTest {
    private static final Logger LOGGER = Logger.getLogger(BaseIntegrationTest.class.getName());
    private static final String DEFAULT_DOCKER_IMAGE = "7.1.6";

    @Container
    private static final ExasolContainer<? extends ExasolContainer<?>> EXASOL = new ExasolContainer<>(
            getExasolDockerImage()).withReuse(true);

    protected static Connection connection;
    protected static ExasolObjectFactory factory;
    protected static ExasolSchema schema;
    protected static SparkSession spark;

    @BeforeAll
    static void beforeAll() throws SQLException {
        EXASOL.purgeDatabase();
        connection = EXASOL.createConnection();
        factory = new ExasolObjectFactory(connection);
        schema = factory.createSchema("DEFAULT_SCHEMA");
        spark = SparkSessionProvider.getSparkSession(getSparkConf());
    }

    @AfterAll
    static void afterAll() throws SQLException {
        dropSchema();
        connection.close();
    }

    public void createSchema(final String schemaName) {
        LOGGER.fine(() -> "Creating a new schema '" + schemaName + '"');
        dropSchema();
        schema = factory.createSchema(schemaName);
    }

    public String getJdbcUrl() {
        return EXASOL.getJdbcUrl();
    }

    public String getUsername() {
        return EXASOL.getUsername();
    }

    public String getPassword() {
        return EXASOL.getPassword();
    }

    public Map<String, String> getDefaultOptions() {
        return Map.of("jdbc_url", getJdbcUrl(), "username", getUsername(), "password", getPassword());
    }

    private static void dropSchema() {
        if (schema != null) {
            schema.drop();
            schema = null;
        }
    }

    private static String getExasolDockerImage() {
        return System.getProperty("com.exasol.dockerdb.image", DEFAULT_DOCKER_IMAGE);
    }

    private static SparkConf getSparkConf() {
        return new SparkConf() //
                .setMaster("local[*]") //
                .setAppName("Tests") //
                .set("spark.ui.enabled", "false") //
                .set("spark.app.id", getRandomAppId()) //
                .set("spark.driver.host", "localhost");
    }

    private static String getRandomAppId() {
        return "SparkAppID" + (int) (Math.random() * 1000 + 1);
    }

}
