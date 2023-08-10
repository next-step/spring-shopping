package shopping.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.exchange.MockExchangeRateApi;
import shopping.global.vo.Name;
import shopping.global.vo.Price;
import shopping.global.vo.Quantity;
import shopping.order.domain.Order;
import shopping.order.domain.OrderProduct;
import shopping.product.domain.ProductImage;
import shopping.util.ExchangeRateApi;

public class OrderTest {

    private ExchangeRateApi exchangeRateApi = new MockExchangeRateApi();

    @Test
    @DisplayName("Order를 생성한다.")
    void 주문_생성_테스트() {
        // given
        final String name = "치킨";
        final String imageUrl = "image.png";
        final int price = 20000;

        // when & then
        assertThatCode(() -> new Order(
                List.of(new OrderProduct(
                        1L,
                        new Name(name),
                        new ProductImage(imageUrl),
                        new Price(price),
                        new Quantity(10)
                    )
                ), 1L, exchangeRateApi.callExchangeRate()
            )
        ).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("주문상품으로 주문의 총합을 구할 수 있다.")
    void calculateOrderSum() {
        // given
        final String name = "치킨";
        final String imageUrl = "image.png";
        final int price = 20000;
        final Long memberId = 1L;

        Order order = new Order(memberId, exchangeRateApi.callExchangeRate());
        OrderProduct orderProduct = new OrderProduct(
            1L,
            new Name(name),
            new ProductImage(imageUrl),
            new Price(price),
            new Quantity(10)
        );
        List<OrderProduct> orderProducts = List.of(orderProduct, orderProduct);

        // when
        order.addOrderProducts(orderProducts);

        // then
        assertThat(order.getTotalPrice()).isEqualTo(400000);
    }

}
