package shopping.dto.request;

import shopping.domain.Ratio;

public class ExchangeRate {

    Double ratio;

    public ExchangeRate(Double ratio) {
        this.ratio = ratio;
    }

    public Ratio toDomain() {
        return new Ratio(ratio);
    }
}
