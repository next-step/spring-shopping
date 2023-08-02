package shopping.exception;

public class CartItemNotFoundException extends ShoppingBaseException {

    private static final int STATUS_CODE = 404;
    private static final String MESSAGE = "장바구니 내 아이템을 찾을 수 없습니다. 현재 입력 값 : ";

    public CartItemNotFoundException(String input) {
        super(MESSAGE + input, STATUS_CODE);
    }
}
