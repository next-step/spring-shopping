package shopping.currency;

import shopping.order.domain.vo.ExchangeRate;

public interface CurrencyProvider {

    ExchangeRate findUsdKrwCurrency();
}
