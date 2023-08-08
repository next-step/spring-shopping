package shopping.order.app.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import shopping.order.app.exception.IllegalExchangeRateException;

public class Exchange {

    private static final double MINIMUM_RATE = 0D;

    private final double rate;

    public Exchange(double rate) {
        validRate(rate);
        this.rate = rate;
    }

    private void validRate(double rate) {
        if (rate <= MINIMUM_RATE) {
            throw new IllegalExchangeRateException(rate);
        }
    }

    BigDecimal calculate(BigInteger price) {
        return new BigDecimal(price).multiply(BigDecimal.valueOf(rate));
    }
}
