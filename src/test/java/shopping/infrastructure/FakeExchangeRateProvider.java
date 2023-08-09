package shopping.infrastructure;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import shopping.application.ExchangeRateProvider;

import java.util.Optional;

@Component
@Profile("test")
public class FakeExchangeRateProvider implements ExchangeRateProvider {

    @Override
    public Optional<Double> getExchangeRate() {
        return Optional.of(1.0D);
    }
}
