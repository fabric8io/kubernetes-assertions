package io.fabric8.kubernetes.assertions;

import io.fabric8.openshift.api.model.DeploymentConfig;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DeploymentConfigPodsAssertTests extends TestBase {
    @Test
    void testPods() {
        DeploymentConfigPodsAssert deploymentConfigPodsAssert = new DeploymentConfigPodsAssert(kClient, new DeploymentConfig());
        assertThat(deploymentConfigPodsAssert.pods()).isNotNull();
    }
}
