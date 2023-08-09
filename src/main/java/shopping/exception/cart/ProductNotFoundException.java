package shopping.exception.cart;

import shopping.exception.general.ShoppingBaseException;

public class ProductNotFoundException extends ShoppingBaseException {

    private static final int STATUS_CODE = 404;
    private static final String MESSAGE = "해당하는 물건을 찾을 수 없습니다.";

    public ProductNotFoundException() {
        super(MESSAGE, STATUS_CODE);
    }
}
