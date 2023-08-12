package shopping.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.domain.vo.Image;
import shopping.domain.vo.Name;
import shopping.domain.vo.Price;
import shopping.domain.vo.Quantity;

import static org.assertj.core.api.Assertions.assertThatNoException;

class OrderItemTest {

    @Test
    @DisplayName("주문 아이템을 생성한다")
    void createOrderItem() {
        // given
        final long productId = 1L;
        final Name name = new Name("name");
        final Image image = new Image("/image.png");
        final Price price = new Price(1000);
        final Quantity quantity = new Quantity(1);

        // when & then
        assertThatNoException().isThrownBy(() -> new OrderItem(productId, name, image, price, quantity));
    }
}
