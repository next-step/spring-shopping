package shopping.order.domain.vo;

import java.util.Objects;
import javax.persistence.Embeddable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shopping.global.exception.ShoppingException;

@Embeddable
public class ExchangeRate {

    private Double exchangeRate;
    private static final Logger logger = LoggerFactory.getLogger(ExchangeRate.class);

    protected ExchangeRate() {
    }

    public ExchangeRate(final Double exchangeRate) {
        try {
            validateRate(exchangeRate);
        } catch (ShoppingException e) {
            logger.error(e.getMessage());
        } finally {
            this.exchangeRate = exchangeRate;
        }
    }

    private void validateRate(final Double exchangeRate) {
        if (exchangeRate == null) {
            throw new ShoppingException("환율이 null 값이면 안됩니다. 입력값: +" + exchangeRate);
        }
        if (exchangeRate <= 0) {
            throw new ShoppingException("환율은 0이하이면 안됩니다. 입력값: " + exchangeRate);
        }
    }


    public Double getExchangeRate() {
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
        return Objects.equals(exchangeRate, that.exchangeRate);
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
