package shopping.domain.order;

import shopping.exception.OrderExceptionType;
import shopping.exception.ShoppingException;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class OrderCurrencyRate {

    private Double currencyRate;

    protected OrderCurrencyRate() {
    }

    public OrderCurrencyRate(final Double currencyRate) {
        validateIsNull(currencyRate);
        validateLessThanEqualZero(currencyRate);

        this.currencyRate = Math.round(currencyRate * 100) / 100.0;
    }

    private void validateIsNull(final Double currencyRate) {
        if (Objects.isNull(currencyRate)) {
            throw new ShoppingException(OrderExceptionType.NOT_FOUND_CURRENCY_RATE);
        }
    }

    private void validateLessThanEqualZero(final Double rate) {
        if (rate <= 0) {
            throw new ShoppingException(OrderExceptionType.INVALID_CURRENCY_EXCHANGE_SIZE, rate);
        }
    }

    public Double getCurrencyRate() {
        return this.currencyRate;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OrderCurrencyRate that = (OrderCurrencyRate) o;
        return Objects.equals(currencyRate, that.currencyRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currencyRate);
    }
}
