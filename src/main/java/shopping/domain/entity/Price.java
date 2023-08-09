package shopping.domain.entity;

import shopping.exception.PriceInvalidException;

import javax.persistence.Embeddable;
import java.util.Collection;
import java.util.function.Function;

@Embeddable
public class Price {

    private static final Price ZERO = new Price();

    private final int price;

    public static <T> Price sum(final Collection<T> items, final Function<T, Price> function) {
        return items.stream()
                .map(function)
                .reduce(Price.ZERO, Price::plus);
    }

    protected Price() {
        this.price = 0;
    }

    public Price(final int value) {
        validateIsPositive(value);

        this.price = value;
    }

    public Price plus(final Price price) {
        return new Price(this.price + price.price);
    }

    public Price multiply(final int value) {
        return new Price(price * value);
    }

    private void validateIsPositive(final int value) {
        if (value <= 0) {
            throw new PriceInvalidException();
        }
    }

    public int getPrice() {
        return price;
    }
}
