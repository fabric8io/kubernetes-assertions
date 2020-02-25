package io.fabric8.kubernetes.assertions;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.AssertFactory;
import org.assertj.core.api.FactoryBasedNavigableListAssert;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Provides helper methods for navigating a list property in a generated assertion class
 *
 */
public class NavigationListAssert<T, EA extends AbstractAssert<EA, T>> extends FactoryBasedNavigableListAssert<NavigationListAssert<T, EA>, List<? extends T>, T, EA> {
    private final AssertFactory<T, EA> assertFactory;

    public NavigationListAssert(List<? extends T> actual, AssertFactory<T, EA> assertFactory) {
        super(actual, NavigationListAssert.class, assertFactory);
        this.assertFactory = assertFactory;
    }

    /**
     * Navigates to the first element in the list if the list is not empty
     *
     * @return the assertion on the first element
     */
    public EA first() {
        isNotEmpty();
        return toAssert(actual.get(0), Assertions.joinDescription(this, "first()"));
    }

    /**
     * Navigates to the last element in the list if the list is not empty
     *
     * @return the assertion on the last element
     */
    public EA last() {
        isNotEmpty();
        return toAssert(actual.get(actual.size() - 1), Assertions.joinDescription(this, "last()"));
    }

    /**
     * Navigates to the element at the given index if the index is within the range of the list
     *
     * @return the assertion on the given element
     */
    public EA item(int index) {
        isNotEmpty();
        assertThat(index).describedAs(Assertions.joinDescription(this, "index")).isGreaterThanOrEqualTo(0).isLessThan(actual.size());
        return toAssert(actual.get(index), Assertions.joinDescription(this, "index(" + index + ")"));
    }

    public EA toAssert(T value, String description) {
        return assertFactory.createAssert(value).describedAs(description);
    }
}