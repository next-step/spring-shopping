package shopping.auth.domain.status;

public enum UserExceptionStatus {

    LOGIN_FAILED("AUTH-401"),
    INVALID_EMAIL("AUTH-402"),
    INVALID_PASSWORD("AUTH-403"),
    ;

    private final String status;

    UserExceptionStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
