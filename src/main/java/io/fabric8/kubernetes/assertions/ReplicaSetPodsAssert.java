package io.fabric8.kubernetes.assertions;

import io.fabric8.kubernetes.api.KubernetesHelper;
import io.fabric8.kubernetes.api.model.LabelSelector;
import io.fabric8.kubernetes.api.model.LabelSelectorRequirement;
import io.fabric8.kubernetes.api.model.apps.ReplicaSet;
import io.fabric8.kubernetes.api.model.apps.ReplicaSetAssert;
import io.fabric8.kubernetes.api.model.apps.ReplicaSetSpec;
import io.fabric8.kubernetes.client.KubernetesClient;

import java.util.List;
import java.util.Map;

/**
 */
public class ReplicaSetPodsAssert extends ReplicaSetAssert implements HasPodSelectionAssert {
    private final KubernetesClient client;

    public ReplicaSetPodsAssert(KubernetesClient client, ReplicaSet replicasSet) {
        super(replicasSet);
        this.client = client;
    }


    @Override
    public PodSelectionAssert pods() {
        spec().isNotNull().selector().isNotNull();
        ReplicaSetSpec spec = this.actual.getSpec();
        Integer replicas = spec.getReplicas();
        LabelSelector selector = spec.getSelector();
        Map<String, String> matchLabels = selector.getMatchLabels();
        List<LabelSelectorRequirement> matchExpressions = selector.getMatchExpressions();
        return new PodSelectionAssert(client, replicas, matchLabels, matchExpressions, "ReplicaSet " + KubernetesHelper.getName(actual));
    }
}