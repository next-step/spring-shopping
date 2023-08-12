package shopping.order.service.currency;

import shopping.order.domain.vo.ExchangeRate;

public interface ExchangeRateProvider {

    ExchangeRate findTargetExchangeRate();
}
