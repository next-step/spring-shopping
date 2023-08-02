package shopping.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import shopping.domain.exception.StatusCodeException;

@DisplayName("Cart 클래스")
class CartTest {

    private void assertStatusCodeException(final Exception exception, final String expectedStatus) {
        assertThat(exception.getClass()).isEqualTo(StatusCodeException.class);
        assertThat(((StatusCodeException) exception).getStatus()).isEqualTo(expectedStatus);
    }

    private void assertCartProduct(Map<Product, Integer> result, Map<Product, Integer> expected) {
        assertThat(result).isEqualTo(expected);
    }

    @Nested
    @DisplayName("addProduct 메소드는")
    class addProduct_method {

        @Test
        @DisplayName("product가 주어지면 cart에 product를 추가한다.")
        void add_product_to_cart() {
            // given
            Cart cart = new Cart(0L, 1L);
            Product product = new Product("맥주", "images/default-product.png", "4500");

            // when
            Exception exception = catchException(() -> cart.addProduct(product));

            // then
            assertThat(exception).isNull();
        }

        @Test
        @DisplayName("product가 null로 주어지면, StatusCodeException을 던진다.")
        void throw_StatusCodeException_when_input_null_product() {
            // given
            Cart cart = new Cart(0L, 1L);
            Product nullProduct = null;

            // when
            Exception exception = catchException(() -> cart.addProduct(nullProduct));

            // then
            assertThat(exception).isInstanceOf(IllegalStateException.class);
        }

        @Test
        @DisplayName("이미 저장된 product가 들어오면, StatusCodeException을 던진다.")
        void throw_StatusCodeException_when_input_exists_product() {
            // given
            Cart cart = new Cart(0L, 1L);
            Product product = new Product("맥주", "images/default-product.png", "4500");

            cart.addProduct(product);

            // when
            Exception exception = catchException(() -> cart.addProduct(product));

            // then
            assertStatusCodeException(exception, "CART-404");
        }
    }

    @Nested
    @DisplayName("updateProduct 메소드는")
    class updateProduct_method {

        @Test
        @DisplayName("update할 product와, 수량을 입력받으면, product의 수량을 변경한다.")
        void update_product() {
            // given
            Cart cart = new Cart(1L, 1L);
            Product product = new Product("맥주", "images/default-product.png", "4500");

            cart.addProduct(product);

            // when
            cart.updateProduct(product, 100);
            Map<Product, Integer> result = cart.getProductCounts();

            // then
            assertCartProduct(result, Map.of(product, 100));
        }

        @Test
        @DisplayName("update할 product와, 수량으로 양수가 아닌 수를 입력받으면 StatusCodeException을 던진다.")
        void throw_StatusCodeException_when_not_positive_count() {
            // given
            Cart cart = new Cart(1L, 1L);
            Product product = new Product("맥주", "images/default-product.png", "4500");

            cart.addProduct(product);

            // when
            Exception exception = catchException(() -> cart.updateProduct(product, 0));

            // then
            assertStatusCodeException(exception, "CART-403");
        }

        @Test
        @DisplayName("update할 product가 존재하지 않으면 StatusCodeException을 던진다.")
        void throw_StatusCodeException_when_not_exists_product() {
            // given
            Cart cart = new Cart(1L, 1L);
            Product product = new Product("맥주", "images/default-product.png", "4500");

            // when
            Exception exception = catchException(() -> cart.updateProduct(product, 1));

            // then
            assertStatusCodeException(exception, "CART-402");
        }

        private void assertCartProduct(Map<Product, Integer> result, Map<Product, Integer> expected) {
            assertThat(result).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("deleteProduct 메소드는")
    class deleteProduct_method {

        @Test
        @DisplayName("delete할 product가 존재한다면, product를 삭제한다.")
        void delete_product() {
            // given
            Cart cart = new Cart(1L, 1L);
            Product product = new Product("맥주", "images/default-product.png", "4500");

            cart.addProduct(product);

            Map<Product, Integer> expected = Map.of();

            // when
            cart.deleteProduct(product);
            Map<Product, Integer> result = cart.getProductCounts();

            // then
            assertCartProduct(result, expected);
        }

        @Test
        @DisplayName("delete할 product가 존재하지 않는다면, IllegalStateException을 던진다.")
        void throw_IllegalStateException_when_null_product_input() {
            // given
            Cart cart = new Cart(1L, 1L);
            Product nullProduct = null;

            // when
            Exception exception = catchException(() -> cart.deleteProduct(nullProduct));

            // then
            assertThat(exception).isInstanceOf(IllegalStateException.class);
        }

        @Test
        @DisplayName("delete할 product를 찾을 수 없다면, StatusCodeException을 던진다.")
        void throw_StatusCodeException_when_not_exists_product() {
            // given
            Cart cart = new Cart(1L, 1L);
            Product product = new Product("맥주", "images/default-product.png", "4500");

            // when
            Exception exception = catchException(() -> cart.deleteProduct(product));

            // then
            assertStatusCodeException(exception, "CART-401");
        }
    }
}
