package shopping.currency;

import shopping.order.domain.vo.ExchangeRate;

public interface ExchangeRateProvider {

    ExchangeRate findUsdKrwExchangeRate();
}
