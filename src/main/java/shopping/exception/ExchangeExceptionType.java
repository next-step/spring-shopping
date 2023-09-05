package shopping.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import org.springframework.http.HttpStatus;

public enum ExchangeExceptionType implements ExceptionType {

    NOT_SUPPORTED_EXCHANGED_TYPE(BAD_REQUEST, "지원하지 않는 환율 정보입니다."),
    INVALID_CURRENCY_EXCHANGE_VALUE(INTERNAL_SERVER_ERROR, "환율은 0 이하일 수 없습니다.");

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
