package shopping.utils;

import java.text.MessageFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import shopping.domain.CurrencyCountry;
import shopping.dto.request.CurrencyRequest;
import shopping.service.CurrencyService;

@Service
public class CurrencyLayer implements CurrencyService {

    @Value("${currency.access-key}")
    private String CURRENCY_ACCESS_KEY;
    @Value("${currency.source-url}")
    private String CURRENCY_SOURCE_URL;

    private final RestTemplate restTemplate;

    public CurrencyLayer() {
        restTemplate = new RestTemplate();
    }

    @Override
    public CurrencyRequest callCurrency(CurrencyCountry currencyCountry) {
        String URL = MessageFormat.format(
            "{0}?access_key={1}&currencies={2}&source={3}",
            CURRENCY_SOURCE_URL,
            CURRENCY_ACCESS_KEY,
            currencyCountry.getTargetCurrency(),
            currencyCountry.getSourceCurrency()
        );

        return restTemplate.getForObject(URL, CurrencyRequest.class);
    }

}
