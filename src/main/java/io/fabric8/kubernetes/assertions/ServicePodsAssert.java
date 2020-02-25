package io.fabric8.kubernetes.assertions;

import io.fabric8.kubernetes.api.KubernetesHelper;
import io.fabric8.kubernetes.api.model.Endpoints;
import io.fabric8.kubernetes.api.model.EndpointsList;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceAssert;
import io.fabric8.kubernetes.api.model.ServiceSpec;
import io.fabric8.kubernetes.api.model.LabelSelector;
import io.fabric8.kubernetes.api.model.LabelSelectorRequirement;
import io.fabric8.kubernetes.client.KubernetesClient;

import java.util.List;
import java.util.Map;

import static io.fabric8.kubernetes.assertions.AbstractPodSelectionAssert.getDefaultNotReadyTimeoutMs;
import static io.fabric8.kubernetes.assertions.AbstractPodSelectionAssert.getDefaultReadyPeriodMs;
import static io.fabric8.kubernetes.api.KubernetesHelper.getLabels;

/**
 * Adds assertions for asserting that a Service has ready pods etc
 */
public class ServicePodsAssert extends ServiceAssert implements HasPodSelectionAssert {
    private final KubernetesClient client;

    public ServicePodsAssert(KubernetesClient client, Service actual) {
        super(actual);
        this.client = client;
    }

    @Override
    public PodSelectionAssert pods() {
        spec().isNotNull().selector().isNotNull();
        ServiceSpec spec = this.actual.getSpec();
        int replicas = 1;
        LabelSelector selector = null;
        Map<String, String> matchLabels = spec.getSelector();
        List<LabelSelectorRequirement> matchExpressions = selector.getMatchExpressions();
        return new PodSelectionAssert(client, replicas, matchLabels, matchExpressions, "Service " + KubernetesHelper.getName(actual));
    }


    /**
     * Asserts that either this service has a valid Endpoint or that a pod is Ready for a period of time
     */
    public ServicePodsAssert hasEndpointOrReadyPod() {
        return hasEndpointOrReadyPod(getDefaultNotReadyTimeoutMs(), getDefaultReadyPeriodMs());
    }

    /**
     * Asserts that either this service has a valid Endpoint or that a pod is Ready for a period of time
     */
    public ServicePodsAssert hasEndpointOrReadyPod(long notReadyTimeoutMS, long readyPeriodMS) {
        EndpointsList list = client.endpoints().withLabels(getLabels(actual)).list();
        if (list != null) {
            List<Endpoints> items = list.getItems();
            if (items.size() > 0) {
                return this;
            }
        }
        pods().isPodReadyForPeriod(notReadyTimeoutMS, readyPeriodMS);
        return this;
    }
}