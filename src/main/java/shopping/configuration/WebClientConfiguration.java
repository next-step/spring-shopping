package shopping.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    private static final String API_BASE_URL =
            "http://api.currencylayer.com/live?currencies=KRW&access_key=";

    @Value("${secret.currency.access_key}")
    private String API_ACCESS_KEY;

    @Bean
    public WebClient webClient() {
        return WebClient.create(API_BASE_URL + API_ACCESS_KEY);
    }
}
