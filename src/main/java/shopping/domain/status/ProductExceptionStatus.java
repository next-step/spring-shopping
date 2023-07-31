package shopping.domain.status;

public enum ProductExceptionStatus {

    NEGATIVE_PRICE("PRODUCT-401"),
    NOT_NUMBER("PRODUCT-402"),
    EXCEED_NAME("PRODUCT-403"),
    BLANK_NAME("PRODUCT-404"),
    NULL_NAME("PRODUCT-405"),
    ;

    private final String status;

    ProductExceptionStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
