package io.fabric8.kubernetes.assertions;

import io.fabric8.kubernetes.api.model.HasMetadata;
import org.assertj.core.api.AbstractListAssert;
import org.assertj.core.api.ObjectAssert;

import java.util.ArrayList;
import java.util.List;

public class MetadatasListAssert<T extends HasMetadata> extends AbstractListAssert<MetadatasListAssert<T>, List<? extends T>, T, ObjectAssert<T>> {

    public MetadatasListAssert(List<? extends T> actual) {
        super(actual, MetadatasListAssert.class);
    }

    @Override
    protected ObjectAssert<T> toAssert(T value, String description) {
        return new ObjectAssert<>(value).describedAs(description);
    }

    @Override
    protected MetadatasListAssert<T> newAbstractIterableAssert(Iterable<? extends T> iterable) {
        List<T> target = new ArrayList<>();
        iterable.forEach(target::add);
        return new MetadatasListAssert<>(target);
    }

}
