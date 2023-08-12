package shopping.infrastructure.dto;

public class ExchangeRateResponse {

    private Double rate;

    public ExchangeRateResponse(Double rate) {
        this.rate = rate;
    }

    public Double getRate() {
        return rate;
    }
}
