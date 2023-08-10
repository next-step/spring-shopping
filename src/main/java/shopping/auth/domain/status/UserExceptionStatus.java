package shopping.auth.domain.status;

public enum UserExceptionStatus {

    LOGIN_FAILED("AUTH-401"),
    INVALID_EMAIL("AUTH-402"),
    INVALID_PASSWORD("AUTH-403"),
    ALREADY_EXIST_USER("AUTH-404"),
    NOT_EXIST_USER("AUTH-405"),
    ;

    private final String status;

    UserExceptionStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
