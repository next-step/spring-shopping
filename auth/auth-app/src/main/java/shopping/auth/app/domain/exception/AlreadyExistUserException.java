package shopping.auth.app.domain.exception;

public final class AlreadyExistUserException extends RuntimeException {

    public AlreadyExistUserException(String message) {
        super(message);
    }
}
