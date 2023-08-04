package shopping.exception;

public final class ProductAlreadyInCartException extends ShoppingBaseException {

    private static final int STATUS_CODE = 409;
    private static final String MESSAGE = "이미 카트에 담겨있는 상품입니다.";

    public ProductAlreadyInCartException() {
        super(MESSAGE, STATUS_CODE);
    }
}
