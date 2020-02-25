package io.fabric8.kubernetes.assertions;

import io.fabric8.kubernetes.api.model.HasMetadata;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HasMetadatasAssertTests {
    HasMetadatasAssert<HasMetadata, HasMetadatasAssert> expectedMetadatasAssert = new HasMetadatasAssert<HasMetadata, HasMetadatasAssert>(new ArrayList<HasMetadata>()) {
        @Override
        protected HasMetadatasAssert createListAssert(List<HasMetadata> list) {
            return this;
        }
    };

    @Test
    void testAssertThat() {
        HasMetadatasAssert actualAssert = expectedMetadatasAssert.assertThat(expectedMetadatasAssert.get());
        assertThat(actualAssert.get()).isEqualTo(expectedMetadatasAssert.get());
    }

    @Test
    void testAssertSize() {
        expectedMetadatasAssert.assertSize().isEqualTo(0);
    }

}
