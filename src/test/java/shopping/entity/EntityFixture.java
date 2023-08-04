package shopping.entity;

import shopping.entity.product.Image;
import shopping.entity.product.Name;
import shopping.entity.product.Price;
import shopping.entity.product.Product;
import shopping.entity.user.Email;
import shopping.entity.user.Password;
import shopping.entity.user.User;

public class EntityFixture {
    public static User createUser() {
        return new User(1L, new Email("test@test.com"), Password.createEncodedPassword("test", password -> password));
    }

    public static Product createProduct() {
        return new Product(1L, new Name("치킨"), new Image("chicken.png"), new Price(2000));
    }
}
