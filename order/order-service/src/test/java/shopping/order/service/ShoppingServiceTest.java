package shopping.order.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shopping.mart.app.api.cart.event.CartClearEvent;
import shopping.mart.app.spi.CartRepository;
import shopping.order.app.api.OrderUseCase;
import shopping.order.app.spi.ReceiptRepository;

@RecordApplicationEvents
@ExtendWith(SpringExtension.class)
@DisplayName("ShoppingService 클래스")
@ContextConfiguration(classes = OrderService.class)
class ShoppingServiceTest {

    @Autowired
    private OrderUseCase orderUseCase;

    @Autowired
    private ApplicationEvents applicationEvents;

    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private ReceiptRepository receiptRepository;

    @Nested
    @DisplayName("order 메소드는")
    class order_method {

        @Test
        @DisplayName("cartId를 받아서, Cart의 product를 주문 한다")
        void order_cart_items_when_receive_cart_id() {
            // given
            long cartId = 1L;

            when(cartRepository.getById(cartId)).thenReturn(DomainFixture.Cart.defaultCart());

            // when
            orderUseCase.order(cartId);

            // then
            assertThat(applicationEvents.stream(CartClearEvent.class).count()).isEqualTo(1L);
        }
    }

}
