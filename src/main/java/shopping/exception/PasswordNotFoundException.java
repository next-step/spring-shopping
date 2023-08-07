package shopping.exception;

public class PasswordNotFoundException extends ShoppingException {

    public PasswordNotFoundException() {
        super(ErrorType.PASSWORD_NULL);
    }
}
