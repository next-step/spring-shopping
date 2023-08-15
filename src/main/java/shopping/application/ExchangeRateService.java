package shopping.application;

import org.springframework.stereotype.Service;
import shopping.domain.vo.ExchangeRate;
import shopping.exception.ExchangeRateNotFoundException;
import shopping.infrastructure.CurrencyCountry;
import shopping.infrastructure.ExchangeRateProvider;

@Service
public class ExchangeRateService {

    private final ExchangeRateProvider provider;

    public ExchangeRateService(final ExchangeRateProvider provider) {
        this.provider = provider;
    }

    public ExchangeRate convert(final CurrencyCountry country) {
        final Double exchangeRate = provider.getFrom(country)
                .orElseThrow(ExchangeRateNotFoundException::new);
        return new ExchangeRate(exchangeRate);
    }
}
