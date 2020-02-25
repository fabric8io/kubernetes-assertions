package io.fabric8.kubernetes.assertions;

import org.junit.jupiter.api.Test;

import static io.fabric8.kubernetes.assertions.Assertions.assertThat;

class AssertionTests extends TestBase {

    @Test
    void testDeploymentPodIsReadyForPeriod() {
        assertThat(kClient).deployments().pods().isPodReadyForPeriod();
    }

    @Test
    void testAllServicesHaveEndpointOrReadyPod() {
        assertThat(kClient).services().assertAllServicesHaveEndpointOrReadyPod();
    }
}
