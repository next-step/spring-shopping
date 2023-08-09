package shopping.exception.infrastructure;

public class ConnectionFailException extends Exception {

    private final int errorCode;

    public ConnectionFailException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
