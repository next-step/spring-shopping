package shopping.mart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import shopping.core.exception.StatusCodeException;

@DisplayName("Product 클래스")
class ProductTest {

    @Nested
    @DisplayName("new 생성자는")
    class new_constructor {

        @Test
        @DisplayName("image url로 null이 들어올 경우, 기본 url이 설정된다.")
        void it_setting_default_image_when_input_null_image() {
            // given
            Product expected = new Product(1L, "product", "images/default-product.png", "5000");

            // when
            Product result = new Product(1L, "product", null, "5000");

            // then
            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("price로 음수가 들어올 경우 StatusCodeException을 던진다.")
        void throw_StatusCodeException_when_input_negative_price() {
            // given
            String expectedStatus = "PRODUCT-401";

            // when
            Exception exception = catchException(() -> new Product(1L, "product", null, "-5000"));

            // then
            assertStatusCodeException(exception, expectedStatus);
        }

        @Test
        @DisplayName("price로 정수가 아닌 값이 들어올 경우 StatusCodeException을 던진다.")
        void throw_StatusCodeException_when_input_not_number() {
            // given
            String expectedStatus = "PRODUCT-402";

            // when
            Exception exception = catchException(() -> new Product(1L, "product", null, "안녕^^"));

            // then
            assertStatusCodeException(exception, expectedStatus);
        }

        @Test
        @DisplayName("name에 null이 들어올경우, StatusCodeException을 던진다.")
        void throw_StatusCodeException_when_input_null_name() {
            // given
            String expectedStatus = "PRODUCT-405";

            // when
            Exception exception = catchException(() -> new Product(1L, null, "/default-product.png", "5000"));

            // then
            assertStatusCodeException(exception, expectedStatus);
        }

        @Test
        @DisplayName("name이 공백으로 들어올 경우, StatusCodeException을 던진다.")
        void throw_StatusCodeException_when_name_blank() {
            // given
            String expectedStatus = "PRODUCT-404";
            String blankName = "          ";

            // when
            Exception exception = catchException(() -> new Product(1L, blankName, "/default-product.png", "5000"));

            // then
            assertStatusCodeException(exception, expectedStatus);
        }

        @Test
        @DisplayName("name이 20자를 초과할 경우, StatusCodeException을 던진다.")
        void throw_StatusCodeException_when_name_exceed_20() {
            // given
            String expectedStatus = "PRODUCT-403";
            String exceedName = "123456789_123456789_1";

            // when
            Exception exception = catchException(() -> new Product(1L, exceedName, "/default-product.png", "5000"));

            // then
            assertStatusCodeException(exception, expectedStatus);
        }

        private void assertStatusCodeException(final Exception exception, final String expectedStatus) {
            assertThat(exception.getClass()).isEqualTo(StatusCodeException.class);
            assertThat(((StatusCodeException) exception).getStatus()).isEqualTo(expectedStatus);
        }
    }

}
