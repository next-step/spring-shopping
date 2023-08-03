package shopping.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import shopping.exception.CartProductException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

@DisplayName("Cart 클래스")
class CartProductTest {

    @Nested
    @DisplayName("new CartProduct 는")
    class Cart_Constructor {

        @Test
        @DisplayName("CartProduct 를 생성한다.")
        void createCartProduct() {
            // given
            Member member = new Member("home@naver.com", "1234");
            Product product = new Product("치킨", "image", 23000L);
            int quantity = 15;

            // when
            Exception exception = catchException(() -> new CartProduct(member, product, quantity));

            // then
            assertThat(exception).isNull();
        }

        @Test
        @DisplayName("수량이 0 이하이면 CartProductException 을 던진다.")
        void throwCartProductException_whenQuantityIsNotPositive() {
            // given
            Member member = new Member("home@naver.com", "1234");
            Product product = new Product("치킨", "image", 23000L);
            int invalidQuantity = 0;

            // when
            Exception exception = catchException(() -> new CartProduct(member, product, invalidQuantity));

            // then
            assertThat(exception).isInstanceOf(CartProductException.class);
        }


        @Test
        @DisplayName("Member 가 null 이면 CartProductException 을 던진다.")
        void throwCartProductException_whenMemberIsNull() {
            // given
            Member member = null;
            Product product = new Product("치킨", "image", 23000L);
            int quantity = 15;

            // when
            Exception exception = catchException(() -> new CartProduct(member, product, quantity));

            // then
            assertThat(exception).isInstanceOf(CartProductException.class);
        }


        @Test
        @DisplayName("Product 가 null 이면 CartProductException 을 던진다.")
        void throwCartProductException_whenProductIsNull() {
            // given
            Member member = new Member("home@naver.com", "1234");
            Product product = null;
            int quantity = 15;

            // when
            Exception exception = catchException(() -> new CartProduct(member, product, quantity));

            // then
            assertThat(exception).isInstanceOf(CartProductException.class);
        }
    }
}