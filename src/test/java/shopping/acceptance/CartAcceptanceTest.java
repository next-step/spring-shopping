package shopping.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.dto.request.CartProductRequest;

class CartAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("장바구니 상품을 추가한다.")
    void createCartProduct() {
        /* given */
        final Long memberId = 1L;
        final CartProductRequest request = new CartProductRequest(1L);

        /* when */
        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header("memberId", memberId)
            .body(request)
            .when().post("/cart")
            .then().log().all()
            .extract();

        /* then */
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}
