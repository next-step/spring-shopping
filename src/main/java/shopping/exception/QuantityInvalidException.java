package shopping.exception;

public class QuantityInvalidException extends ShoppingException {

    public QuantityInvalidException() {
        super(ErrorType.QUANTITY_INVALID);
    }
}
