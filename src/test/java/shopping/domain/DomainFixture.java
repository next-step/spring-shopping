package shopping.domain;

import shopping.domain.product.Image;
import shopping.domain.product.Name;
import shopping.domain.product.Price;
import shopping.domain.product.Product;
import shopping.domain.user.Email;
import shopping.domain.user.Password;
import shopping.domain.user.User;

public class DomainFixture {
    public static User createUser() {
        return new User(1L, new Email("test@test.com"), Password.createEncodedPassword("test", password -> password));
    }

    public static Product createProduct() {
        return new Product(1L, new Name("치킨"), new Image("chicken.png"), new Price(20000));
    }
}
