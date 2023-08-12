package shopping.domain;

public class EmptyExchangeRate extends ExchangeRate {

    public EmptyExchangeRate() {
        super();
    }

    @Override
    public double getValue() {
        return 0;
    }

    @Override
    public double exchange(long totalPrice) {
        return 0;
    }
}
