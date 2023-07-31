package shopping.exception;


public class ShoppingException extends RuntimeException {

    private final ErrorCode errorCode;

    public ShoppingException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorPage() {
        return errorCode.getErrorPage();
    }

    @Override
    public String getMessage() {
        return errorCode.getMessage();
    }
}
