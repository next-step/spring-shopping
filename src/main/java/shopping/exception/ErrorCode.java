package shopping.exception;

public enum ErrorCode {

    PRICE_LESS_THAN_ZERO("4xx", "가격은 0원보다 커야합니다."),
    PRODUCT_IMAGE_INVALID("4xx", "상품의 이미지 주소가 올바른 형식이 아닙니다."),
    PRODUCT_NAME_INVALID("4xx", "상품의 이름이 올바른 형식이 아닙니다."),
    EMAIL_INVALID("4xx", "이메일이 올바른 형식이 아닙니다."),
    NICKNAME_INVALID("4xx", "닉네임은 1글자 이상 10글자이하 입니다."),
    PASSWORD_INVALID("4xx", "패스워드는 소문자, 특수문자 7글자 이상 18글자 이하여야 합니다."),
    NOT_FOUND_MEMBER_EMAIL("4xx", "해당 이메일로 회원을 찾을 수 없습니다."),
    PASSWORD_NOT_MATCH("4xx", "이메일 또는 비밀번호가 잘못 입력 되었습니다.");

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
