package shopping.integration;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import shopping.integration.helper.CommonRestAssuredUtils;
import shopping.product.dto.request.ProductCreationRequest;
import shopping.product.dto.request.ProductUpdateRequest;
import shopping.product.dto.response.ProductResponse;

@DisplayName("상품 통합 테스트")
class ProductIntegrationTest extends IntegrationTest {

    @Test
    @DisplayName("전체 상품 리스트를 조회한다.")
    void findAllProduct() {
        // given
        createProduct(new ProductCreationRequest("피자", "15000", "imageUrl1"));
        createProduct(new ProductCreationRequest("치킨", "35000", "imageUrl2"));
        createProduct(new ProductCreationRequest("햄버거", "5000", "imageUrl3"));

        // when
        ExtractableResponse<Response> response = RestAssured.given().log().all()
            .when()
            .get("/")
            .then().log().all()
            .assertThat()
            .contentType(ContentType.HTML)
            .statusCode(200)
            .extract();

        // then
        String html = response.htmlPath().getString("body");
        assertThat(html).contains("피자", "치킨", "햄버거", "15000.00", "35000.00", "5000.00");
    }

    @Test
    @DisplayName("상품 하나를 조회한다.")
    void findOneProduct() {
        // given
        createProduct(new ProductCreationRequest("피자", "15000", "imageUrl1"));

        // when
        ExtractableResponse<Response> response = CommonRestAssuredUtils.get(
            "/products/{productId}",
            1
        );

        // then
        ProductResponse productResponse = response.body().as(ProductResponse.class);
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(productResponse).extracting("id").isEqualTo(1L);
    }

    @Test
    @DisplayName("상품 하나를 생성한다.")
    void createOneProduct() {

        // when
        ExtractableResponse<Response> response = createProduct(new ProductCreationRequest("name", "10000", "imageUrl"));

        // then
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private static ExtractableResponse<Response> createProduct(ProductCreationRequest productCreationRequest) {
        ExtractableResponse<Response> response = CommonRestAssuredUtils.post(
            "/products",
            productCreationRequest
        );
        return response;
    }

    @Test
    @DisplayName("상품 하나를 수정한다.")
    void updateOneProduct() {
        // given
        CommonRestAssuredUtils.post(
            "/products",
            new ProductCreationRequest("name", "10000", "imageUrl")
        );

        // when
        CommonRestAssuredUtils.put(
            "/products/1",
            new ProductUpdateRequest("updatedName", "100000", "updatedImageUrl")
        );

        // then
        ProductResponse productResponse = getProductResponse(1L);
        Assertions.assertThat(productResponse).extracting("name").isEqualTo("updatedName");
        Assertions.assertThat(productResponse).extracting("price").isEqualTo("100000.00");
        Assertions.assertThat(productResponse).extracting("imageUrl").isEqualTo("updatedImageUrl");
    }

    @Test
    @DisplayName("상품 하나를 삭제한다.")
    void deleteOneProduct() {
        // given
        CommonRestAssuredUtils.post(
            "/products",
            new ProductCreationRequest("name", "10000", "imageUrl")
        );

        // when
        CommonRestAssuredUtils.delete("/products/{productId}", 1L);

        // then
        ExtractableResponse<Response> response = CommonRestAssuredUtils.get(
            "/products/{productId}",
            1L
        );
        Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    private static ProductResponse getProductResponse(Long productId) {
        return CommonRestAssuredUtils.get(
            "/products/{productId}",
            productId
        ).body().as(ProductResponse.class);
    }
}
