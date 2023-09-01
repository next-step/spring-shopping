package shopping.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.domain.cart.Cart;
import shopping.domain.cart.CartProduct;

@DisplayName("OrderMapper 단위 테스트")
class OrderMapperTest {

    OrderMapper orderMapper;
    Cart cart;


    @BeforeEach
    void setUp() {
        orderMapper = new OrderMapper();

        final CartProduct cartProduct1 = mock(CartProduct.class);
        final CartProduct cartProduct2 = mock(CartProduct.class);
        when(cartProduct1.getProductPrice()).thenReturn(100);
        when(cartProduct2.getProductPrice()).thenReturn(200);
        when(cartProduct1.getProductImage()).thenReturn("url1");
        when(cartProduct2.getProductImage()).thenReturn("url2");
        when(cartProduct1.getProductName()).thenReturn("cp1");
        when(cartProduct2.getProductName()).thenReturn("cp2");
        when(cartProduct1.getQuantity()).thenReturn(1);
        when(cartProduct2.getQuantity()).thenReturn(2);

        cart = new Cart(1L, List.of(cartProduct1, cartProduct2));
    }

    @Test
    @DisplayName("Cart를 Order로 매핑한다.")
    void mapFromCart() {
        /* given */

        /* when & then */
        assertThatCode(() -> orderMapper.mapFrom(cart))
            .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Cart와 환율을 Order로 매핑한다.")
    void mapOfCartAndRate() {
        /* given */
        final double rate = 1234.5;

        /* when & then */
        assertThatCode(() -> orderMapper.mapOf(cart, rate))
            .doesNotThrowAnyException();
    }
}
