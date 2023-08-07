package shopping.integration;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("상품 조회 기능")
public class ProductIntegrationTest extends IntegrationTest {

    @DisplayName("상품 목록을 조회한다")
    @Test
    void findProducts() {
        // when
        ExtractableResponse<Response> response = ProductIntegrationSupporter.findProducts();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
