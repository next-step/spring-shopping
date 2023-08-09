package shopping.application;

import java.util.Optional;

public interface ExchangeRateProvider {

    Optional<Double> getExchangeRate();
}
