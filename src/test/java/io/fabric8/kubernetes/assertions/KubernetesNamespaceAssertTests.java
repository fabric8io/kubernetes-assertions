package io.fabric8.kubernetes.assertions;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class KubernetesNamespaceAssertTests extends TestBase {
    @Test
    void testNamespace() {
        String namespace = "default";
        KubernetesNamespaceAssert kNamespaceAssert = new KubernetesNamespaceAssert(kClient, namespace);
        assertThat(namespace).isEqualTo(kNamespaceAssert.namespace());
    }
}
