package shopping.domain.order;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.global.vo.Name;
import shopping.global.vo.Price;
import shopping.global.vo.Quantity;
import shopping.order.domain.OrderProduct;
import shopping.product.domain.ProductImage;

public class OrderProductTest {

    @Test
    @DisplayName("Order Product를 생성한다.")
    void 주문_상품_생성_테스트() {
        // given
        final String name = "치킨";
        final String imageUrl = "image.png";
        final int price = 20000;
        assertThatCode(() -> new OrderProduct(
            1L,
            new Name(name),
            new ProductImage(imageUrl),
            new Price(price),
            new Quantity(10))
        ).doesNotThrowAnyException();

    }
}
