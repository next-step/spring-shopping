package shopping.domain.cart;

import shopping.exception.cart.NotExchangeableException;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class ExchangeRate {

    private Double rate;

    @Enumerated(EnumType.STRING)
    private CurrencyType source;

    @Enumerated(EnumType.STRING)
    private CurrencyType target;

    public ExchangeRate() {
    }

    public ExchangeRate(Double rate, CurrencyType source, CurrencyType target) {
        this.rate = rate;
        this.source = source;
        this.target = target;
    }

    public Double getRate() {
        return rate;
    }

    public CurrencyType getSource() {
        return source;
    }

    public CurrencyType getTarget() {
        return target;
    }

    public void validateExchangeable(CurrencyType type) {
        if (!type.equals(target)) {
            throw new NotExchangeableException();
        }
    }
}
