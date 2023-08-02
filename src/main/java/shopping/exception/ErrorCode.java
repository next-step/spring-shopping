package shopping.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    EMAIL_NOT_VALID(HttpStatus.UNAUTHORIZED, "유효하지 않은 이메일입니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
