package shopping.infrastructure;

import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomRestTemplate {

    private final RestTemplate restTemplate;
    private final ExceptionLogger logger;

    public CustomRestTemplate(RestTemplate restTemplate, ExceptionLogger logger) {
        this.restTemplate = restTemplate;
        this.logger = logger;
    }

    public <T> Optional<T> getResult(String url, Class<T> clazz) {
        return Optional.ofNullable(doApiCall(url, clazz));
    }

    private <T> T doApiCall(String url, Class<T> clazz) {
        try {
            return restTemplate.getForObject(url, clazz);
        } catch (RestClientException exception) {
            logger.logException(exception);
            return null;
        }
    }
}
