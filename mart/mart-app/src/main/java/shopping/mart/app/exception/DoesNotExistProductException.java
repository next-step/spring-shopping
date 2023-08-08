package shopping.mart.app.exception;

public final class DoesNotExistProductException extends RuntimeException {

    public DoesNotExistProductException(String message) {
        super(message);
    }
}
