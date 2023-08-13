package shopping.infrastructure;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import shopping.domain.cart.CurrencyType;
import shopping.exception.infrastructure.ErrorResponseException;
import shopping.exception.infrastructure.NullResponseException;

@Profile("test")
@Primary
@Component
public class MockExchangeFetcher implements ExchangeRateFetcher {

    private static final CurrencyType SOURCE = CurrencyType.USD;
    private static final CurrencyType TARGET = CurrencyType.KRW;
    private static final String NOT_SUPPORT_SOURCE_MESSAGE = "Access Restricted - Your current Subscription Plan does not support Source Currency Switching.";
    private static final int NOT_SUPPORT_SOURCE_ERROR_CODE = 105;

    @Override
    public Double getExchangeRate(CurrencyType source, CurrencyType target) throws ErrorResponseException, NullResponseException {
        if (source.equals(SOURCE) && target.equals(TARGET)) {
            return 1300.0;
        }
        throw new ErrorResponseException(NOT_SUPPORT_SOURCE_MESSAGE, NOT_SUPPORT_SOURCE_ERROR_CODE);
    }

}
