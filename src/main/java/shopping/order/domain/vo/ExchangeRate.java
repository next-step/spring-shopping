package shopping.order.domain.vo;

import java.util.Objects;
import javax.persistence.Embeddable;
import shopping.global.exception.ShoppingException;

@Embeddable
public class ExchangeRate {

    private double rate;

    protected ExchangeRate() {
    }

    public ExchangeRate(double rate) {
        validateRate(rate);
        this.rate = rate;
    }

    private void validateRate(double rate) {
        if (rate <= 0) {
            throw new ShoppingException("환율은 0이하이면 안됩니다. 입력값: " + rate);
        }
    }

    public double getRate() {
        return rate;
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
        return Double.compare(that.rate, rate) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rate);
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
            "rate=" + rate +
            '}';
    }
}
