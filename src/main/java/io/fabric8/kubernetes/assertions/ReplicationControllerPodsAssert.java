package io.fabric8.kubernetes.assertions;

import io.fabric8.kubernetes.api.KubernetesHelper;
import io.fabric8.kubernetes.api.model.ReplicationController;
import io.fabric8.kubernetes.api.model.ReplicationControllerAssert;
import io.fabric8.kubernetes.api.model.ReplicationControllerSpec;
import io.fabric8.kubernetes.api.model.LabelSelectorRequirement;
import io.fabric8.kubernetes.client.KubernetesClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 */
public class ReplicationControllerPodsAssert extends ReplicationControllerAssert implements HasPodSelectionAssert {
    private final KubernetesClient client;

    public ReplicationControllerPodsAssert(KubernetesClient client, ReplicationController replicationController) {
        super(replicationController);
        this.client = client;
    }

    public PodSelectionAssert pods() {
        spec().isNotNull().selector().isNotNull();
        ReplicationControllerSpec spec = this.actual.getSpec();
        Integer replicas = spec.getReplicas();
        Map<String, String> matchLabels = spec.getSelector();
        List<LabelSelectorRequirement> matchExpressions = new ArrayList<>();
        return new PodSelectionAssert(client, replicas, matchLabels, matchExpressions, "ReplicationController " + KubernetesHelper.getName(actual));
    }
}
