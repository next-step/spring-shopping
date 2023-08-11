package shopping.exchange;

public enum CurrencyType {

    KRW("KRW"), USD("USD");

    private final String name;

    CurrencyType(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
