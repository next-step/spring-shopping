package shopping.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shopping.domain.entity.CartItemEntity;
import shopping.domain.entity.OrderEntity;
import shopping.domain.entity.OrderItemEntity;
import shopping.domain.entity.ProductEntity;
import shopping.domain.entity.UserEntity;

@DisplayName("OrderEntityTest")
public class OrderEntityTest {

    @Test
    @DisplayName("성공 : 주문 상품의 전체 금액과 환율 정보 업데이트")
    void updatePrices() {
        // given
        UserEntity user = new UserEntity(1L, "test@email.com", "test_password");
        OrderEntity order = OrderEntity.by(user);
        ProductEntity chicken = new ProductEntity(1L, "치킨", "image_chicken", 20000);
        ProductEntity pizza = new ProductEntity(2L, "피자", "image_pizza", 25000);
        CartItemEntity cartChicken = new CartItemEntity(1L, user, chicken, 1);
        CartItemEntity cartPizza = new CartItemEntity(2L, user, pizza, 2);
        List<CartItemEntity> cartItems = List.of(cartChicken, cartPizza);
        double currencyRatio = 1000d;
        CurrencyPoint currencyPoint = CurrencyPoint.HUNDREDTH;

        // when
        order.updatePrices(cartItems, currencyRatio, currencyPoint);

        // then
        assertThat(order.getTotalPrice()).isEqualTo(70000);
        assertThat(order.getTotalPriceUSD()).isEqualTo(70.00d);
    }

    @Test
    @DisplayName("성공 : OrderItemEntity 주문 리스트 추가")
    void addOrderItems() {
        // given
        UserEntity user = new UserEntity(1L, "test@email.com", "test_password");
        OrderEntity order = OrderEntity.by(user);
        ProductEntity chicken = new ProductEntity(1L, "치킨", "image_chicken", 20000);
        ProductEntity pizza = new ProductEntity(2L, "피자", "image_pizza", 25000);
        CartItemEntity cartChicken = new CartItemEntity(1L, user, chicken, 1);
        CartItemEntity cartPizza = new CartItemEntity(2L, user, pizza, 2);
        List<CartItemEntity> cartItems = List.of(cartChicken, cartPizza);
        double currencyRatio = 1000d;
        CurrencyPoint currencyPoint = CurrencyPoint.HUNDREDTH;

        // when
        order.addOrderItems(cartItems, currencyRatio, currencyPoint);
        List<OrderItemEntity> orderItems = order.getOrderItems();

        // then
        assertThat(orderItems.size()).isEqualTo(2);
        assertThat(orderItems.get(0).getTotalPrice()).isEqualTo(20000);
        assertThat(orderItems.get(1).getTotalPrice()).isEqualTo(50000);

    }

}
