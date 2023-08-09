package shopping.application;

import shopping.domain.ExchangeRate;

public interface ExchangeRateProvider {

    ExchangeRate getExchange(String quote);
}
