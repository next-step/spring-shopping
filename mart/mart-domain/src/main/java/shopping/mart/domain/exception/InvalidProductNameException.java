package shopping.mart.domain.exception;

public final class InvalidProductNameException extends RuntimeException {

    public InvalidProductNameException(String message) {
        super(message);
    }
}
