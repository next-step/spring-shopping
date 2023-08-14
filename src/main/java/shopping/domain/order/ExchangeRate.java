package shopping.domain.order;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import shopping.exception.ShoppingErrorType;
import shopping.exception.ShoppingException;

@Embeddable
public class ExchangeRate {

    @Column(name = "exchange_rate", columnDefinition = "DOUBLE")
    private double value;

    protected ExchangeRate() {
    }

    private ExchangeRate(final double value) {
        validate(value);
        this.value = value;
    }

    private void validate(final double value) {
        if (Double.isInfinite(value)) {
            throw new ShoppingException(ShoppingErrorType.ERROR_EXCHANGE_RATE);
        }
    }

    public static ExchangeRate from(final double value) {
        return new ExchangeRate(value);
    }

    public double getValue() {
        return this.value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ExchangeRate that = (ExchangeRate) o;
        return Double.compare(value, that.value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
