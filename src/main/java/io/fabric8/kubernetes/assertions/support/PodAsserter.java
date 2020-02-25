package io.fabric8.kubernetes.assertions.support;

import io.fabric8.kubernetes.api.KubernetesHelper;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.utils.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.util.Timer;
import java.util.TimerTask;

import static org.fusesource.jansi.Ansi.Color.YELLOW;
import static org.fusesource.jansi.Ansi.ansi;

/**
 */
public class PodAsserter implements Closeable {
    private static final transient Logger LOG = LoggerFactory.getLogger(PodAsserter.class);

    private final PodWatcher watcher;
    private final String name;
    private final Pod pod;
    private Timer timer;

    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            watcher.podIsReadyForEntireDuration(name, pod);
        }
    };

    public PodAsserter(PodWatcher watcher, String name, Pod pod) {
        this.watcher = watcher;
        this.name = name;
        this.pod = pod;
        updated(pod);
    }

    public void close() {
        cancelTimer();
    }

    public void updated(Pod pod) {
        String statusText = KubernetesHelper.getPodStatusText(pod);
        boolean ready = KubernetesHelper.isPodReady(pod);

        String message = "Pod " + name + " has status: " + statusText + " isReady: " + ready;
        LOG.info(ansi().fg(YELLOW).a(message).reset().toString());

        if (ready) {
            watcher.podIsReady(name, pod);

            if (timer == null) {
                timer = new Timer(watcher.getDescription());
                timer.schedule(task, watcher.getReadyPeriodMS());
            }
        } else {
            cancelTimer();
        }
    }

    protected void cancelTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
