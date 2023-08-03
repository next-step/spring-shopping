package shopping.exception;

import org.springframework.http.HttpStatus;

public enum ErrorType {

    CART_ITEM_NO_EXIST(HttpStatus.BAD_REQUEST, "존재하지 않는 장바구니 상품 id입니다 : "),
    USER_NO_EXIST(HttpStatus.BAD_REQUEST, "존재하지 않는 사용자 id입니다 : "),
    PRODUCT_NO_EXIST(HttpStatus.BAD_REQUEST, "존재하지 않는 상품 id입니다 : "),
    USER_NOT_CONTAINS_ITEM(HttpStatus.BAD_REQUEST, "유효하지 않은 cart item 입니다 : "),
    WRONG_LOGIN_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 로그인 요청입니다."),
    EMAIL_TOO_LONG(HttpStatus.BAD_REQUEST, "이메일 길이는 50자를 넘을 수 없습니다."),
    EMAIL_INVALID_FORM(HttpStatus.BAD_REQUEST, "이메일 형식이 올바르지 않습니다."),
    IMAGE_TOO_LONG(HttpStatus.BAD_REQUEST, "이미지 주소는 255자를 넘을 수 없습니다."),
    NAME_TOO_LONG(HttpStatus.BAD_REQUEST, "이름은 20자를 넘을 수 없습니다."),
    PRICE_INVALID(HttpStatus.BAD_REQUEST, "가격은 음수일 수 없습니다."),
    QUANTITY_INVALID(HttpStatus.BAD_REQUEST, "수량은 0보다 작거나 같을 수 없습니다."),
    NO_TOKEN(HttpStatus.BAD_REQUEST, "토큰 정보가 없습니다."),
    TOKEN_INVALID(HttpStatus.BAD_REQUEST, "토큰이 유효하지 않습니다."),
    PRODUCT_INVALID(HttpStatus.BAD_REQUEST, "productId는 양의 정수입니다."),
    PRODUCT_NULL(HttpStatus.BAD_REQUEST, "productId는 필수 항목입니다."),
    PASSWORD_NULL(HttpStatus.BAD_REQUEST, "password는 필수 항목입니다."),
    EMAIL_NULL(HttpStatus.BAD_REQUEST, "email은 필수 항목입니다."),
    DECODING_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "복호화 실패");

    private final HttpStatus status;
    private final String message;

    ErrorType(final HttpStatus status, final String message) {
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
