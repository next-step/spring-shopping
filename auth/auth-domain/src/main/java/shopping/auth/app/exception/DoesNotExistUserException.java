package shopping.auth.app.exception;

public final class DoesNotExistUserException extends RuntimeException {

    public DoesNotExistUserException(String message) {
        super(message);
    }
}
