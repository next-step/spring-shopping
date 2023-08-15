package shopping.exception;

public enum ErrorType {

    CART_ITEM_NO_EXIST("존재하지 않는 장바구니 상품 id입니다"),
    USER_NO_EXIST("존재하지 않는 사용자 id입니다"),
    PRODUCT_NO_EXIST("존재하지 않는 상품 id입니다"),
    USER_NOT_CONTAINS_ITEM("사용자가 장바구니에 담지 않은 상품입니다"),
    WRONG_LOGIN_REQUEST("잘못된 로그인 요청입니다"),
    EMAIL_TOO_LONG("이메일 길이는 50자를 넘을 수 없습니다"),
    EMAIL_INVALID_FORM("이메일 형식이 올바르지 않습니다"),
    IMAGE_TOO_LONG("이미지 주소는 255자를 넘을 수 없습니다"),
    NAME_TOO_LONG("이름은 20자를 넘을 수 없습니다"),
    PRICE_INVALID("가격은 음수일 수 없습니다"),
    QUANTITY_INVALID("수량은 0보다 작거나 같을 수 없습니다"),
    NO_TOKEN("토큰 정보가 없습니다"),
    TOKEN_INVALID("토큰이 유효하지 않습니다"),
    PRODUCT_INVALID("productId는 양의 정수입니다"),
    PRODUCT_NULL("productId는 필수 항목입니다"),
    PASSWORD_NULL("password는 필수 항목입니다"),
    EMAIL_NULL("email은 필수 항목입니다"),
    ORDER_NO_EXIST("유저의 주문을 찾을 수 없습니다"),
    EXCHANGE_RATE_NULL("환율 정보가 존재하지 않습니다"),
    SERVER_ERROR("서버에 에러가 발생했습니다");

    private final String message;

    ErrorType(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
