package io.fabric8.kubernetes.assertions;

import io.fabric8.kubernetes.client.KubernetesClient;

/**
 * Provides a set of assertions for a namespace
 */
public class KubernetesNamespaceAssert extends KubernetesAssert {
    private final KubernetesClient client;
    private final String namespace;

    public KubernetesNamespaceAssert(KubernetesClient client, String namespace) {
        super(client);
        this.client = client;
        this.namespace = namespace;
    }

    @Override
    public String namespace() {
        return namespace;
    }
}