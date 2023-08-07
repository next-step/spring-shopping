package shopping.exception;

public class EmailLengthInvalidException extends ShoppingException {

    public EmailLengthInvalidException() {
        super(ErrorType.EMAIL_TOO_LONG);
    }
}
