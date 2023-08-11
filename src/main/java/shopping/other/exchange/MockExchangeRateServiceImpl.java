package shopping.other.exchange;

import org.springframework.stereotype.Component;
import shopping.common.domain.Rate;
import shopping.order.service.ExchangeRateService;

@Component
public class MockExchangeRateServiceImpl implements ExchangeRateService {
    @Override
    public Rate getExchangeRate(String source, String target) {
        return new Rate(1000L);
    }
}
