package shopping.mart.app.domain.exception;

public final class AlreadyExistProductException extends RuntimeException {

    public AlreadyExistProductException(String message) {
        super(message);
    }
}
