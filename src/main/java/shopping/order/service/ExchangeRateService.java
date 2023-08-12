package shopping.order.service;

import shopping.common.domain.Rate;

public interface ExchangeRateService {
    Rate getExchangeRate(ExchangeType source, ExchangeType target);
}
