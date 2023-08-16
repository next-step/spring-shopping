package shopping.global.exception;

public class ShoppingException extends RuntimeException {

    public ShoppingException(final String message) {
        super(message);
    }

    public ShoppingException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
