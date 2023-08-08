package shopping.fixture;

import org.springframework.test.util.ReflectionTestUtils;
import shopping.domain.product.Product;

public class ProductFixture {

    public static final Long CHICKEN_ID = 1L;
    public static final String CHICKEN_IMAGE = "/image/Chicken.png";
    public static final String CHICKEN_NAME = "Chicken";
    public static final int CHICKEN_PRICE = 10000;
    public static final Long PIZZA_ID = 2L;
    public static final String PIZZA_IMAGE = "/image/Pizza.png";
    public static final String PIZZA_NAME = "Pizza";
    public static final int PIZZA_PRICE = 13000;

    public static Product createChicken() {
        final Product product = new Product(CHICKEN_IMAGE, CHICKEN_NAME, CHICKEN_PRICE);
        ReflectionTestUtils.setField(product, "id", CHICKEN_ID);
        return product;
    }

    public static Product createPizza() {
        final Product product = new Product(PIZZA_IMAGE, PIZZA_NAME, PIZZA_PRICE);
        ReflectionTestUtils.setField(product, "id", PIZZA_ID);
        return product;
    }
}
