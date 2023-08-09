package shopping.dto;

import org.junit.jupiter.api.Test;
import shopping.domain.DomainFixture;
import shopping.domain.order.OrderItem;
import shopping.domain.product.Product;
import shopping.domain.wrapper.Quantity;

import static org.assertj.core.api.Assertions.assertThat;

class OrderItemResponseTest {

    @Test
    void createFromEntity() {
        // given
        Product product = DomainFixture.createProduct();
        OrderItem orderItem = new OrderItem(product.getId(), product.getName(),
                product.getImage(), product.getPrice(), new Quantity(2));

        // when
        OrderItemResponse response = OrderItemResponse.from(orderItem);

        // then
        assertThat(response.getName()).isEqualTo(product.getName().getName());
        assertThat(response.getImage()).isEqualTo(product.getImage().getImage());
        assertThat(response.getPrice()).isEqualTo(product.getPrice().getPrice());
        assertThat(response.getQuantity()).isEqualTo(orderItem.getQuantity().getQuantity());
    }
}