package shopping.order.domain.vo;

import java.util.Objects;
import javax.persistence.Embeddable;
import shopping.global.exception.ShoppingException;

@Embeddable
public class ExchangeRate {

    private double exchangeRate;

    protected ExchangeRate() {
    }

    public ExchangeRate(final double exchangeRate) {
        validateRate(exchangeRate);
        this.exchangeRate = exchangeRate;
    }

    private void validateRate(final double exchangeRate) {
        if (exchangeRate <= 0) {
            throw new ShoppingException("환율은 0이하이면 안됩니다. 입력값: " + exchangeRate);
        }
    }


    public double getExchangeRate() {
        return exchangeRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExchangeRate that = (ExchangeRate) o;
        return Double.compare(that.exchangeRate, exchangeRate) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(exchangeRate);
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
            "exchangeRate=" + exchangeRate +
            '}';
    }
}
