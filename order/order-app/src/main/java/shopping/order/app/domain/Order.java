package shopping.order.app.domain;

import java.math.BigInteger;
import java.util.Map;
import java.util.Map.Entry;
import shopping.mart.app.domain.Cart;
import shopping.mart.app.domain.Product;
import shopping.order.app.domain.exception.EmptyCartException;

public final class Order {

    private final Long id;
    private final BigInteger totalPrice;
    private final Map<Product, Integer> products;

    public Order(Cart cart) {
        this(null, cart);
    }

    public Order(Long id, Cart cart) {
        validCart(cart);
        this.id = id;
        this.products = cart.getProductCounts();
        this.totalPrice = calculatePrice();
    }

    private void validCart(Cart cart) {
        if (cart == null || cart.isEmptyCart()) {
            throw new EmptyCartException();
        }
    }

    public BigInteger calculatePrice() {
        BigInteger calculate = BigInteger.ZERO;
        for (Entry<Product, Integer> entry : products.entrySet()) {
            calculate = calculate.add(
                    BigInteger.valueOf(Long.parseLong(entry.getKey().getPrice()) * entry.getValue()));
        }
        return calculate;
    }

    public Long getId() {
        return id;
    }

    public String getTotalPrice() {
        return totalPrice.toString();
    }

    public Map<Product, Integer> getProducts() {
        return products;
    }
}
