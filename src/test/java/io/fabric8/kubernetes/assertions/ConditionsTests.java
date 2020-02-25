package io.fabric8.kubernetes.assertions;

import io.fabric8.kubernetes.api.KubernetesHelper;
import io.fabric8.kubernetes.api.PodStatusType;
import io.fabric8.kubernetes.api.model.Pod;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static io.fabric8.kubernetes.assertions.Conditions.matchesLabel;
import static org.assertj.core.api.Assertions.assertThat;

class ConditionsTests {
    @Test
    void testHasLabel() {
        final String key = "someKey", value = "someValue";
        Condition<Pod> expectedCondition = new Condition<Pod>() {
            @Override
            public String toString() {
                return "hasLabel(" + key + " = " + value + ")";
            }

            @Override
            public boolean matches(Pod resource) {
                return matchesLabel(resource.getMetadata().getLabels(), key, value);
            }
        };

        Condition<Pod> actualCondition = Conditions.hasLabel(key, value);
        assertThat(expectedCondition.toString()).isEqualTo(actualCondition.toString());
    }

    @Test
    void testHasName() {
        final String name = "podName";
        Condition<Pod> expectedCondition = new Condition<Pod>() {
            @Override
            public String toString() {
                return "hasName(" + name + ")";
            }

            @Override
            public boolean matches(Pod resource) {
                return Objects.equals(name, resource.getMetadata().getName());
            }
        };

        Condition<Pod> actualCondition = Conditions.hasName(name);
        assertThat(expectedCondition.toString()).isEqualTo(actualCondition.toString());
    }

    @Test
    void testHasNamespace() {
        final String namespace = "default";
        Condition<Pod> expectedCondition = new Condition<Pod>() {
            @Override
            public String toString() {
                return "hasNamespace(" + namespace + ")";
            }

            @Override
            public boolean matches(Pod resource) {
                return Objects.equals(namespace, resource.getMetadata().getNamespace());
            }
        };

        Condition<Pod> actualCondition = Conditions.hasNamespace(namespace);
        assertThat(expectedCondition.toString()).isEqualTo(actualCondition.toString());
    }

    @Test
    void testStatus() {
        PodStatusType status = PodStatusType.OK;
        Condition<Pod> actualCondition = Conditions.status(status);
        assertPodStatus(status, actualCondition);
    }

    @Test
    void testRunningStatus() {
        Condition<Pod> actualCondition = Conditions.runningStatus();
        assertPodStatus(PodStatusType.OK, actualCondition);
    }

    @Test
    void testWaitingStatus() {
        Condition<Pod> actualCondition = Conditions.waitingStatus();
        assertPodStatus(PodStatusType.WAIT, actualCondition);
    }

    @Test
    void testErrorStatus() {
        Condition<Pod> actualCondition = Conditions.errorStatus();
        assertPodStatus(PodStatusType.ERROR, actualCondition);
    }

    private void assertPodStatus(final PodStatusType status, Condition<Pod> actualCondition) {
        Condition<Pod> expectedCondition = new Condition<Pod>() {
            @Override
            public String toString() {
                return "podStatus(" + status + ")";
            }

            @Override
            public boolean matches(Pod pod) {
                return Objects.equals(status, KubernetesHelper.getPodStatus(pod));
            }
        };
        assertThat(expectedCondition.toString()).isEqualTo(actualCondition.toString());
    }


}
