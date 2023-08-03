package shopping.exception;

import org.springframework.http.HttpStatus;

public class ShoppingException extends RuntimeException {

    private final HttpStatus status;

    public ShoppingException(final ErrorType errorType) {
        super(errorType.getMessage());
        this.status = errorType.getStatus();
    }

    public ShoppingException(final ErrorType errorType, final Object value) {
        this(errorType, value, null);
    }

    public ShoppingException(final ErrorType errorType, final Object value, final Throwable cause) {
        super(errorType.getMessage() + value.toString(), cause);
        this.status = errorType.getStatus();
    }

    public HttpStatus getStatus() {
        return status;
    }
}
