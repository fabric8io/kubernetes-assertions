package io.fabric8.kubernetes.assertions;

import io.fabric8.openshift.api.model.DeploymentConfig;
import org.assertj.core.api.ObjectAssert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class MetadatasListAssertTests {
    @Test
    void testToAssert() {
        MetadatasListAssert metadatasListAssert = new MetadatasListAssert<>(new ArrayList<DeploymentConfig>());
        ObjectAssert<DeploymentConfig> oAssert = metadatasListAssert.toAssert(new DeploymentConfig(), "test");
        assertThat(oAssert.descriptionText()).isEqualTo("test");
    }

}
