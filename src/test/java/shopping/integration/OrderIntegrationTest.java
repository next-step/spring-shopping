package shopping.integration;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import shopping.domain.cart.Price;
import shopping.domain.cart.Product;
import shopping.domain.user.User;
import shopping.dto.request.CartItemCreateRequest;
import shopping.dto.request.LoginRequest;
import shopping.dto.response.OrderItemResponse;
import shopping.dto.response.OrderResponse;
import shopping.repository.ProductRepository;
import shopping.repository.UserRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("주문 인수 테스트")
class OrderIntegrationTest extends IntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

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
        OrderResponse orderResponse = createOrder(accessToken);

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

    @DisplayName("타인의 주문 세부 내역 조회시 403 Forbidden")
    @Test
    void getOthersOrderDetail() {
        // given
        String accessToken = login();
        String otherAccess = otherLogin();

        List<Product> productList = List.of(
                new Product("치킨", "/chicken.jpg", 10_000L),
                new Product("피자", "/pizza.jpg", 20_000L),
                new Product("샐러드", "/salad.jpg", 5_000L)
        );
        List<Product> savedProducts = productRepository.saveAll(productList);
        savedProducts.forEach(product -> addCartItem(new CartItemCreateRequest(product.getId()), accessToken));
        OrderResponse orderResponse = createOrder(accessToken);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(otherAccess)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/order/{:id}", orderResponse.getId())
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("주문 전체 내역 조회 성공시 200 Ok")
    @Test
    void getOrderSuccess() {
        // given
        String accessToken = login();

        OrderResponse orderResponse = createProductAndOrder(accessToken);
        OrderResponse orderResponse2 = createProductAndOrder(accessToken);

        // when
        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .auth().oauth2(accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/order/")
                .then().log().all()
                .extract();

        // then
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        List<OrderResponse> orderResponses = response.jsonPath().getList(".", OrderResponse.class);
        assertThat(orderResponses).hasSize(2);
        assertThat(orderResponses.get(0)).usingRecursiveComparison().ignoringFieldsOfTypes(Double.class).isEqualTo(orderResponse);
        assertThat(orderResponses.get(1)).usingRecursiveComparison().ignoringFieldsOfTypes(Double.class).isEqualTo(orderResponse2);
        assertThat(orderResponses.get(0).getExchangePrice()).isCloseTo(orderResponse.getExchangePrice(), Offset.offset(1.0));
        assertThat(orderResponses.get(1).getExchangePrice()).isEqualTo(orderResponse2.getExchangePrice());
    }

    private OrderResponse createProductAndOrder(String accessToken) {
        List<Product> productList = List.of(
                new Product("치킨", "/chicken.jpg", 10_000L),
                new Product("피자", "/pizza.jpg", 20_000L),
                new Product("샐러드", "/salad.jpg", 5_000L)
        );
        List<Product> savedProducts = productRepository.saveAll(productList);
        savedProducts.forEach(product -> addCartItem(new CartItemCreateRequest(product.getId()), accessToken));
        return createOrder(accessToken);
    }

    private OrderResponse createOrder(String accessToken) {
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

    private String otherLogin() {
        String email = "other@example.com";
        String password = "12345";
        userRepository.save(new User(email, password));

        return RestAssured
                .given()
                .body(new LoginRequest(email, password))
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
