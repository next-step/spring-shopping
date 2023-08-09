package shopping.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.HttpStatus;

public enum ProductExceptionType implements ExceptionType {

    NOT_FOUND_PRODUCT(NOT_FOUND, "존재하지 않는 상품입니다."),
    NO_CONTENT_IMAGE(BAD_REQUEST, "이미지 경로는 비어있거나 공백이면 안됩니다."),
    NO_CONTENT_NAME(BAD_REQUEST, "이름은 비어있거나 공백이면 안됩니다."),
    INVALID_PRICE_SIZE(BAD_REQUEST, "가격은 0 이하일 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ProductExceptionType(final HttpStatus httpStatus, final String message) {
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
