package shopping.exception;

public class UserNotFoundException extends ShoppingException {

    public UserNotFoundException(long userId) {
        super(message(userId));
    }

    private static String message(final long userId) {
        return String.format("%s- 요청 유저 id: %d", ErrorType.USER_NO_EXIST, userId);
    }
}
