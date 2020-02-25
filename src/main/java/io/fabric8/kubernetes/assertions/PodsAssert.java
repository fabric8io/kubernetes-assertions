package io.fabric8.kubernetes.assertions;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.utils.Strings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Adds some extra assertion operations
 */
public class PodsAssert extends HasMetadatasAssert<Pod, PodsAssert> {
    private final KubernetesClient client;

    public PodsAssert(List<Pod> actual, KubernetesClient client) {
        super(actual);
        this.client = client;
    }

    /**
     * Returns an assertion on the logs of the pods
     */
    public PodLogsAssert logs() {
        return logs(null);
    }

    /**
     * Returns an assertion on the logs of the pods for the given container name
     */
    public PodLogsAssert logs(String containerName) {
        Map<String,String> logs = new HashMap<>();
        List<Pod> pods = get();
        for (Pod pod : pods) {
            ObjectMeta metadata = pod.getMetadata();
            if (metadata != null) {
                String name = metadata.getName();
                String namespace = metadata.getNamespace();
                String key = name;
                if (Strings.isNotBlank(namespace)) {
                    key = namespace + "/" + name;
                }
                // TODO deal with more than one container!!!
                //String log = client.pods().inNamespace(namespace).withName(name).getLog(containerName, true);
                String log = client.pods().inNamespace(namespace).withName(name).getLog(true);
                if (log != null) {
                    logs.put(key, log);
                }
            }
        }
        return new PodLogsAssert(logs, containerName);
    }

    /**
     * Returns the filtered list of pods which have running status
     */
    public PodsAssert runningStatus() {
        return filter(Conditions.runningStatus());
    }

    /**
     * Returns the filtered list of pods which have waiting status
     */
    public PodsAssert waitingStatus() {
        return filter(Conditions.waitingStatus());
    }

    /**
     * Returns the filtered list of pods which have error status
     */
    public PodsAssert errorStatus() {
        return filter(Conditions.errorStatus());
    }

    @Override
    protected PodsAssert createListAssert(List<Pod> list) {
        return new PodsAssert(list, client);
    }
}