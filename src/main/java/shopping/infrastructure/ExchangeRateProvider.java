package shopping.infrastructure;

public interface ExchangeRateProvider {

    double getFrom(final CurrencyCountry country);
}
