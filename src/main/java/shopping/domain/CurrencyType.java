package shopping.domain;

public enum CurrencyType {
    USD("USD"),
    KRW("KRW"),
    ;

    private final String type;

    CurrencyType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
