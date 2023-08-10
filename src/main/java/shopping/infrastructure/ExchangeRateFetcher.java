package shopping.infrastructure;

import shopping.domain.cart.CurrencyType;
import shopping.exception.infrastructure.ConnectionErrorException;
import shopping.exception.infrastructure.NullResponseException;

public interface ExchangeRateFetcher {

    Double getExchangeRate(CurrencyType source, CurrencyType target) throws ConnectionErrorException, NullResponseException;
}
