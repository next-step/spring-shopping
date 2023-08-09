package shopping.dto;

import org.junit.jupiter.api.Test;
import shopping.domain.DomainFixture;
import shopping.domain.order.Order;
import shopping.domain.order.OrderItem;
import shopping.domain.product.Product;
import shopping.domain.wrapper.Quantity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderDetailResponseTest {

    @Test
    void name() {
        // given
        Product product = DomainFixture.createProduct();

        Order order = new Order(1L, 1L,
                List.of(new OrderItem(product.getId(), product.getName(),
                        product.getImage(), product.getPrice(), new Quantity(2))),
                20000L
        );

        // when
        OrderDetailResponse response = OrderDetailResponse.from(order);

        // then
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getItems().get(0).getName()).isEqualTo("치킨");
        assertThat(response.getTotalPrice()).isEqualTo(20000L);
    }
}