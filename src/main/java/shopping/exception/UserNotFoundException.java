package shopping.exception;

public final class UserNotFoundException extends ShoppingBaseException {

    private static final int STATUS_CODE = 400;
    private static final String MESSAGE = "해당하는 유저를 찾을 수 없습니다.";

    public UserNotFoundException() {
        super(MESSAGE, STATUS_CODE);
    }
}
