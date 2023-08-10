package shopping.mart.domain.exception;

public final class NegativeProductCountException extends RuntimeException {

    public NegativeProductCountException(String message) {
        super(message);
    }
}
