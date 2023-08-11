package shopping.order.repository;

import java.math.BigInteger;

class DomainFixture {

    static class Product {

        static shopping.order.domain.Product defaultProduct() {
            return new shopping.order.domain.Product(0L, "default", BigInteger.ONE, "images/default-image.png");
        }
    }
}
