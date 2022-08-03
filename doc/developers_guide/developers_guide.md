# Developers Guide

This guide contains information for developers.

## Creating Custom Connector

AWS Glue Studio allows to create a custom connectors. It can be useful to debug the connector.

To test connector by creating a custom connector, please follow these steps.

### Creating an Assembly Jar

By running `mvn verify` or `mvn package` create an connector artifact. For example, `target/exasol-glue-connector-0.1.0-assembly.jar`.

### Uploading the Artifact to S3 Bucket

Upload the JAR artifact from previous step into an S3 bucket. For instance, `s3://exasol-artifacts/glue-connector/`.

### Creating a Glue Studio Custom Connector

Go to AWS Glue Studio, and click on "Connectors" &rarr; "Create Custom Connector".

![Exasol AWS Glue Studio Custom Connectors](img/custom_connector.png)

Then select `Spark` as "Connection type" from drop-down list and fill other necessary fields.

- "Browse S3" and select the uploaded JAR artifact from previous step
- Enter unique and descriptive name for the connector
- Enter `exasol` as "Class name"
- And click to the "Create connector" button

![Exasol AWS Glue Studio Custom Connector Setup](img/custom_connector_setup.png)

Now create a connection for your custom connector.

- Go to Connectors and select your custom connector
- Click on "Create connection" button on top-right corner
- Give a unique name for the connection
- Finally click "Create connection" button

### Creating a Job

Now you can follow from the [user guide - creating a job](../user_guide/user_guide.md#creating-a-job) section.


## Publishing the Connector to AWS Marketplace
