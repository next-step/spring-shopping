package shopping.domain.fixture;

import shopping.domain.entity.Order;
import shopping.domain.entity.OrderItem;
import shopping.domain.entity.Product;
import shopping.domain.entity.User;
import shopping.domain.vo.*;

import java.util.List;

public class DomainFixture {

    public static User createUser() {
        return new User(1L, new Email("test@test.com"), Password.from("test"));
    }

    public static Product createProduct() {
        return new Product(1L, new Name("치킨"), new Image("chicken.png"), new Price(2000));
    }

    public static OrderItem createOrderItem(final long productId, final int price, final int quantity) {
        return new OrderItem(
                productId,
                new Name("치킨"),
                new Image("chicken.png"),
                new Price(price),
                new Quantity(quantity)
        );
    }

    public static OrderItem createOrderItemWithId(final long productId, final int price, final int quantity) {
        return new OrderItem(
                1L,
                productId,
                new Name("치킨"),
                new Image("chicken.png"),
                new Price(price),
                new Quantity(quantity)
        );
    }

    public static OrderItem createOrderItem() {
        return createOrderItem(1, 1, 1);
    }

    public static OrderItem createOrderItemWithId() {
        return createOrderItemWithId(1, 1, 1);
    }

    public static Order createOrder(final long userId) {
        return Order.of(userId, List.of(createOrderItem()), new ExchangeRate(1.0));
    }

    public static Order createOrderWithId() {
        return Order.of(1L, 1L, List.of(createOrderItemWithId()), new ExchangeRate(1.0));
    }
}
