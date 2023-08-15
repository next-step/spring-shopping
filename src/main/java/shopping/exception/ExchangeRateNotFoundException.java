package shopping.exception;

public class ExchangeRateNotFoundException extends ShoppingException {

    public ExchangeRateNotFoundException() {
        super(ErrorType.EXCHANGE_RATE_NULL);
    }
}
