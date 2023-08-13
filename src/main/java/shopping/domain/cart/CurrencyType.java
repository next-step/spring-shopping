package shopping.domain.cart;

public enum CurrencyType {
    KRW("KRW"),
    USD("USD"),
    ;

    private final String type;

    CurrencyType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
