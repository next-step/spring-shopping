package shopping.exception;

public final class ProductNotFoundException extends ShoppingBaseException {

    private static final int STATUS_CODE = 400;
    private static final String MESSAGE = "해당하는 상품을 찾을 수 없습니다. 현재 입력 값 : ";

    public ProductNotFoundException(String productId) {
        super(MESSAGE + productId, STATUS_CODE);
    }
}
