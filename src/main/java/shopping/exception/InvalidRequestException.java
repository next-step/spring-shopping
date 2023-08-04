package shopping.exception;

public class InvalidRequestException extends ShoppingBaseException{

    private static final int STATUS_CODE = 400;

    public InvalidRequestException(String message) {
        super(message, STATUS_CODE);
    }
}
