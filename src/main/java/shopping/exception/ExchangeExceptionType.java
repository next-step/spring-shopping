package shopping.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.http.HttpStatus;

public enum ExchangeExceptionType implements ExceptionType {

    NOT_SUPPORTED_EXCHANGED_TYPE(BAD_REQUEST, "지원하지 않는 환율 정보입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ExchangeExceptionType(final HttpStatus httpStatus, final String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
