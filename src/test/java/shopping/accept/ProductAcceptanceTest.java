package shopping.accept;

import io.restassured.response.ValidatableResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import shopping.accept.UrlHelper.Product;
import shopping.dto.ProductsGetResponse;
import shopping.dto.ProductsGetResponse.ProductElement;

@DisplayName("Product 인수테스트")
class ProductAcceptanceTest extends AcceptanceTest {

    private ProductsGetResponse.ProductElement getSozu(ValidatableResponse response) {
        long id = response.extract().body().as(ProductsGetResponse.class)
                .getProducts().stream()
                .filter(productElement -> productElement.getName().equals("소주"))
                .map(ProductElement::getId)
                .findFirst()
                .orElseThrow();

        return new ProductElement(id, "소주", "/sozu.png", "5000");
    }

    private ProductsGetResponse.ProductElement getBeer(ValidatableResponse response) {
        long id = response.extract().body().as(ProductsGetResponse.class)
                .getProducts().stream()
                .filter(productElement -> productElement.getName().equals("맥주"))
                .map(ProductElement::getId)
                .findFirst()
                .orElseThrow();
        return new ProductElement(id, "맥주", "/beer.png", "5500");
    }

    private ProductsGetResponse.ProductElement getMakgeolli(ValidatableResponse response) {
        long id = response.extract().body().as(ProductsGetResponse.class)
                .getProducts().stream()
                .filter(productElement -> productElement.getName().equals("막걸리"))
                .map(ProductElement::getId)
                .findFirst()
                .orElseThrow();
        return new ProductElement(id, "막걸리", "/makgeolli.png", "6000");
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
                    new ProductsGetResponse(List.of(getSozu(response), getBeer(response), getMakgeolli(response))));
        }
    }
}
