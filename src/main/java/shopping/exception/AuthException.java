package shopping.exception;

import org.springframework.http.HttpStatus;

public class AuthException extends RuntimeException {
    private final ErrorCode errorCode;

    public AuthException(final ErrorCode errorCode) {
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
