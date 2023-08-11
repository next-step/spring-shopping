package shopping.exchange;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import shopping.exception.ExchangeExceptionType;
import shopping.exception.ShoppingException;

public interface CurrencyExchanger {

    Map<CurrencyType, Set<CurrencyType>> supportedCurrencyTypesByCurrencyType
        = Map.of(CurrencyType.USD, Set.of(CurrencyType.KRW));

    Optional<CurrencyExchangeRate> findCurrencyExchangeRate(final CurrencyType from,
        final CurrencyType to);

    default void validateSupportedExchangeType(final CurrencyType from, final CurrencyType to) {
        final Set<CurrencyType> supportedCurrencyTypes =
            supportedCurrencyTypesByCurrencyType.get(from);

        if (Objects.isNull(supportedCurrencyTypes) || !supportedCurrencyTypes.contains(to)) {
            throw new ShoppingException(ExchangeExceptionType.NOT_SUPPORTED_EXCHANGED_TYPE);
        }
    }
}
