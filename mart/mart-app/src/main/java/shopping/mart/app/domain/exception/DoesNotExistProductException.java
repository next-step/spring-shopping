package shopping.mart.app.domain.exception;

public final class DoesNotExistProductException extends RuntimeException {

    public DoesNotExistProductException(String message) {
        super(message);
    }
}
