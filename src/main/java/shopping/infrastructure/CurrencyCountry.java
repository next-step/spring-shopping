package shopping.infrastructure;

public enum CurrencyCountry {

    KOREA("KRW"),
    USA("USA");

    private final String currencyLayerName;

    CurrencyCountry(final String currencyLayerName) {
        this.currencyLayerName = currencyLayerName;
    }

    public String getCurrencyLayerName() {
        return currencyLayerName;
    }
}
