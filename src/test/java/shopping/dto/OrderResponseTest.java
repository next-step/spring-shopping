package shopping.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.domain.DomainFixture;
import shopping.domain.order.Order;
import shopping.domain.order.OrderItem;
import shopping.domain.order.OrderItemPrice;
import shopping.domain.product.Product;
import shopping.domain.wrapper.Quantity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderResponseTest {

    @Test
    @DisplayName("Order 엔티티로 Response 객체를 생성한다.")
    void createFromEntity() {
        // given
        Product product = DomainFixture.createProduct();

        Order order = new Order(1L, 1L,
                List.of(new OrderItem(product.getId(), product.getName(),
                        product.getImage(), new OrderItemPrice(product.getPrice().getPrice()), new Quantity(2))),
                20000L,
                1
        );

        // when
        OrderResponse response = OrderResponse.from(order);

        // then
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getItems().get(0).getName()).isEqualTo("치킨");
    }
}