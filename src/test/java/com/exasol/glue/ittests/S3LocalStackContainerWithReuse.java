package com.exasol.glue.ittests;

import java.util.logging.Logger;

import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.TestcontainersConfiguration;

public class S3LocalStackContainerWithReuse extends LocalStackContainer {
    private static final Logger LOGGER = Logger.getLogger(S3LocalStackContainerWithReuse.class.getName());

    public S3LocalStackContainerWithReuse(final DockerImageName dockerImageName) {
        super(dockerImageName);
        withServices(Service.S3);
        withReuse(true);
    }

    @Override
    public void stop() {
        if (this.isShouldBeReused() && TestcontainersConfiguration.getInstance().environmentSupportsReuse()) {
            LOGGER.warning("Leaving container running since reuse is enabled. Don't forget to stop and remove "
                    + "the container manually using docker rm -f CONTAINER_ID.");
        } else {
            super.stop();
        }
    }
}
