package shopping.exception;

public enum ErrorCode {

    PRICE_LESS_THAN_ZERO("4xx", "가격은 0원보다 커야합니다."),
    PRODUCT_IMAGE_INVALID("4xx", "상품의 이미지 주소가 올바른 형식이 아닙니다."),
    PRODUCT_NAME_INVALID("4xx", "상품의 이름이 올바른 형식이 아닙니다.");

    ErrorCode(final String errorPage, final String message) {
        this.errorPage = errorPage;
        this.message = message;
    }

    private final String errorPage;
    private final String message;

    public String getErrorPage() {
        return this.errorPage;
    }

    public String getMessage() {
        return this.message;
    }
}
