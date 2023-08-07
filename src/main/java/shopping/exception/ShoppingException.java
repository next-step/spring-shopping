package shopping.exception;

public abstract class ShoppingException extends RuntimeException {

    public ShoppingException(String message) {
        super(message);
    }
}
