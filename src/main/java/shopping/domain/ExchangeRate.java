package shopping.domain;

import shopping.domain.entity.Price;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ExchangeRate {

    private double value;

    protected ExchangeRate() {
    }

    public ExchangeRate(final double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public double apply(final Price price) {
        return price.getValue() / value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ExchangeRate that = (ExchangeRate) o;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
