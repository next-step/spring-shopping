package shopping.domain.order;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.global.vo.Name;
import shopping.global.vo.Price;
import shopping.global.vo.Quantity;
import shopping.order.domain.Order;
import shopping.order.domain.OrderProduct;
import shopping.product.domain.ProductImage;

public class OrderTest {

    @Test
    @DisplayName("Order를 생성한다.")
    void 주문_생성_테스트() {
        final String name = "치킨";
        final String imageUrl = "image.png";
        final int price = 20000;
        assertThatCode(() -> new Order(List.of(new OrderProduct(
            1L,
            new Name(name),
            new ProductImage(imageUrl),
            new Price(price),
            new Quantity(10))), new Price(1000))
        ).doesNotThrowAnyException();
    }
}