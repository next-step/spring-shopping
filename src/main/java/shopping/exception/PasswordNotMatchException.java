package shopping.exception;

public class PasswordNotMatchException extends ShoppingBaseException {

    private static final int STATUS_CODE = 400;
    private static final String MESSAGE = "비밀번호가 일치하지 않습니다.";

    public PasswordNotMatchException() {
        super(MESSAGE, STATUS_CODE);
    }
}
