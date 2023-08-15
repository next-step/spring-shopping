package shopping.other.exchange.currencylayer;

import shopping.common.domain.Rate;
import shopping.order.service.ExchangeRateService;
import shopping.order.service.ExchangeType;

public class MockExchangeRateServiceImpl implements ExchangeRateService {
    @Override
    public Rate getExchangeRate(ExchangeType source, ExchangeType target) {
        return new Rate(1000L);
    }
}
