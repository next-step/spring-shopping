package shopping.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shopping.infrastructure.MockExchangeRateApi;
import shopping.global.vo.Name;
import shopping.global.vo.Price;
import shopping.global.vo.Quantity;
import shopping.order.domain.Order;
import shopping.order.domain.OrderProduct;
import shopping.order.repository.OrderRepository;
import shopping.product.domain.ProductImage;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    @DisplayName("회원의 아이디를 이용해 주문번호로 Order를 조회할 수 있다.")
    void findByMemberId() {
        // given
        final String name = "치킨";
        final String imageUrl = "image.png";
        final int price = 20000;
        Long memberId = 1L;
        OrderProduct orderProduct = new OrderProduct(
            1L,
            new Name(name),
            new ProductImage(imageUrl),
            new Price(price),
            new Quantity(10));
        Order order = new Order( memberId, new MockExchangeRateApi().callExchangeRate());
        order.addOrderProducts(List.of(orderProduct));
        orderRepository.save(order);

        // when & then
        assertThat(orderRepository.findByMemberId(memberId))
            .isEqualTo(List.of(order));
    }
}
