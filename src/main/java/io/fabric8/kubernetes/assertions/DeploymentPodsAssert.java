package io.fabric8.kubernetes.assertions;

import io.fabric8.kubernetes.api.KubernetesHelper;
import io.fabric8.kubernetes.api.model.LabelSelector;
import io.fabric8.kubernetes.api.model.LabelSelectorRequirement;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentAssert;
import io.fabric8.kubernetes.api.model.apps.DeploymentSpec;
import io.fabric8.kubernetes.client.KubernetesClient;

import java.util.List;
import java.util.Map;

/**
 * Adds assertions for asserting that a Deployment starts up correctly etc
 */
public class DeploymentPodsAssert extends DeploymentAssert implements HasPodSelectionAssert {
    private final KubernetesClient client;

    public DeploymentPodsAssert(KubernetesClient client, Deployment deployment) {
        super(deployment);
        this.client = client;
    }

    @Override
    public PodSelectionAssert pods() {
        spec().isNotNull().selector().isNotNull();
        DeploymentSpec spec = this.actual.getSpec();
        Integer replicas = spec.getReplicas();
        LabelSelector selector = spec.getSelector();
        Map<String, String> matchLabels = selector.getMatchLabels();
        List<LabelSelectorRequirement> matchExpressions = selector.getMatchExpressions();
        return new PodSelectionAssert(client, replicas, matchLabels, matchExpressions, "DeploymentConfig " + KubernetesHelper.getName(actual));
    }
}