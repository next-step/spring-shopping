package shopping.exception.infrastructure;

public class ErrorResponseException extends Exception {

    private final int errorCode;

    public ErrorResponseException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
