package shopping.infrastructure;

import shopping.exception.infrastructure.ConnectionErrorException;
import shopping.exception.infrastructure.NullResponseException;

public interface ExchangeRateFetcher {

    Double getExchangeRate(String source, String target) throws ConnectionErrorException, NullResponseException;
}
