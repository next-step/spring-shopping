package shopping.exception;

import org.springframework.http.HttpStatus;

public class ShoppingException extends RuntimeException {

    private final ErrorCode errorCode;

    public ShoppingException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public HttpStatus getHttpStatus() {
        return this.errorCode.getHttpStatus();
    }

    @Override
    public String getMessage() {
        return errorCode.getMessage();
    }
}

