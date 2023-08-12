package shopping.infrastructure;

import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomRestTemplate {

    private final RestTemplate restTemplate;

    public CustomRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public <T> Optional<T> getResult(String url, Class<T> clazz) {
        return Optional.ofNullable(doApiCall(url, clazz));
    }

    private <T> T doApiCall(String url, Class<T> clazz) {
        try {
            return restTemplate.getForObject(url, clazz);
        } catch (RestClientException exception) {
            return null;
        }
    }
}
