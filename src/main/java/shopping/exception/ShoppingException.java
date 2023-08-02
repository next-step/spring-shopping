package shopping.exception;

import org.springframework.http.HttpStatus;

public class ShoppingException extends RuntimeException {

    private HttpStatus status;

    public ShoppingException(ErrorType errorType) {
        super(errorType.getMessage());
        this.status = errorType.getStatus();
    }

    public ShoppingException(ErrorType errorType, Object value) {
        this(errorType, value, null);
    }

    public ShoppingException(ErrorType errorType, Object value, Throwable cause) {
        super(errorType.getMessage() + value.toString(), cause);
        this.status = errorType.getStatus();
    }

    public HttpStatus getStatus() {
        return status;
    }
}
