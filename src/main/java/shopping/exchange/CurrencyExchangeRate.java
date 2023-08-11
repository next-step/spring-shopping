package shopping.exchange;

import java.util.Objects;
import shopping.exception.ExchangeExceptionType;
import shopping.exception.ShoppingException;

public class CurrencyExchangeRate {

    private final double rate;

    public CurrencyExchangeRate(final double rate) {
        validateLessThanEqualZero(rate);

        this.rate = Math.round(rate * 100) / 100.0;
    }

    private void validateLessThanEqualZero(final double rate) {
        if (rate <= 0) {
            throw new ShoppingException(
                ExchangeExceptionType.INVALID_CURRENCY_EXCHANGE_VALUE, rate
            );
        }
    }

    public double getRate() {
        return this.rate;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CurrencyExchangeRate that = (CurrencyExchangeRate) o;
        return Double.compare(that.rate, rate) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rate);
    }
}
