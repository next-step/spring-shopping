package shopping.exception;

public class ArgumentValidateFailException extends ShoppingBaseException {

    private static final int STATUS_CODE = 400;

    public ArgumentValidateFailException(String message) {
        super(message, STATUS_CODE);
    }
}
