package io.fabric8.kubernetes.assertions;

import io.fabric8.kubernetes.api.model.LabelSelectorRequirement;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class PodSelectionAssertTests extends TestBase {
    @Test
    void testIsPodReadyForPeriod() {
        PodSelectionAssert psAssert =
                new PodSelectionAssert(kClient, 1, new HashMap<String, String>(), new ArrayList<LabelSelectorRequirement>(), "");
        // Needs Kubernetes to be running
        psAssert.isPodReadyForPeriod(5000, 1000);
    }
}
