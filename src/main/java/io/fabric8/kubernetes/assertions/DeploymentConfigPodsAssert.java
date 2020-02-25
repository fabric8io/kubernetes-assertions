package io.fabric8.kubernetes.assertions;

import io.fabric8.kubernetes.api.KubernetesHelper;
import io.fabric8.kubernetes.api.model.LabelSelectorRequirement;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.openshift.api.model.DeploymentConfig;
import io.fabric8.openshift.api.model.DeploymentConfigAssert;
import io.fabric8.openshift.api.model.DeploymentConfigSpec;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Adds assertions for asserting that a Deployment starts up correctly etc
 */
public class DeploymentConfigPodsAssert extends DeploymentConfigAssert implements HasPodSelectionAssert {
    private final KubernetesClient client;

    public DeploymentConfigPodsAssert(KubernetesClient client, DeploymentConfig deployment) {
        super(deployment);
        this.client = client;
    }

    public PodSelectionAssert pods() {
        spec().isNotNull().selector().isNotNull();
        DeploymentConfigSpec spec = this.actual.getSpec();
        Integer replicas = spec.getReplicas();
        Map<String, String> matchLabels = spec.getSelector();
        List<LabelSelectorRequirement> matchExpressions = new ArrayList<>();
        return new PodSelectionAssert(client, replicas, matchLabels, matchExpressions, "DeploymentConfig " + KubernetesHelper.getName(actual));
    }
}