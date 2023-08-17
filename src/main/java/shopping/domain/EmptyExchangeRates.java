package shopping.domain;

public class EmptyExchangeRates extends ExchangeRates {

    public EmptyExchangeRates() {
        super();
    }

    @Override
    public ExchangeRate getRate(ExchangeCode code) {
        return new EmptyExchangeRate();
    }
}
