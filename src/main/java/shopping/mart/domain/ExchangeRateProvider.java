package shopping.mart.domain;

public interface ExchangeRateProvider {

    CurrencyRate getCurrencyRate(final CurrencyType source, final CurrencyType target);
}
