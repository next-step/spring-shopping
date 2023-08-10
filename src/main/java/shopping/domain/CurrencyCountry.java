package shopping.domain;

public enum CurrencyCountry {
    USDKRW(CurrencyType.USD, CurrencyType.KRW);

    private final String sourceCurrency;
    private final String targetCurrency;

    CurrencyCountry(CurrencyType sourceCurrency, CurrencyType targetCurrency) {
        this.sourceCurrency = sourceCurrency.getType();
        this.targetCurrency = targetCurrency.getType();
    }

    public String getSourceTargetCountry() {
        return (sourceCurrency + targetCurrency);
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public String getTargetCurrency() {
        return targetCurrency;
    }
}
