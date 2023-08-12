package shopping.domain.entity.fixture;

import shopping.domain.Email;
import shopping.domain.ExchangeRate;
import shopping.domain.Password;
import shopping.domain.entity.*;

import java.util.List;

public class EntityFixture {

    public static User createUser() {
        return new User(1L, new Email("test@test.com"), Password.createEncodedPassword("test", password -> password));
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
