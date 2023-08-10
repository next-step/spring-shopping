package shopping.domain;

public enum CurrencyCountry {
    USDKRW(CurrencyType.USD, CurrencyType.KRW);

    private final String sourceCountry;
    private final String targetCountry;

    CurrencyCountry(CurrencyType sourceCountry, CurrencyType targetCountry) {
        this.sourceCountry = sourceCountry.getType();
        this.targetCountry = targetCountry.getType();
    }

    public String getSourceTargetCountry() {
        return (sourceCountry + targetCountry);
    }

    public String getSourceCountry() {
        return sourceCountry;
    }

    public String getTargetCountry() {
        return targetCountry;
    }
}
