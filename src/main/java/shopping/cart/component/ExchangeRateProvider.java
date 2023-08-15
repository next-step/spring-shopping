package shopping.cart.component;

import shopping.cart.domain.vo.ExchangeRate;

public interface ExchangeRateProvider {

    ExchangeRate fetchExchangeRate();
}
