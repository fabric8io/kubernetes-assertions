package io.fabric8.kubernetes.assertions;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.Service;
import org.junit.jupiter.api.Test;

public class ServicePodsAssertTests extends TestBase {
    @Test
    void testHasEndpointOrReadyPod() {
        Service service = new Service();
        ObjectMeta metadata = new ObjectMeta();
        metadata.setName("Test Service");
        service.setMetadata(metadata);
        ServicePodsAssert spAssert = new ServicePodsAssert(kClient, service);
        // Needs Kubernetes to be running
        spAssert.hasEndpointOrReadyPod();
    }
}
