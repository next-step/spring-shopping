package shopping.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.HttpStatus;

public enum CartExceptionType implements ExceptionType {

    DUPLICATED_CART_PRODUCT(BAD_REQUEST, "이미 장바구니에 담긴 상품입니다."),
    NOT_FOUND_CART_PRODUCT(NOT_FOUND, "존재하지 않는 장바구니 상품입니다."),
    INVALID_QUANTITY_SIZE(BAD_REQUEST, "장바구니 상품 개수는 0 이하일 수 없습니다."),
    EMPTY_CART(BAD_REQUEST, "장바구니가 비어있습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    CartExceptionType(final HttpStatus httpStatus, final String message) {
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
