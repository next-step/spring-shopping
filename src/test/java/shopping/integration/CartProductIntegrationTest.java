package shopping.integration;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("상품 추가 기능")
public class CartProductIntegrationTest extends IntegrationTest {

    @DisplayName("장바구니에 상품을 추가한다")
    @Test
    void addProduct() {
        // given
        Long memberId = 1L;
        Long productId = 5L;

        // when
        ExtractableResponse<Response> response = CartProductIntegrationSupporter.addProduct(memberId, productId);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());

    }
}
