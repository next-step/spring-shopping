package shopping.exception;

public class NoCartItemForOrderException extends ShoppingBaseException {

    private static final int STATUS_CODE = 400;
    private static final String MESSAGE = "장바구니에 아이템이 없어 주문할 수 없습니다.";

    public NoCartItemForOrderException() {
        super(MESSAGE, STATUS_CODE);
    }
}
