package io.fabric8.kubernetes.assertions;

import io.fabric8.kubernetes.api.model.apps.ReplicaSet;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ReplicaSetPodsAssertTests extends TestBase {
    @Test
    void testPods() {
        ReplicaSetPodsAssert rspAssert = new ReplicaSetPodsAssert(kClient, new ReplicaSet());
        // Needs Kubernetes to be running
        assertThat(rspAssert.pods().getReplicas()).isEqualTo(0);
    }
}
