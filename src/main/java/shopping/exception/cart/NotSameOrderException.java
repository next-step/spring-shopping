package shopping.exception.cart;

import shopping.exception.general.ShoppingBaseException;

public class NotSameOrderException extends ShoppingBaseException {
    private static final int STATUS_CODE = 500;

    public NotSameOrderException() {
        super("", STATUS_CODE);
    }
}
