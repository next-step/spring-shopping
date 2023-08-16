package shopping.global.exception;

public class ShoppingAuthenticationException extends ShoppingException {

    public ShoppingAuthenticationException(final String message) {
        super(message);
    }

    public ShoppingAuthenticationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
