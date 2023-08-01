package shopping.integration;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.dto.CartCreateRequest;
import shopping.dto.CartResponse;
import shopping.dto.CartUpdateRequest;
import shopping.dto.ErrorResponse;
import shopping.dto.LoginResponse;
import shopping.integration.config.IntegrationTest;
import shopping.integration.util.AuthUtil;
import shopping.integration.util.CartUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
public class CartIntegrationTest {

    @Test
    @DisplayName("장바구니 페이지 접속 테스트.")
    void loginPage() {
        RestAssured.given().log().all()
                .contentType(MediaType.TEXT_HTML_VALUE)
                .when().get("/cart")
                .then().statusCode(HttpStatus.OK.value())
                .log().all();
    }

    @Test
    @DisplayName("장바구니에 상품을 추가할 수 있다.")
    void addCart() {
        // given
        String accessToken = AuthUtil.login().as(LoginResponse.class).getAccessToken();

        // when, then
        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .body(new CartCreateRequest(1L))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/carts")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    @DisplayName("장바구니 상품 추가 시 productId는 null일 수 없다.")
    void addCartNullProductId() {
        // given
        String accessToken = AuthUtil.login().as(LoginResponse.class).getAccessToken();
        Map<String, Long> request = new HashMap<>();
        request.put("productId", null);

        // when
        ErrorResponse response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/carts")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().as(ErrorResponse.class);

        // then
        assertThat(response.getMessage()).isEqualTo("productId는 필수 항목입니다.");
    }

    @Test
    @DisplayName("장바구니 상품 추가 시 productId는 0일 수 없다.")
    void addCartZeroProductId() {
        // given
        String accessToken = AuthUtil.login().as(LoginResponse.class).getAccessToken();
        Map<String, Long> request = new HashMap<>();
        request.put("productId", 0L);

        // when
        ErrorResponse response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/carts")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().as(ErrorResponse.class);

        // then
        assertThat(response.getMessage()).isEqualTo("productId는 양의 정수입니다.");
    }

    @Test
    @DisplayName("장바구니 상품 추가 시 productId는 음수일 수 없다.")
    void addCartNegativeProductId() {
        // given
        String accessToken = AuthUtil.login().as(LoginResponse.class).getAccessToken();
        Map<String, Long> request = new HashMap<>();
        request.put("productId", -1L);

        // when
        ErrorResponse response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/carts")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().as(ErrorResponse.class);

        // then
        assertThat(response.getMessage()).isEqualTo("productId는 양의 정수입니다.");
    }

    @Test
    @DisplayName("장바구니 아이템 목록을 조회한다.")
    void findAllCartItems() {
        // given
        String accessToken = AuthUtil.login().as(LoginResponse.class).getAccessToken();

        CartUtil.createCartItem(accessToken, 1L);
        CartUtil.createCartItem(accessToken, 2L);

        // when
        CartResponse[] cartResponses = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/carts")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(CartResponse[].class);

        // then
        assertThat(Stream.of(cartResponses).map(CartResponse::getId)).containsAll(List.of(1L, 2L));
    }

    @Test
    @DisplayName("장바구니 수량을 변경할 수 있다.")
    void updateCartItemQuantity() {
        // given
        String accessToken = AuthUtil.login().as(LoginResponse.class).getAccessToken();
        CartUtil.createCartItem(accessToken, 1L);

        // when, then
        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .body(new CartUpdateRequest(3))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().patch("/carts/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("장바구니 항목 변경 시 수량이 0인 경우 오류를 반환한다.")
    void updateCartItemWithZeroQuantity() {
        // given
        String accessToken = AuthUtil.login().as(LoginResponse.class).getAccessToken();
        CartUtil.createCartItem(accessToken, 1L);
        Map<String, Integer> request = new HashMap<>();
        request.put("quantity", 0);

        // when, then
        ErrorResponse response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().patch("/carts/1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().as(ErrorResponse.class);

        // then
        assertThat(response.getMessage()).isEqualTo("장바구니 수량은 양의 정수입니다.");
    }

    @Test
    @DisplayName("장바구니 항목 변경 시 수량이 음수인 경우 오류를 반환한다.")
    void updateCartItemWithNegativeQuantity() {
        // given
        String accessToken = AuthUtil.login().as(LoginResponse.class).getAccessToken();
        CartUtil.createCartItem(accessToken, 1L);
        Map<String, Integer> request = new HashMap<>();
        request.put("quantity", -1);

        // when, then
        ErrorResponse response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .body(request)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().patch("/carts/1")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .extract().as(ErrorResponse.class);

        // then
        assertThat(response.getMessage()).isEqualTo("장바구니 수량은 양의 정수입니다.");
    }

    @Test
    @DisplayName("장바구니 수량을 삭제할 수 있다.")
    void deleteCartItem() {
        // given
        String accessToken = AuthUtil.login().as(LoginResponse.class).getAccessToken();
        CartUtil.createCartItem(accessToken, 1L);

        // when, then
        RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .when().delete("/carts/1")
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }
}
