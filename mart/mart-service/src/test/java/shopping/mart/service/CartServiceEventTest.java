package shopping.mart.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shopping.mart.app.api.cart.event.CartClearEvent;

@ExtendWith(SpringExtension.class)
@DisplayName("CartService 클래스 - Event")
public class CartServiceEventTest {

    @MockBean
    private CartService cartService;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Nested
    @DisplayName("clearCart 메소드는")
    class clearCart_method {

        @Test
        @DisplayName("CartOrderedEvent가 발행되면, 호출된다.")
        void called_when_publish_cart_ordered_event() {
            // given
            CartClearEvent cartClearEvent = new CartClearEvent(1L);

            // when
            applicationEventPublisher.publishEvent(cartClearEvent);

            // then
            Mockito.verify(cartService).clearCart(cartClearEvent);
        }
    }
}
