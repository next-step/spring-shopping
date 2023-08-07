package shopping.exception;

public class NameLengthInvalidException extends ShoppingException {

    public NameLengthInvalidException() {
        super(ErrorType.NAME_TOO_LONG);
    }
}
