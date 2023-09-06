package shopping.mart.domain.status;

public enum OrderExceptionStatus {

    EMPTY_CART("ORDER-401"),
    NOT_EXISTS_ORDER("ORDER-402"),
    ;

    private final String status;

    OrderExceptionStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
