package shopping.utils;

import java.text.MessageFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import shopping.domain.Currency;

@Component
public class CurrencyLayer {

    private static final String CURRENCY_ACCESS_KEY = "df19208b7579412eee5bae3132cad09b";
    private static final String CURRENCY_SOURCE = "USD";
    private static final String CURRENCY_TARGET = "KRW";
    private static final String CURRENCY_SOURCE_URL = "http://apilayer.net/api/live";

    private final RestTemplate restTemplate;

    public CurrencyLayer() {
        restTemplate = new RestTemplate();
    }

    public Currency callCurrency() {
        String URL = MessageFormat.format(
            "{0}?access_key={1}&currencies={2}&source={3}",
            CURRENCY_SOURCE_URL,
            CURRENCY_ACCESS_KEY,
            CURRENCY_TARGET,
            CURRENCY_SOURCE
        );

        Currency currency = restTemplate.getForObject(URL, Currency.class);
        System.out.println("currency = " + currency);
        return currency;
    }

}
