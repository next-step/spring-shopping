package shopping.exception;

import org.springframework.http.HttpStatus;

public enum ShoppingErrorType {

    PRICE_LESS_THAN_ZERO(HttpStatus.BAD_REQUEST, "가격은 0원보다 커야합니다."),
    PRODUCT_IMAGE_INVALID(HttpStatus.BAD_REQUEST, "상품의 이미지 주소가 올바른 형식이 아닙니다."),
    PRODUCT_NAME_INVALID(HttpStatus.BAD_REQUEST, "상품의 이름이 올바른 형식이 아닙니다."),
    EMAIL_INVALID(HttpStatus.BAD_REQUEST, "이메일이 올바른 형식이 아닙니다."),
    NICKNAME_INVALID(HttpStatus.BAD_REQUEST, "닉네임은 1글자 이상 10글자이하 입니다."),
    PASSWORD_INVALID(HttpStatus.BAD_REQUEST, "패스워드는 소문자, 특수문자 7글자 이상 18글자 이하여야 합니다."),
    NOT_FOUND_MEMBER_EMAIL(HttpStatus.NOT_FOUND, "해당 이메일로 회원을 찾을 수 없습니다."),
    PASSWORD_NOT_MATCH(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 잘못 입력 되었습니다."),
    QUANTITY_INVALID(HttpStatus.BAD_REQUEST, "장바구니 상품 수량 개수는 1개 이상 1000개 이하여야합니다."),
    NOT_FOUND_PRODUCT_ID(HttpStatus.NOT_FOUND, "해당 상품을 찾을 수 없습니다."),
    NOT_FOUND_MEMBER_ID(HttpStatus.NOT_FOUND, "해당 회원을 찾을 수 없습니다."),
    NOT_FOUND_CART_ITEM_ID(HttpStatus.NOT_FOUND, "해당 장바구니 아이템을 찾을 수 없습니다."),
    NOT_FOUND_ORDER_ID(HttpStatus.NOT_FOUND, "해당 주문을 찾을 수 없습니다."),
    INVALID_ORDER_REQUEST(HttpStatus.BAD_REQUEST, "장바구니에 상품이 없는 경우 주문할 수 없습니다."),
    ERROR_EXCHANGE_RATE(HttpStatus.INTERNAL_SERVER_ERROR, "환율 API에서 오류가 발생했습니다."),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다."),
    TOKEN_IS_EMPTY(HttpStatus.BAD_REQUEST, "토큰이 존재하지 않습니다."),
    FORBIDDEN_MODIFY_CART_ITEM(HttpStatus.FORBIDDEN, "해당 장바구니 아이템을 수정할 권한이 없습니다.");

    ShoppingErrorType(final HttpStatus httpStatus, final String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    private final HttpStatus httpStatus;
    private final String message;

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public String getMessage() {
        return this.message;
    }
}
