package shopping.application.support;

import shopping.domain.Member;
import shopping.domain.Product;

public class DomainFixture {

    private final static String EMAIL = "woowa@woowa.com";
    private final static String PASSWORD = "password";
    public static final String PRODUCT_NAME = "productName";
    public static final String IMAGE_URL = "imageUrl";
    public static final long PRICE = 10000L;

    public static Member getMember(long memberId) {
        return new Member(memberId, EMAIL, PASSWORD);
    }

    public static Product getProduct(long productId) {
        return new Product(
            productId,
            PRODUCT_NAME,
            IMAGE_URL,
            PRICE
        );
    }

}
