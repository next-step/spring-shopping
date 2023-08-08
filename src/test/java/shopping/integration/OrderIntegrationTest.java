package shopping.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.dto.LoginResponse;
import shopping.integration.config.IntegrationTest;
import shopping.integration.util.AuthUtil;
import shopping.integration.util.CartUtil;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
public class OrderIntegrationTest {

    @Test
    @DisplayName("장바구니 데이터로 주문을 생성할 수 있다.")
    void createOrder() {
        // given
        String accessToken = AuthUtil.login().as(LoginResponse.class).getAccessToken();
        CartUtil.createCartItem(accessToken, 1L);
        CartUtil.createCartItem(accessToken, 1L);
        CartUtil.createCartItem(accessToken, 2L);

        // when
        String location = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/orders")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().header("Location");

        // then
        Long id = Long.parseLong(location.split("/")[2]);
        assertThat(id).isEqualTo(1L);
    }
}
