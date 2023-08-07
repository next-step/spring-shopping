package shopping.exception;

public class PriceInvalidException extends ShoppingException {

    public PriceInvalidException() {
        super(ErrorType.PRICE_INVALID);
    }
}
