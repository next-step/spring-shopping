package shopping.exception;

public class EmptyCartException extends ShoppingBaseException {

    private static final int STATUS_CODE = 400;
    private static final String MESSAGE = "카트가 비어있습니다.";

    public EmptyCartException() {
        super(MESSAGE, STATUS_CODE);
    }
}
