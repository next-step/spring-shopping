package shopping.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    PRICE_INVALID(HttpStatus.BAD_REQUEST, "가격은 0원보다 크고 10억보다 같거나 낮아야합니다."),
    ORDER_PRICE_INVALID(HttpStatus.BAD_REQUEST, "주문 총 가격은 0원보다 크고 1조보다 같거나 낮아야합니다."),
    ORDER_ITEM_IMAGE_INVALID(HttpStatus.BAD_REQUEST, "주문 아이템의 이미지 주소가 올바른 형식이 아닙니다."),
    ORDER_ITEM_QUANTITY_INVALID(HttpStatus.BAD_REQUEST, "주문 아이템 수량 개수는 1개 이상 1000개 이하여야합니다."),
    ORDER_ITEM_NAME_INVALID(HttpStatus.BAD_REQUEST, "주문 아이템의 이름이 올바른 형식이 아닙니다."),
    PRODUCT_IMAGE_INVALID(HttpStatus.BAD_REQUEST, "상품의 이미지 주소가 올바른 형식이 아닙니다."),
    PRODUCT_NAME_INVALID(HttpStatus.BAD_REQUEST, "상품의 이름이 올바른 형식이 아닙니다."),
    EMAIL_INVALID(HttpStatus.BAD_REQUEST, "이메일이 올바른 형식이 아닙니다."),
    NICKNAME_INVALID(HttpStatus.BAD_REQUEST, "닉네임은 1글자 이상 10글자이하 입니다."),
    PASSWORD_INVALID(HttpStatus.BAD_REQUEST, "패스워드는 소문자, 특수문자 7글자 이상 18글자 이하여야 합니다."),
    NOT_FOUND_MEMBER_EMAIL(HttpStatus.NOT_FOUND, "해당 이메일로 회원을 찾을 수 없습니다."),
    PASSWORD_NOT_MATCH(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 잘못 입력 되었습니다."),
    CART_ITEM_QUANTITY_INVALID(HttpStatus.BAD_REQUEST, "장바구니 상품 수량 개수는 1개 이상 1000개 이하여야합니다."),
    NOT_FOUND_PRODUCT_ID(HttpStatus.NOT_FOUND, "해당 상품을 찾을 수 없습니다."),
    NOT_FOUND_MEMBER_ID(HttpStatus.NOT_FOUND, "해당 회원을 찾을 수 없습니다."),
    NOT_FOUND_ORDER_ID(HttpStatus.NOT_FOUND, "해당 주문 정보를 찾을 수 없습니다."),
    NOT_FOUND_CART_ITEM_ID(HttpStatus.NOT_FOUND, "해당 장바구니 아이템을 찾을 수 없습니다."),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "토큰이 유효하지 않습니다."),
    TOKEN_IS_EMPTY(HttpStatus.BAD_REQUEST, "토큰이 존재하지 않습니다."),
    FORBIDDEN_MODIFY_CART_ITEM(HttpStatus.FORBIDDEN, "해당 장바구니 아이템을 수정할 권한이 없습니다."),
    EMPTY_CART_ITEM(HttpStatus.BAD_REQUEST, "해당 장바구니가 비어있습니다."),
    CURRENCY_API_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "환율 정보를 가져오지 못했습니다."),
    API_RETRY_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "외부 API 재시도를 했지만 실패했습니다."),
    RETRY_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "환율 정보를 가져오지 못했습니다."),
    CACHED_INVALID(HttpStatus.INTERNAL_SERVER_ERROR, "캐시된 정보가 유효하지 않습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(final HttpStatus httpStatus, final String message) {
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
