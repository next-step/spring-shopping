package shopping.domain;

public enum CurrencyConversion {
    USD_KRW(CurrencyType.USD, CurrencyType.KRW);

    private final String source;
    private final String target;

    CurrencyConversion(CurrencyType source, CurrencyType target) {
        this.source = source.getType();
        this.target = target.getType();
    }

    public String getSourceTarget() {
        return (source + target);
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }
}
