package shopping.exchange;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.springframework.stereotype.Component;
import shopping.exception.ExchangeExceptionType;
import shopping.exception.ShoppingException;

/**
 * 외부 API가 정해져있지 않을 경우 테스트 용도로만 사용한다.
 */
@Component
public class DummyCurrencyExchangeManager implements CurrencyExchangeManager {

    private static final Map<CurrencyType, Set<CurrencyType>> supportedCurrencyTypesByCurrencyType
        = Map.of(CurrencyType.KRW, Set.of(CurrencyType.USD));

    @Override
    public Optional<Double> findCurrencyExchangeRate(
        final CurrencyType from,
        final CurrencyType to
    ) {
        validateSupportedExchangeType(from, to);

        return Optional.of(1300.0);
    }


    private void validateSupportedExchangeType(final CurrencyType from, final CurrencyType to) {
        final Set<CurrencyType> supportedCurrencyTypes =
            supportedCurrencyTypesByCurrencyType.get(from);

        if (Objects.isNull(supportedCurrencyTypes) || !supportedCurrencyTypes.contains(to)) {
            throw new ShoppingException(ExchangeExceptionType.NOT_SUPPORTED_EXCHANGED_TYPE);
        }
    }
}
