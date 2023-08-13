package shopping.exception.cart;

import shopping.exception.general.ShoppingBaseException;

public class NoOrderItemException extends ShoppingBaseException {
    private static final int STATUS_CODE = 500;

    public NoOrderItemException() {
        super("", STATUS_CODE);
    }
}
