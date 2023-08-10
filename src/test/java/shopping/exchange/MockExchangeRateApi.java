package shopping.exchange;

import util.ExchangeRateApi;

public class MockExchangeRateApi implements ExchangeRateApi {

    @Override
    public double callExchangeRate() {
        return 1300.1;
    }
}
