package shopping.domain.entity.fixture;

import shopping.domain.entity.*;

public class EntityFixture {

    public static User createUser() {
        return new User(1L, "test@test.com", "test");
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
}
