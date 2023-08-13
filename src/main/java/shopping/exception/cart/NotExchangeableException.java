package shopping.exception.cart;

import shopping.exception.general.ShoppingBaseException;

public class NotExchangeableException extends ShoppingBaseException {

    private static final String MESSAGE = "환전할 수 없습니다.";
    private static final int STATUS_CODE = 500;

    public NotExchangeableException() {
        super(MESSAGE, STATUS_CODE);
    }
}
