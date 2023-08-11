package shopping.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ExchangeRate {

    @Column(name = "exchange_rate")
    private double value;

    public ExchangeRate(double value) {
        this.value = value;
    }

    protected ExchangeRate() {
    }

    public double getValue() {
        return value;
    }

    public double exchange(long totalPrice) {
        final double result = totalPrice / value;
        return Math.round(result * 100) / 100.0;
    }
}