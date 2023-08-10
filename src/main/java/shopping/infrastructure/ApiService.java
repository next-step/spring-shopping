package shopping.infrastructure;

import java.time.Duration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import shopping.exception.ExternalApiException;

@Service
public class ApiService {

    private final RestTemplate restTemplate;

    public ApiService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                                .setConnectTimeout(Duration.ofSeconds(5))
                                .setReadTimeout(Duration.ofSeconds(5))
                                .build();
    }

    public <T> T getResult(String url, Class<T> clazz) {
        ResponseEntity<T> response = callExternalApi(url, clazz);
        if (response.getStatusCode().isError()) {
            throw new ExternalApiException("외부 API 호출 과정에서 오류가 발생했습니다");
        }

        return response.getBody();
    }

    private <T> ResponseEntity<T> callExternalApi(String url, Class<T> clazz) {
        try {
            return restTemplate.getForEntity(url, clazz);
        } catch (Exception exception) {
            throw new ExternalApiException("외부 API 호출 과정에서 오류가 발생했습니다");
        }
    }
}
