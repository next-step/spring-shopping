package shopping.exception;

public class EmailNotFoundException extends ShoppingException {

    public EmailNotFoundException() {
        super(ErrorType.EMAIL_NULL);
    }
}
