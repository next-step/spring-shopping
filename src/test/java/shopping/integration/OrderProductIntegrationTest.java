package shopping.integration;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("상품 주문 기능")
public class OrderProductIntegrationTest extends IntegrationTest {

    @DisplayName("장바구니에 있는 상품들을 주문한다")
    @Test
    void orderProduct() {
        // given && when
        ExtractableResponse<Response> response = OrderProductIntegrationSupporter.orderProduct();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.SC_OK);
    }

}
