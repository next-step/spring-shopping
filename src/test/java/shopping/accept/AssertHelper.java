package shopping.accept;

import static org.hamcrest.core.IsIterableContaining.hasItems;

import io.restassured.response.ValidatableResponse;
import java.math.BigInteger;
import java.util.List;
import org.springframework.http.HttpStatus;

class AssertHelper {

    static final class Product {

        static void assertAllProducts(final ValidatableResponse response,
                final ProductGetResponse productGetResponse) {
            response.statusCode(HttpStatus.OK.value())
                    .body("products", hasItems(productGetResponse.getProducts()));
        }
    }

    public static final class ProductGetResponse {

        private final List<ProductElement> products;

        public ProductGetResponse(final List<ProductElement> products) {
            this.products = products;
        }

        public List<ProductElement> getProducts() {
            return products;
        }

        public static final class ProductElement {

            private final Long id;
            private final String name;
            private final String imageUrl;
            private final BigInteger price;

            public ProductElement(final Long id, final String name, final String imageUrl, final BigInteger price) {
                this.id = id;
                this.name = name;
                this.imageUrl = imageUrl;
                this.price = price;
            }

            public Long getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public String getImageUrl() {
                return imageUrl;
            }

            public BigInteger getPrice() {
                return price;
            }
        }
    }
}
