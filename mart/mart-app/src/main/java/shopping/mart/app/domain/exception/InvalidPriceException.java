package shopping.mart.app.domain.exception;

public final class InvalidPriceException extends RuntimeException {

    public InvalidPriceException(String message) {
        super(message);
    }
}
