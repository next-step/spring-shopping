package shopping.integration;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.domain.cart.Price;
import shopping.domain.cart.Product;
import shopping.dto.request.CartItemCreateRequest;
import shopping.dto.request.LoginRequest;
import shopping.dto.response.OrderItemResponse;
import shopping.dto.response.OrderResponse;
import shopping.repository.ProductRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;

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
        assertThat(response.as(OrderResponse.class).getTotalPrice())
                .isEqualTo(productList.stream()
                        .map(Product::getPrice)
                        .mapToLong(Price::getPrice)
                        .sum());
        assertThat(response.as(OrderResponse.class).getItems())
                .extracting(OrderItemResponse::getName)
                .containsExactlyInAnyOrder("치킨", "피자", "샐러드");
    }

    @DisplayName("주문 세부 내역 조회 성공시 200 Ok")
    @Test
    void getOrderDetailSuccess() {
        // given
        String accessToken = login();

        List<Product> productList = List.of(
                new Product("치킨", "/chicken.jpg", 10_000L),
                new Product("피자", "/pizza.jpg", 20_000L),
                new Product("샐러드", "/salad.jpg", 5_000L)
        );
        List<Product> savedProducts = productRepository.saveAll(productList);
        savedProducts.forEach(product -> addCartItem(new CartItemCreateRequest(product.getId()), accessToken));
        OrderResponse orderResponse = createOrdeer(accessToken);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/order/{:id}", orderResponse.getId())
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.as(OrderResponse.class).getTotalPrice()).isEqualTo(orderResponse.getTotalPrice());
        assertThat(response.as(OrderResponse.class).getItems())
                .extracting(OrderItemResponse::getName)
                .containsExactlyInAnyOrder("치킨", "피자", "샐러드");
    }

    private OrderResponse createOrdeer(String accessToken) {
        return RestAssured.given().auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/order")
                .then().extract().as(OrderResponse.class);
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
