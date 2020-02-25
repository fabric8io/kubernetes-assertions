package io.fabric8.kubernetes.assertions;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodList;
import io.fabric8.kubernetes.api.model.LabelSelectorRequirement;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.Watch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.fabric8.kubernetes.assertions.support.PodWatcher;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Assertion helper for performing assertions on a selection of pods
 */
public class PodSelectionAssert extends AbstractPodSelectionAssert {

    private static final transient Logger LOG = LoggerFactory.getLogger(PodSelectionAssert.class);

    private final KubernetesClient client;
    private final Integer replicas;
    private final Map<String, String> matchLabels;
    private final List<LabelSelectorRequirement> matchExpressions;
    private final String description;


    public PodSelectionAssert(KubernetesClient client, Integer replicas, Map<String, String> matchLabels, List<LabelSelectorRequirement> matchExpressions, String description) {
        this.client = client;
        this.replicas = replicas;
        this.matchLabels = matchLabels;
        this.matchExpressions = matchExpressions;
        this.description = description;
    }

    public KubernetesClient getClient() {
        return client;
    }

    public String getDescription() {
        return description;
    }

    public Integer getReplicas() {
        return replicas;
    }

    public Map<String, String> getMatchLabels() {
        return matchLabels;
    }

    public List<LabelSelectorRequirement> getMatchExpressions() {
        return matchExpressions;
    }

    /**
     * Asserts that a pod is ready for this deployment all become ready within the given time and that each one keeps being ready for the given time
     */
    public PodSelectionAssert isPodReadyForPeriod(long notReadyTimeoutMS, long readyPeriodMS) {
        if (replicas.intValue() <= 0) {
            LOG.warn("Not that the pod selection for: " + description + " has no replicas defined so we cannot assert there is a pod ready");
            return this;
        }

        try (PodWatcher podWatcher = new PodWatcher(this, notReadyTimeoutMS, readyPeriodMS);
             Watch watch = client.pods().withLabels(matchLabels).watch(podWatcher);
        ) {
            podWatcher.loadCurrentPods();
            podWatcher.waitForPodReady();

        }
        return this;
    }

    /**
     * Loads the current pods for this selection
     *
     * @return the current pods
     */
    public List<Pod> getPods() {
        PodList list = getClient().pods().withLabels(getMatchLabels()).list();
        assertThat(list).describedAs(getDescription() + " pods").isNotNull();
        return list.getItems();
    }
}