package shopping.integration;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.TestFixture;
import shopping.dto.request.CartItemAddRequest;
import shopping.dto.request.LoginRequest;
import shopping.dto.response.CartItemResponse;
import shopping.dto.response.LoginResponse;

@DisplayName("CartIntegrationTest")
class CartIntegrationTest extends IntegrationTest {

    @Override
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @Test
    @DisplayName("성공 : 장바구니에 아이템을 추가한다")
    void successAddItem() {
        /* given */
        final LoginRequest loginRequest = new LoginRequest("test_email@woowafriends.com",
            "test_password!");
        String accessToken = TestFixture.login(loginRequest).as(LoginResponse.class)
            .getAccessToken();

        final CartItemAddRequest cartItemAddRequest = new CartItemAddRequest(1L);

        /* when */
        final ExtractableResponse<Response> response = RestAssured
            .given().log().all()
            .auth().oauth2(accessToken)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(cartItemAddRequest)
            .when().post("/cart/items")
            .then().log().all()
            .extract();

        /* then */
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("성공 : 장바구니에 있는 아이템 목록을 조회한다")
    void successReadItem() {
        /* given */
        final LoginRequest loginRequest = new LoginRequest("test_email@woowafriends.com",
            "test_password!");
        String accessToken = TestFixture.login(loginRequest).as(LoginResponse.class)
            .getAccessToken();

        final CartItemAddRequest cartChicken = new CartItemAddRequest(1L);
        final CartItemAddRequest cartPizza = new CartItemAddRequest(2L);
        TestFixture.addCartItem(accessToken, cartChicken);
        TestFixture.addCartItem(accessToken, cartPizza);

        /* when */
        final ExtractableResponse<Response> response = RestAssured.given().log().all()
            .auth().oauth2(accessToken)
            .when().get("/cart/items")
            .then().log().all().extract();

        /* then */
        List<CartItemResponse> cartItems = response.jsonPath().getList(".", CartItemResponse.class);
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(cartItems).hasSize(2);
    }

}
