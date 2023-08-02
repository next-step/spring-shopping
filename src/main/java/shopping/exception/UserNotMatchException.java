package shopping.exception;

public class UserNotMatchException extends ShoppingBaseException {

    private static final int STATUS_CODE = 403;
    private static final String MESSAGE = "잘못된 접근입니다.";

    public UserNotMatchException() {
        super(MESSAGE, STATUS_CODE);
    }
}
