package shopping.exception;

public class BadLoginRequestException extends ShoppingException {

    public BadLoginRequestException() {
        super(ErrorType.WRONG_LOGIN_REQUEST);
    }
}
