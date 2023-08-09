package shopping.infrastructure;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import shopping.exception.InfraException;

public class CustomRestTemplate {

    private final RestTemplate restTemplate = new RestTemplate();

    public <T> T getResult(String url, Class<T> clazz) {
        ResponseEntity<T> response = restTemplate.getForEntity(url, clazz);

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new InfraException("에러가 발생했습니다.");
        }

        return response.getBody();
    }
}
