package shopping.exception;

public final class UserNotFoundException extends ShoppingBaseException {

    private static final int STATUS_CODE = 400;
    private static final String MESSAGE = "해당하는 유저를 찾을 수 없습니다. 현재 입력 값 : ";

    public UserNotFoundException(String email) {
        super(MESSAGE + email, STATUS_CODE);
    }
}
