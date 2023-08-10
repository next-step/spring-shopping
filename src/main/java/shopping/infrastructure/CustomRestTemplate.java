package shopping.infrastructure;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import shopping.exception.InfraException;

@Component
public class CustomRestTemplate {

    private final RestTemplate restTemplate = new RestTemplate();

    public <T> T getResult(String url, Class<T> clazz) {
        ResponseEntity<T> response = doApiCall(url, clazz);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new InfraException("외부 API 호출 중 에러가 발생했습니다. - " + url);
        }

        return response.getBody();
    }

    private <T> ResponseEntity<T> doApiCall(String url, Class<T> clazz) {
        try {
            return restTemplate.getForEntity(url, clazz);
        } catch (RestClientException exception) {
            throw new InfraException("외부 API 호출 중 에러가 발생했습니다");
        }
    }
}
