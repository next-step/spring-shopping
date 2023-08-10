package shopping.exception.infrastructure;

public class ConnectionErrorException extends Exception {

    private final int errorCode;

    public ConnectionErrorException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
