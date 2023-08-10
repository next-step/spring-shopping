package shopping.utils;

import java.text.MessageFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shopping.domain.Currency;
import shopping.domain.CurrencyCountry;

@Component
public class CurrencyLayer {

    @Value("${currency.access-key}")
    private String CURRENCY_ACCESS_KEY;
    @Value("${currency.source-url}")
    private String CURRENCY_SOURCE_URL;

    private final RestTemplate restTemplate;

    public CurrencyLayer() {
        restTemplate = new RestTemplate();
    }

    public Currency callCurrency(CurrencyCountry targetCounty, CurrencyCountry sourceCountry) {
        String URL = MessageFormat.format(
            "{0}?access_key={1}&currencies={2}&source={3}",
            CURRENCY_SOURCE_URL,
            CURRENCY_ACCESS_KEY,
            targetCounty,
            sourceCountry
        );

        Currency currency = restTemplate.getForObject(URL, Currency.class);
        System.out.println("currency = " + currency);
        return currency;
    }

}
