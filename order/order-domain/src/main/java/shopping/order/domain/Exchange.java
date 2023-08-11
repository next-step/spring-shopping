package shopping.order.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import shopping.order.domain.exception.IllegalExchangeRateException;

public class Exchange {

    private static final double MINIMUM_RATE = 0D;
    private static final int SCALE = 5;

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
        return new BigDecimal(price).divide(BigDecimal.valueOf(rate), SCALE, RoundingMode.HALF_UP);
    }

    public double getRate() {
        return rate;
    }

}
