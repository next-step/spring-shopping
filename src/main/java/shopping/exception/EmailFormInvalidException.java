package shopping.exception;

public class EmailFormInvalidException extends ShoppingException {

    public EmailFormInvalidException() {
        super(ErrorType.EMAIL_INVALID_FORM);
    }
}
