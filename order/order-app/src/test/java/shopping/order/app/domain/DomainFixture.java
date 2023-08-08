package shopping.order.app.domain;

import static shopping.order.app.domain.DomainFixture.Product.defaultProduct;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

class DomainFixture {

    static class Cart {

        private static final long DEFAULT_CART_ID = 1L;
        private static final long DEFAULT_USER_ID = 1L;

        static shopping.mart.app.domain.Cart defaultCart() {
            shopping.mart.app.domain.Cart cart = new shopping.mart.app.domain.Cart(DEFAULT_CART_ID, DEFAULT_USER_ID);
            cart.addProduct(defaultProduct());
            cart.updateProduct(defaultProduct(), 100);
            return cart;
        }

    }

    static class Product {

        static shopping.mart.app.domain.Product defaultProduct() {
            return new shopping.mart.app.domain.Product(0L, "default", "images/default-image.png", "1000");
        }

    }

    static class Receipt {

        static shopping.order.app.domain.Receipt fromOrder(Order order) {
            List<ReceiptProduct> receiptProducts = order.getProducts().entrySet()
                    .stream()
                    .map(entry -> new ReceiptProduct(0, entry.getKey().getName(),
                            new BigInteger(entry.getKey().getPrice()), entry.getKey().getImageUrl(), entry.getValue()))
                    .collect(Collectors.toList());

            return new shopping.order.app.domain.Receipt(order.getUserId(), receiptProducts,
                    new BigInteger(order.getTotalPrice()), new BigDecimal(10000));
        }
    }
}
