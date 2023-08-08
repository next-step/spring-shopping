package shopping.auth.exception;

import shopping.global.exception.ShoppingException;

public class ShoppingAuthenticationException extends ShoppingException {

    public ShoppingAuthenticationException(final String message) {
        super(message);
    }

    public ShoppingAuthenticationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
