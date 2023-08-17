package shopping.infrastructure;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomRestTemplate {

    private final RestTemplate restTemplate;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
            logger.error("api 호출 예외 발생: {}", exception.getMessage(), exception);
            return null;
        }
    }
}
