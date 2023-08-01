package shopping.exception;

import org.springframework.http.HttpStatus;

public class ShoppingApiException extends RuntimeException {

    private final ErrorCode errorCode;

    public ShoppingApiException(final ErrorCode errorCode) {
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

