package io.fabric8.kubernetes.assertions.support;

import io.fabric8.kubernetes.api.model.Container;
import io.fabric8.kubernetes.api.model.ContainerStatus;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.PodStatus;

import java.io.File;
import java.util.List;

/**
 */
public class LogHelpers {
    public static final String LOG_FILE_POSTFIX = ".log";

    public static File getLogFileName(File baseDir, String podName, Container container, int restartCount) {
        File logDir = new File(baseDir, "target/test-pod-logs/");
        String containerName = container.getName();
        String restartCountText = "";
        if (restartCount > 0) {
            restartCountText = "-" + restartCount;
        }
        String logFileName = podName + "-" + containerName + restartCountText + LOG_FILE_POSTFIX;
        File logFile = new File(logDir, logFileName);
        logFile.getParentFile().mkdirs();
        return logFile;
    }

    public static int getRestartCount(Pod pod) {
        int restartCount = 0;
        PodStatus podStatus = pod.getStatus();
        if (podStatus != null) {
            List<ContainerStatus> containerStatuses = podStatus.getContainerStatuses();
            for (ContainerStatus containerStatus : containerStatuses) {
                if (restartCount == 0) {
                    Integer restartCountValue = containerStatus.getRestartCount();
                    if (restartCountValue != null) {
                        restartCount = restartCountValue.intValue();
                    }
                }
            }
        }
        return restartCount;
    }
}
