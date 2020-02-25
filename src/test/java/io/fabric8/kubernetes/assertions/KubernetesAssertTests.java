package io.fabric8.kubernetes.assertions;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.openshift.api.model.DeploymentConfig;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class KubernetesAssertTests extends TestBase {


    @Test
    void testNamespace() {
        String namespace = "default";
        KubernetesNamespaceAssert expected = new KubernetesNamespaceAssert(kClient, namespace);
        KubernetesNamespaceAssert actual = kAssert.namespace(namespace);
        assertThat(expected.toString()).isEqualTo(actual.toString());
    }

    @Test
    void testDeployments() {
        HasPodSelectionAssert deployments = kAssert.deployments();
        assertThat(deployments).isNotNull();
    }

    @Test
    void testCreatePodSelectionAssert() {
        DeploymentConfig deploymentConfig = new DeploymentConfig();
        DeploymentConfigPodsAssert expected = new DeploymentConfigPodsAssert(kClient, deploymentConfig);
        HasPodSelectionAssert actual = kAssert.createPodSelectionAssert(deploymentConfig);
        assertThat(expected.toString()).isEqualTo(actual.toString());
    }

    @Test
    void testReplicas() {
        String replicaName = "test";
        HasPodSelectionAssert actual = kAssert.replicas(replicaName);
        assertThat(actual).isNull();
    }

    @Test
    void testPodList() {
        PodsAssert expected = new PodsAssert(new ArrayList<Pod>(), kClient);
        PodsAssert actual = kAssert.podList();
        assertThat(expected.size()).isEqualTo(actual.size());
    }
}
