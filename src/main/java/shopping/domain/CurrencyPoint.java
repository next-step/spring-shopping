package shopping.domain;

public enum CurrencyPoint {
    DECIAML(10D),
    HUNDREDTH(100D),
    ;

    private final Double digits;

    CurrencyPoint(Double digits) {
        this.digits = digits;
    }

    public Double getDigits() {
        return digits;
    }
}
