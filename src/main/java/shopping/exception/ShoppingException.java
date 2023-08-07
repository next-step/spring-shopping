package shopping.exception;

public class ShoppingException extends RuntimeException {

    public ShoppingException(final String message) {
        super(message);
    }

    public ShoppingException(final ErrorType errorType) {
        super(errorType.getMessage());
    }
}
