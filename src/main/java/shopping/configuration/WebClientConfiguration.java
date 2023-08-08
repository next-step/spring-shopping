package shopping.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    @Value("${secret.currency.url}")
    private String API_BASE_URL;

    @Value("${secret.currency.access_key}")
    private String API_ACCESS_KEY;

    @Bean
    public WebClient webClient() {
        return WebClient.create(API_BASE_URL + API_ACCESS_KEY);
    }
}
