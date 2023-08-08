package shopping.order.app.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import shopping.mart.app.domain.Cart;
import shopping.order.app.domain.exception.EmptyCartException;

@DisplayName("Order 클래스")
class OrderTest {

    @Nested
    @DisplayName("new 생성자는")
    class new_constructor {

        @Test
        @DisplayName("cart를 받아, 총 결제금액이 계산된 order를 생성한다.")
        void receive_cart_and_create_order_with_price_calculated() {
            // given
            Cart cart = DomainFixture.Cart.defaultCart();
            String expected = String.valueOf(100 * 1000);

            // when
            Order result = new Order(cart);

            // then
            assertThat(result.getTotalPrice()).isEqualTo(expected);
        }

        @Test
        @DisplayName("cart가 null 이라면, EmptyCartException을 던진다.")
        void throw_empty_cart_exception_when_input_null_cart() {
            // given
            Cart cart = null;

            // when
            Exception exception = catchException(() -> new Order(cart));

            // then
            assertThat(exception).isInstanceOf(EmptyCartException.class);
        }

        @Test
        @DisplayName("cart에 어떠한 아이템도 없다면, EmptyCartException을 던진다.")
        void throw_empty_cart_exception_when_input_empty_cart() {
            // given
            Cart cart = new Cart(1L, 1L);

            // when
            Exception exception = catchException(() -> new Order(cart));

            // then
            assertThat(exception).isInstanceOf(EmptyCartException.class);
        }
    }
}
