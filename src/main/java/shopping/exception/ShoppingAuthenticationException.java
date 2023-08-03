package shopping.exception;

public class ShoppingAuthenticationException extends RuntimeException {

    public ShoppingAuthenticationException(final String message) {
        super(message);
    }

    public ShoppingAuthenticationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
