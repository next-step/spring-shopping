package shopping.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.springframework.http.HttpStatus;

public enum ExceptionType {

    /* CartProduct */
    DUPLICATED_CART_PRODUCT(BAD_REQUEST, "이미 장바구니에 담긴 상품입니다."),
    NOT_FOUND_CART_PRODUCT(NOT_FOUND, "존재하지 않는 장바구니 상품입니다."),

    /* CarProductQuantity */
    INVALID_QUANTITY_SIZE(BAD_REQUEST, "장바구니 상품 개수는 0 이하일 수 없습니다."),

    /* MemberEmail */
    NO_CONTENT_EMAIL(BAD_REQUEST, "이메일은 비어있거나 공백이면 안됩니다."),
    WRONG_EMAIL_FORMAT(BAD_REQUEST, "이메일이 형식에 맞지 않습니다."),
    NOT_FOUND_EMAIL(NOT_FOUND, "존재하지 않는 이메일입니다."),

    /* MemberPassword */
    INVALID_PASSWORD_LENGTH(BAD_REQUEST, "비밀번호는 30자보다 길 수 없습니다."),
    NO_CONTENT_PASSWORD(BAD_REQUEST, "비밀번호는 비어있거나 공백이면 안됩니다."),
    NOT_MATCH_PASSWORD(BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    /* Product */
    NOT_FOUND_PRODUCT(NOT_FOUND, "존재하지 않는 상품입니다."),

    /* ProductImage */
    NO_CONTENT_IMAGE(BAD_REQUEST, "이미지 경로는 비어있거나 공백이면 안됩니다."),

    /* ProductName */
    NO_CONTENT_NAME(BAD_REQUEST, "이름은 비어있거나 공백이면 안됩니다."),

    /* ProductPrice */
    INVALID_PRICE_SIZE(BAD_REQUEST, "가격은 0 이하일 수 없습니다."),

    /* Authorization */
    NO_AUTHORIZATION_HEADER(UNAUTHORIZED, "Authorization 헤더가 존재하지 않습니다."),
    NO_CONTENT_TOKEN(UNAUTHORIZED, "토큰 값이 존재하지 않습니다."),
    NOT_BEARER_TOKEN(UNAUTHORIZED, "토큰이 Bearer로 시작하지 않습니다."),
    INVALID_TOKEN(UNAUTHORIZED, "토큰이 유효하지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    ExceptionType(final HttpStatus httpStatus, final String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public String getMessage() {
        return this.message;
    }
}
