package io.fabric8.kubernetes.assertions;

import io.fabric8.kubernetes.api.model.apps.Deployment;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DeploymentPodsAssertTests extends TestBase {
    @Test
    void testPods() {
        DeploymentPodsAssert deploymentPodsAssert = new DeploymentPodsAssert(kClient, new Deployment());
        assertThat(deploymentPodsAssert.pods()).isNotNull();
    }
}
