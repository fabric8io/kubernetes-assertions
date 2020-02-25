package io.fabric8.kubernetes.assertions.support;

import io.fabric8.kubernetes.api.PodStatusType;
import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodStatus;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

public class LogHelpersTests {
    @Test
    void testGetLogFileName() {
        String expected = "C:\\target\\test-pod-logs\\testPod-testContainer-1.log";
        File baseDir = new File("/");
        String podName = "testPod";
        Container container = new Container();
        container.setName("testContainer");
        String actual = LogHelpers.getLogFileName(baseDir, podName, container, 1).getAbsolutePath();

        assertThat(expected).isEqualTo(actual);
    }

    @Test
    void testGetRestartCount() {
        Pod pod = new Pod();
        pod.setStatus(new PodStatus());
        int restartCount = LogHelpers.getRestartCount(pod);

        assertThat(restartCount).isEqualTo(0);
    }
}
