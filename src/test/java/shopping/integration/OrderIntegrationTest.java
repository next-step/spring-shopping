package shopping.integration;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.domain.cart.Product;
import shopping.dto.request.CartItemCreateRequest;
import shopping.dto.request.LoginRequest;
import shopping.repository.ProductRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderIntegrationTest extends IntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("주문 요청 성공시 201 Created")
    @Test
    void createOrderSuccess() {
        // given
        String accessToken = login();

        List<Product> productList = List.of(
                new Product("치킨", "/chicken.jpg", 10_000L),
                new Product("피자", "/pizza.jpg", 20_000L),
                new Product("샐러드", "/salad.jpg", 5_000L)
        );
        List<Product> savedProducts = productRepository.saveAll(productList);
        savedProducts.forEach(product -> addCartItem(new CartItemCreateRequest(product.getId()), accessToken));

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/order")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    private String login() {
        return RestAssured
                .given()
                .body(new LoginRequest("admin@example.com", "123456789"))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/login/token")
                .then()
                .extract().jsonPath().getString("accessToken");
    }

    private void addCartItem(CartItemCreateRequest cartItemCreateRequest, String accessToken) {
        RestAssured
                .given()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(cartItemCreateRequest)
                .when().post("/cart/items")
                .then();
    }
}
