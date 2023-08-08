package shopping.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.acceptance.helper.AuthHelper;
import shopping.acceptance.helper.RestHelper;
import shopping.cart.dto.response.CartResponse;

public class OrderAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("사용자가 장바구니의 상품을 주문한다")
    void 사용자가_장바구니의_상품을_주문_한다() {
        // given
        final String jwt = AuthHelper.login("woowacamp@naver.com", "woowacamp");
        // when
        ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON_VALUE)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
            .when().post("/api/order")
            .then().log().all()
            .extract();
        final ExtractableResponse<Response> cartProductResponse = RestHelper.get("/api/cartProduct",
            jwt);
        // then
        assertThat(cartProductResponse.body().as(new TypeRef<List<CartResponse>>() {}))
            .hasSize(0);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }
}