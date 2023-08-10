package shopping.auth.domain.exception;

public final class AlreadyExistUserException extends RuntimeException {

    public AlreadyExistUserException(String message) {
        super(message);
    }
}
