package shopping.exception;

public class UserNotHasCartItemException extends ShoppingException {

    public UserNotHasCartItemException() {
        super(ErrorType.USER_NOT_CONTAINS_ITEM);
    }
}
