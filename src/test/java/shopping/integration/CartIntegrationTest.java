package shopping.integration;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.dto.request.CartItemCreateRequest;
import shopping.dto.request.CartItemUpdateRequest;
import shopping.dto.request.LoginRequest;
import shopping.dto.response.CartItemResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("장바구니 인수 테스트")
class CartIntegrationTest extends IntegrationTest {

    @DisplayName("장바구니에 상품 추가 성공시 201 Created")
    @Test
    void addCartItem() {
        // given
        CartItemCreateRequest cartItemCreateRequest = new CartItemCreateRequest(1L);
        String accessToken = RestAssured
                .given().log().all()
                .body(new LoginRequest("admin@example.com", "123456789"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login/token")
                .then().log().all()
                .extract().jsonPath().getString("accessToken");

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartItemCreateRequest)
                .when().post("/cart/items")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @DisplayName("장바구니 물건 조회 성공시 200 Ok")
    @Test
    void getCartItems() {
        // given
        CartItemCreateRequest cartItemCreateRequest = new CartItemCreateRequest(1L);
        String accessToken = RestAssured
                .given().log().all()
                .body(new LoginRequest("admin@example.com", "123456789"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login/token")
                .then().log().all()
                .extract().jsonPath().getString("accessToken");

        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartItemCreateRequest)
                .when().post("/cart/items")
                .then().log().all();

        // when, then
        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/cart")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("장바구니 물건 수량 변경 성공시 200 Ok")
    @Test
    void updateCartItemsQuantity() {
        // given
        CartItemCreateRequest cartItemCreateRequest = new CartItemCreateRequest(1L);
        String accessToken = RestAssured
                .given().log().all()
                .body(new LoginRequest("admin@example.com", "123456789"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login/token")
                .then().log().all()
                .extract().jsonPath().getString("accessToken");

        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartItemCreateRequest)
                .when().post("/cart/items")
                .then().log().all();

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/cart/items")
                .then().log().all()
                .extract();
        List<Long> cartItemIds = response.jsonPath()
                .getList(".", CartItemResponse.class)
                .stream()
                .map(CartItemResponse::getId)
                .collect(Collectors.toList());

        // when
        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new CartItemUpdateRequest(3))
                .when().patch("/cart/items/{:id}", cartItemIds.get(0))
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        // then
        ExtractableResponse<Response> result = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/cart/items")
                .then().log().all()
                .extract();
        List<CartItemResponse> cartItemResponses = result.jsonPath()
                .getList(".", CartItemResponse.class);

        assertThat(cartItemResponses.get(0).getQuantity()).isEqualTo(3);
    }

    @DisplayName("장바구니 물건 수량 변경시 수량이 양수가 아니면 400 BadRequest")
    @Test
    void updateCartItemsQuantityNotPositiveQuantity() {
        // given
        CartItemCreateRequest cartItemCreateRequest = new CartItemCreateRequest(1L);
        String accessToken = RestAssured
                .given().log().all()
                .body(new LoginRequest("admin@example.com", "123456789"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login/token")
                .then().log().all()
                .extract().jsonPath().getString("accessToken");

        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartItemCreateRequest)
                .when().post("/cart/items")
                .then().log().all();

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/cart/items")
                .then().log().all()
                .extract();
        List<Long> cartItemIds = response.jsonPath()
                .getList(".", CartItemResponse.class)
                .stream()
                .map(CartItemResponse::getId)
                .collect(Collectors.toList());

        Map<String, Object> params = new HashMap<>();
        params.put("quantity", 0);

        // when, then
        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().patch("/cart/items/{:id}", cartItemIds.get(0))
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("장바구니 물건 삭제 성공시 204 No content")
    @Test
    void deleteCartItem() {
        // given
        CartItemCreateRequest cartItemCreateRequest = new CartItemCreateRequest(1L);
        String accessToken = RestAssured
                .given().log().all()
                .body(new LoginRequest("admin@example.com", "123456789"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login/token")
                .then().log().all()
                .extract().jsonPath().getString("accessToken");

        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartItemCreateRequest)
                .when().post("/cart/items")
                .then().log().all();

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/cart/items")
                .then().log().all()
                .extract();
        List<Long> cartItemIds = response.jsonPath()
                .getList(".", CartItemResponse.class)
                .stream()
                .map(CartItemResponse::getId)
                .collect(Collectors.toList());

        // when
        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/cart/items/{:id}", cartItemIds.get(0))
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());

        // then
        ExtractableResponse<Response> result = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/cart/items")
                .then().log().all()
                .extract();
        int length = result.jsonPath()
                .getList(".", CartItemResponse.class).size();

        assertThat(length).isEqualTo(cartItemIds.size() - 1);
    }
}
