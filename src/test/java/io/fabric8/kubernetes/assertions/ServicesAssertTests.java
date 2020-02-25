package io.fabric8.kubernetes.assertions;

import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.Service;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ServicesAssertTests extends TestBase {
    @Test
    void testAssertAllServicesHaveEndpointOrReadyPod() {
        Service service = new Service();
        ObjectMeta metadata = new ObjectMeta();
        metadata.setName("Test Service");
        service.setMetadata(metadata);
        ServicesAssert sAssert = new ServicesAssert(kClient, Arrays.asList(service));
        sAssert.assertAllServicesHaveEndpointOrReadyPod();
    }

    @Test
    void testService() {
        String serviceName = "Test Service";
        Service service = new Service();
        ObjectMeta metadata = new ObjectMeta();
        metadata.setName(serviceName);
        service.setMetadata(metadata);
        ServicesAssert sAssert = new ServicesAssert(kClient, Arrays.asList(service));
        sAssert.service(serviceName);
    }
}
