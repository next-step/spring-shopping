package shopping.exception;

public class OrderNotFoundException extends ShoppingBaseException {

    private static final int STATUS_CODE = 404;
    private static final String MESSAGE = "주문을 찾을 수 없습니다. 현재 입력 값 : ";

    public OrderNotFoundException(String input) {
        super(MESSAGE + input, STATUS_CODE);
    }
}
