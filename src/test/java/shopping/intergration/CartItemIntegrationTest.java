package shopping.intergration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.dto.request.CartItemAddRequest;
import shopping.dto.response.ProductResponse;
import shopping.intergration.helper.LoginHelper;

class CartItemIntegrationTest extends IntegrationTest {

    private static final String EMAIL = "yongs170@naver.com";
    private static final String PASSWORD = "123!@#asd";

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void addProductToCart() {
        final String accessToken = LoginHelper.login(EMAIL, PASSWORD);
        final Long productId = 1L;

        final ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .body(new CartItemAddRequest(productId))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/cart-items")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThatCode(() -> response.jsonPath().getLong("cartItemId")).doesNotThrowAnyException();
        final ProductResponse product = response.jsonPath().getObject("product", ProductResponse.class);
        assertThat(product)
                .extracting("id", "name", "image", "price")
                .contains(productId, "Chicken", "/image/Chicken.png", 10_000);
    }
}
