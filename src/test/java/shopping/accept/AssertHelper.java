package shopping.accept;

import static org.hamcrest.Matchers.hasItems;

import io.restassured.response.ValidatableResponse;
import org.springframework.http.HttpStatus;
import shopping.dto.ProductsGetResponse;

class AssertHelper {

    static final class Product {

        static void assertAllProducts(final ValidatableResponse response,
                final ProductsGetResponse productsGetResponse) {
            response.statusCode(HttpStatus.OK.value())
                    .body("products", hasItems(productsGetResponse.getProducts()));
        }
    }
}
