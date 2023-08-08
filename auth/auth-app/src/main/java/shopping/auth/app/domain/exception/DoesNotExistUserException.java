package shopping.auth.app.domain.exception;

public final class DoesNotExistUserException extends RuntimeException {

    public DoesNotExistUserException(String message) {
        super(message);
    }
}
