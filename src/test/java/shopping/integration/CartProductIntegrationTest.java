package shopping.integration;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("장바구니 기능")
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

    @DisplayName("장바구니에 있는 상품을 조회한다")
    @Test
    void findCartProducts() {
        // when
        ExtractableResponse<Response> response = CartProductIntegrationSupporter.findCartProducts();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("장바구니에 있는 상품을 제거한다")
    @Test
    void deleteCartProduct() {
        // given
        Long cartProductId = 1L;

        // when
        ExtractableResponse<Response> response = CartProductIntegrationSupporter.deleteCartProduct(cartProductId);

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}
