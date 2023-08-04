package shopping.app.accept;

import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.app.accept.UrlHelper.Product;

@DisplayName("Product 인수테스트")
class ProductAcceptanceTest extends AcceptanceTest {

    @Test
    @DisplayName("GET / API 는 상품 리스트를 반환한다.")
    void return_products() {
        // when
        ValidatableResponse response = Product.getAllProducts();

        // then
        AssertHelper.Product.assertAllProducts(response, "소주", "맥주", "막걸리");
    }
}
