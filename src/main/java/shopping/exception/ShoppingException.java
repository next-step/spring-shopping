package shopping.exception;

public class ShoppingException extends RuntimeException {
    public ShoppingException(String message) {
        super(message);
    }

    public ShoppingException(String message, Throwable e) {
        super(message, e);
    }
}
