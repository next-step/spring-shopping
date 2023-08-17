package shopping.domain.vo;

import shopping.exception.PriceInvalidException;

import javax.persistence.Embeddable;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

@Embeddable
public class Price {

    private static final Price ZERO = new Price();

    private final long value;

    public static <T> Price sum(final Collection<T> items, final Function<T, Price> function) {
        return items.stream()
                .map(function)
                .reduce(Price.ZERO, Price::plus);
    }

    protected Price() {
        this.value = 0;
    }

    public Price(final long value) {
        validateIsPositive(value);

        this.value = value;
    }

    public Price plus(final Price price) {
        return new Price(this.value + price.value);
    }

    public Price multiply(final long value) {
        return new Price(this.value * value);
    }

    private void validateIsPositive(final long value) {
        if (value <= 0) {
            throw new PriceInvalidException();
        }
    }

    public long getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Price price = (Price) o;
        return value == price.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
