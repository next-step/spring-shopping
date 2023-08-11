package shopping.order.domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import shopping.order.domain.Order;
import shopping.order.domain.ReceiptProduct;

class DomainFixture {

    static class Product {

        static shopping.order.domain.Product defaultProduct() {
            return new shopping.order.domain.Product(0L, "default", BigInteger.ONE, "1000");
        }

    }

    static class Receipt {

        static shopping.order.domain.Receipt from(Order order, BigDecimal exchangedPrice, double exchangeRate,
                Map<shopping.order.domain.Product, Integer> products) {

            List<ReceiptProduct> receiptProducts = products.entrySet()
                    .stream()
                    .map(entry -> entry.getKey().purchase(entry.getValue()))
                    .collect(Collectors.toList());

            return new shopping.order.domain.Receipt(order.getUserId(), receiptProducts,
                    order.getTotalPrice(), exchangedPrice, exchangeRate);
        }

    }

}
