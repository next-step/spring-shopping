package shopping.exception;

import org.springframework.http.HttpStatus;

public enum OrderExceptionType implements ExceptionType {
    INVALID_QUANTITY_SIZE(HttpStatus.BAD_REQUEST, "주문 상품 개수는 1개 이상이어야 합니다.");

    private final HttpStatus httpStatus;
    private final String message;

    OrderExceptionType(final HttpStatus httpStatus, final String message) {
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
