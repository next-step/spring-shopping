package shopping.order.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shopping.mart.app.api.cart.CartUseCase;
import shopping.mart.app.api.cart.response.CartResponse;
import shopping.mart.app.api.cart.response.CartResponse.ProductResponse;
import shopping.order.app.api.order.OrderUseCase;
import shopping.order.app.api.order.request.OrderRequest;
import shopping.order.app.domain.Exchange;
import shopping.order.app.spi.ExchangeVendor;
import shopping.order.app.spi.ReceiptRepository;

@DisplayName("OrderService 클래스")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = OrderService.class)
class OrderServiceTest {

    @Autowired
    private OrderUseCase orderUseCase;

    @MockBean
    private ReceiptRepository receiptRepository;

    @MockBean
    private ExchangeVendor exchangeVendor;

    @MockBean
    private CartUseCase cartUseCase;

    @Nested
    @DisplayName("order 메소드는")
    class order_method {

        @Test
        @DisplayName("order 메소드는 OrderRequest를 받아, cart의 아이템을 구매한다.")
        void buy_cart_products_when_receive_order_request() {
            // given
            long cartId = 1L;
            long productId = 2L;
            long userId = 3L;

            OrderRequest orderRequest = new OrderRequest(userId);

            when(cartUseCase.getCart(userId)).thenReturn(getCartResponse(cartId, productId));
            when(exchangeVendor.currentExchange()).thenReturn(new Exchange(1.1D));

            // when
            orderUseCase.order(orderRequest);

            // then
            verify(receiptRepository, times(1)).persist(any());
            verify(cartUseCase, times(1)).clearCart(anyLong());
        }

        private CartResponse getCartResponse(long cartId, long productId) {
            int defaultCount = 1;
            List<ProductResponse> productResponses = List.of(
                    new ProductResponse(productId, defaultCount, "default-image.png", "default", "1")
            );

            return new CartResponse(cartId, productResponses);
        }

    }

}
