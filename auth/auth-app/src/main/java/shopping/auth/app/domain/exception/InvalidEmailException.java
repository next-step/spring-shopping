package shopping.auth.app.domain.exception;

public final class InvalidEmailException extends RuntimeException {

    public InvalidEmailException(String message) {
        super(message);
    }
}
