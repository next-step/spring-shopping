package shopping;

import shopping.domain.entity.ProductEntity;

public class TestFixture {

    private static Long sequence = 1L;
    public static ProductEntity createProductEntity(String name, int price) {
        return new ProductEntity(sequence, name, "uuid" + sequence++, price);
    }
}
