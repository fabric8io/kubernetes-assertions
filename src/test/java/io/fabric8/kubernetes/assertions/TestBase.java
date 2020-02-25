package io.fabric8.kubernetes.assertions;

import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;

public class TestBase {
    final KubernetesClient kClient = new DefaultKubernetesClient();
    final KubernetesAssert kAssert = new KubernetesAssert(kClient);
}
