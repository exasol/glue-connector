package com.exasol.glue.ittests;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.Container.ExecResult;
import org.testcontainers.containers.localstack.LocalStackContainer.Service;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import com.exasol.containers.ExasolContainer;
import com.exasol.dbbuilder.dialects.exasol.ExasolObjectFactory;
import com.exasol.dbbuilder.dialects.exasol.ExasolSchema;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

public class BaseIntegrationTestSetup {
    private static final Logger LOGGER = Logger.getLogger(BaseIntegrationTestSetup.class.getName());
    private static final String DEFAULT_DOCKER_IMAGE = "8.32.0";
    protected static final String DEFAULT_BUCKET_NAME = "csvtest";

    @Container
    @SuppressWarnings("resource") // Will be closed by @Testcontainers
    private static final ExasolContainer<? extends ExasolContainer<?>> EXASOL = new ExasolContainer<>(
            getExasolDockerImage()).withReuse(true);
    @Container
    private static final S3LocalStackContainerWithReuse S3 = new S3LocalStackContainerWithReuse(
            DockerImageName.parse("localstack/localstack:3.8"));

    protected static Connection connection;
    protected static ExasolObjectFactory factory;
    protected static ExasolSchema schema;
    protected static SparkSession spark;
    protected static S3Client s3Client;

    @BeforeAll
    public static void beforeAll() throws SQLException {
        EXASOL.purgeDatabase();
        connection = EXASOL.createConnection();
        factory = new ExasolObjectFactory(connection);
        schema = factory.createSchema("DEFAULT_SCHEMA");
        spark = SparkSessionProvider.getSparkSession(getSparkConf());
        s3Client = S3Client.builder() //
                .endpointOverride(S3.getEndpointOverride(Service.S3)) //
                .credentialsProvider(StaticCredentialsProvider
                        .create(AwsBasicCredentials.create(S3.getAccessKey(), S3.getSecretKey()))) //
                .region(Region.of(S3.getRegion())) //
                .build();
        LOGGER.info(() -> "Created localstack S3 client with region '" + S3.getRegion() + "'.");
        updateExasolContainerHostsFile();
        createBucket(DEFAULT_BUCKET_NAME);
    }

    @AfterAll
    public static void afterAll() throws SQLException {
        dropSchema();
        connection.close();
    }

    public static void createBucket(final String bucketName) {
        LOGGER.info(() -> "Creating S3 bucket '" + bucketName + "'.");
        s3Client.createBucket(b -> b.bucket(bucketName));
    }

    public boolean isBucketEmpty(final String bucketName) {
        final List<S3Object> objects = s3Client.listObjects(ListObjectsRequest.builder().bucket(bucketName).build())
                .contents();
        return objects.isEmpty();
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
        final String endpointOverride = DockerClientFactory.instance().dockerHostIpAddress() + ":"
                + S3.getMappedPort(4566);
        final Map<String, String> map = new HashMap<>(Map.of( //
                "jdbc_url", getJdbcUrl(), //
                "username", getUsername(), //
                "password", getPassword(), //
                "awsAccessKeyId", S3.getAccessKey(), //
                "awsSecretAccessKey", S3.getSecretKey(), //
                "awsRegion", S3.getRegion(), //
                "s3Bucket", DEFAULT_BUCKET_NAME, //
                "s3PathStyleAccess", "true", //
                "awsEndpointOverride", endpointOverride));
        map.put("useSsl", "false");
        map.put("numPartitions", "3");
        map.put("replaceLocalhostByDefaultS3Endpoint", "true");
        return map;
    }

    public Dataset<Row> loadTable(final String tableName) {
        return spark.read() //
                .format("exasol") //
                .option("table", tableName) //
                .options(getDefaultOptions()) //
                .load();
    }

    private static void dropSchema() {
        if (schema != null) {
            LOGGER.fine(() -> "Dropping schema '" + schema.getName() + '"');
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

    private static void updateExasolContainerHostsFile() {
        final List<String> commands = List.of( //
                "sed -i '/amazonaws/d' /etc/hosts", //
                "echo '" + getS3ContainerInternalIp() + " csvtest.s3.amazonaws.com' >> /etc/hosts");
        commands.forEach(command -> {
            try {
                final ExecResult exitCode = EXASOL.execInContainer("/bin/sh", "-c", command);
                if (exitCode.getExitCode() != 0) {
                    throw new RuntimeException(
                            "Command to update Exasol container `/etc/hosts` file returned non-zero result.");
                }
            } catch (final InterruptedException | IOException exception) {
                throw new RuntimeException("Failed to update Exasol container `/etc/hosts`.", exception);
            }
        });
    }

    private static String getS3ContainerInternalIp() {
        return S3.getContainerInfo().getNetworkSettings().getNetworks().values().iterator().next().getGateway();
    }

}
