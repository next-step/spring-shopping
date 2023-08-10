package shopping.service;

import shopping.domain.CurrencyCountry;
import shopping.dto.request.CurrencyRequest;

public interface CurrencyService {

    CurrencyRequest callCurrency(CurrencyCountry currencyCountry);

}
