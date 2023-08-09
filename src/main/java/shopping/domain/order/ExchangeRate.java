package shopping.domain.order;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ExchangeRate {

    @Column(name = "exchange_rate", nullable = false)
    private double value;

    protected ExchangeRate() {
    }

    private ExchangeRate(final double value) {
        this.value = value;
    }

    public static ExchangeRate from(final double value) {
        return new ExchangeRate(value);
    }

    public double getValue() {
        return value;
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
