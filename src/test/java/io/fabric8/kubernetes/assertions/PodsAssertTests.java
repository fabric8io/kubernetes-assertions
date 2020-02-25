package io.fabric8.kubernetes.assertions;

import io.fabric8.kubernetes.api.PodStatusType;
import io.fabric8.kubernetes.api.model.Pod;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PodsAssertTests extends TestBase {
    @Test
    void testLogs() {
        List<Pod> pods = new ArrayList<>();
        Pod pod1 = new Pod();
        Pod pod2 = new Pod();
        pods.add(pod1);
        pods.add(pod2);

        PodsAssert pAssert = new PodsAssert(pods, kClient);
        pAssert.logs().isEmpty();
    }
}
