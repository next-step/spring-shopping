package shopping.mart.app.domain.exception;

public final class NegativeProductCountException extends RuntimeException {

    public NegativeProductCountException(String message) {
        super(message);
    }
}
