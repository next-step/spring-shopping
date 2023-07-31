package shopping.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import shopping.exception.ProductException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

@DisplayName("Product 클래스")
public class ProductTest {

    @Nested
    @DisplayName("new 생성자는")
    class Product_Constructor {

        @Test
        @DisplayName("Product 를 생성한다.")
        void createProduct() {
            // given
            String name = "마라샹궈";
            String url = "url";
            long price = 21000;

            // when
            Exception exception = catchException(() -> new Product(name, url, price));

            // then
            assertThat(exception).isNull();
        }

        @Test
        @DisplayName("Product name 이 null 이면 ProductException 을 던진다.")
        void throwProductException_whenNameIsNull() {
            // given
            String name = null;
            String url = "url";
            long price = 21000;

            // when
            Exception exception = catchException(() -> new Product(name, url, price));

            // then
            assertThat(exception).isInstanceOf(ProductException.class);
        }

        @Test
        @DisplayName("Product name 길이가 25보다 크면 ProductException 을 던진다.")
        void throwProductException_whenNameLengthIsOverThan25() {
            // given
            String name = "invalid".repeat(10);
            String url = "url";
            long price = 21000;

            // when
            Exception exception = catchException(() -> new Product(name, url, price));

            // then
            assertThat(exception).isInstanceOf(ProductException.class);
        }

        @Test
        @DisplayName("Product imageUrl 이 null 이면 ProductException 을 던진다.")
        void throwProductException_whenUrlIsNull() {
            // given
            String name = "치킨";
            String url = null;
            long price = 21000;

            // when
            Exception exception = catchException(() -> new Product(name, url, price));

            // then
            assertThat(exception).isInstanceOf(ProductException.class);
        }

        @Test
        @DisplayName("Product price 가 음수면 ProductException 을 던진다.")
        void throwProductException_whenPriceIsNegative() {
            // given
            String name = "치킨";
            String url = "url";
            long price = -1;

            // when
            Exception exception = catchException(() -> new Product(name, url, price));

            // then
            assertThat(exception).isInstanceOf(ProductException.class);
        }
    }
}
