package shopping.infrastructure;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import shopping.application.ExchangeRateProviderService;

@Component
@Profile("test")
public class FakeExchangeRateProvider implements ExchangeRateProviderService {

    @Override
    public Double getExchangeRate() {
        return 1.0D;
    }
}
