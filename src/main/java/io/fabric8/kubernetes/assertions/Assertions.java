package io.fabric8.kubernetes.assertions;

import io.fabric8.kubernetes.client.KubernetesClient;
import org.assertj.core.api.AbstractAssert;

public class Assertions extends io.fabric8.kubernetes.assertions.internal.Assertions {

    public static KubernetesAssert assertThat(KubernetesClient kubernetesClient) {
        return new KubernetesAssert(kubernetesClient);
    }

    public static KubernetesNamespaceAssert assertThat(KubernetesClient kubernetesClient, String namespace) {
        return assertThat(kubernetesClient).namespace(namespace);
    }

    // TODO remove and replace with Descriptions.navigateDescription() when this issue is resolved and released:
    // https://github.com/joel-costigliola/assertj-core/issues/641
    public static String joinDescription(AbstractAssert asserter, String propertyName) {
        String text = asserter.descriptionText();
        if (text == null || text.length() == 0) {
            text = asserter.getClass().getSimpleName();
            String postfix = "Assert";
            if (text.endsWith(postfix)) {
                text = text.substring(0, text.length() - postfix.length());
            }
        }
        return text + "." + propertyName;
    }
}