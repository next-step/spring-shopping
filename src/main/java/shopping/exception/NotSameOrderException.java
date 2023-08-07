package shopping.exception;

public class NotSameOrderException extends ShoppingBaseException {
    private static final int STATUS_CODE = 500;

    public NotSameOrderException() {
        super("", STATUS_CODE);
    }
}
