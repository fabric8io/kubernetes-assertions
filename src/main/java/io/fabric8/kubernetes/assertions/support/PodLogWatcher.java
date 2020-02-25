package io.fabric8.kubernetes.assertions.support;

import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodSpec;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.LogWatch;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 */
public class PodLogWatcher implements Closeable {
    private final LogWatch logWatch;

    public PodLogWatcher(PodWatcher podWatcher, String name, Pod pod, String containerName, File logFile) throws FileNotFoundException {
        KubernetesClient client = podWatcher.getClient();
        ObjectMeta metadata = pod.getMetadata();
        logFile.getParentFile().mkdirs();
        PodSpec spec = pod.getSpec();
        this.logWatch = client.pods().inNamespace(metadata.getNamespace()).withName(name).inContainer(containerName).watchLog(new FileOutputStream(logFile));
    }

    @Override
    public void close() throws IOException {
        if (logWatch != null) {
            logWatch.close();
        }
    }
}
