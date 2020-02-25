package io.fabric8.kubernetes.assertions;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.utils.Strings;
import io.fabric8.utils.Systems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public abstract class AbstractPodSelectionAssert {
    private static final transient Logger LOG = LoggerFactory.getLogger(AbstractPodSelectionAssert.class);

    public static final String PROPERTY_ASSERT_NOT_READY_TIMEOUT_MILLIS = "fabric8.assert.notReadyTimeoutMills";
    public static final String PROPERTY_ASSERT_READY_PERIOD_MILLIS = "fabric8.assert.readyPeriodMills";

    public static final long DEFAULT_NOT_READY_TIMEOUT_MS = 5 * 60 * 1000;
    public static final long DEFAULT_READY_PERIOD_MS = 10 * 1000;

    public static long getDefaultReadyPeriodMs() {
        return parseLongValue(Systems.getEnvVarOrSystemProperty(PROPERTY_ASSERT_READY_PERIOD_MILLIS), DEFAULT_READY_PERIOD_MS);
    }

    public static long getDefaultNotReadyTimeoutMs() {
        return parseLongValue(Systems.getEnvVarOrSystemProperty(PROPERTY_ASSERT_NOT_READY_TIMEOUT_MILLIS), DEFAULT_NOT_READY_TIMEOUT_MS);
    }

    private static long parseLongValue(String text, long defaultValue) {
        if (Strings.isNotBlank(text)) {
            try {
                return Long.parseLong(text);
            } catch (NumberFormatException e) {
                LOG.warn("Could not parse long value " + text + ": " + e, e);
            }
        }
        return defaultValue;
    }

    /**
     * Asserts that a pod is ready for this deployment all become ready within the given time and that each one keeps being ready for the given time
     */
    public AbstractPodSelectionAssert isPodReadyForPeriod() {
        return isPodReadyForPeriod(getDefaultNotReadyTimeoutMs(), getDefaultReadyPeriodMs());
    }

    abstract public AbstractPodSelectionAssert isPodReadyForPeriod(long notReadyTimeoutMS, long readyPeriodMS);

    abstract public List<Pod> getPods();
}