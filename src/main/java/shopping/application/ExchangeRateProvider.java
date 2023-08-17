package shopping.application;

import shopping.domain.ExchangeCode;
import shopping.domain.ExchangeRate;

public interface ExchangeRateProvider {

    ExchangeRate getExchange(ExchangeCode code);
}
