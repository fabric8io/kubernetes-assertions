package io.fabric8.kubernetes.assertions;

import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.assertj.core.api.ListAssert;

import java.util.List;
import java.util.Objects;

import static io.fabric8.kubernetes.api.KubernetesHelper.getName;
import static org.assertj.core.api.Assertions.assertThat;

/**
 */
public class ServicesAssert extends ListAssert<Service> {
    private final KubernetesClient client;
    private final List<? extends Service> actual;

    public ServicesAssert(KubernetesClient client, List<? extends Service> actual) {
        super(actual);
        this.client = client;
        this.actual = actual;
    }

    public ServicesAssert assertAllServicesHaveEndpointOrReadyPod() {
        for (Service service : actual) {
            ServicePodsAssert asserter = new ServicePodsAssert(client, service);
            asserter.hasEndpointOrReadyPod();
        }
        return this;
    }

    /**
     * Asserts that the given service name exist
     *
     * @return the assertion object on the given service
     */
    public ServicePodsAssert service(String serviceName) {
        Service service = null;
        for (Service aService : actual) {
            String name = getName(aService);
            if (Objects.equals(name, serviceName)) {
                service = aService;
            }
        }
        assertThat(service).describedAs("No service could be found for name: " + serviceName).isNotNull();
        return new ServicePodsAssert(client, service);
    }


}
