package shopping.exception.general;

public abstract class ShoppingBaseException extends RuntimeException {

    private final int statusCode;

    protected ShoppingBaseException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
