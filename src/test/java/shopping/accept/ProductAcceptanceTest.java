package shopping.accept;

import io.restassured.response.ValidatableResponse;
import java.math.BigInteger;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import shopping.accept.AssertHelper.ProductGetResponse;
import shopping.accept.AssertHelper.ProductGetResponse.ProductElement;
import shopping.accept.UrlHelper.Product;

@DisplayName("Product 인수테스트")
class ProductAcceptanceTest extends AcceptanceTest {

    private ProductGetResponse.ProductElement getSozu(ValidatableResponse response) {
        long id = response.extract().body().as(ProductGetResponse.class)
                .getProducts().stream()
                .filter(productElement -> productElement.getName().equals("소주"))
                .map(ProductElement::getId)
                .findFirst()
                .orElseThrow();
        return new ProductElement(id, "소주", "/sozu.png", new BigInteger("5000"));
    }

    private ProductGetResponse.ProductElement getBeer(ValidatableResponse response) {
        long id = response.extract().body().as(ProductGetResponse.class)
                .getProducts().stream()
                .filter(productElement -> productElement.getName().equals("맥주"))
                .map(ProductElement::getId)
                .findFirst()
                .orElseThrow();
        return new ProductElement(id, "맥주", "/beer.png", new BigInteger("5500"));
    }

    private ProductGetResponse.ProductElement getMakgeolli(ValidatableResponse response) {
        long id = response.extract().body().as(ProductGetResponse.class)
                .getProducts().stream()
                .filter(productElement -> productElement.getName().equals("막걸리"))
                .map(ProductElement::getId)
                .findFirst()
                .orElseThrow();
        return new ProductElement(id, "막걸리", "/makgeolli.png", new BigInteger("6000"));
    }

    @Nested
    @DisplayName("GET /products API 는")
    class get_all_products {

        @Test
        @DisplayName("상품 리스트를 반환한다.")
        void return_products() {
            // when
            ValidatableResponse response = Product.getAllProducts();

            // then
            AssertHelper.Product.assertAllProducts(response,
                    new ProductGetResponse(List.of(getSozu(response), getBeer(response), getMakgeolli(response))));
        }
    }
}
