package shopping.cart.domain;

import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;
import shopping.exception.WooWaException;
import shopping.product.domain.Product;

public class Cart {
    private List<CartItem> cartItems;

    public Cart(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public void validate(List<Product> product) {
        if (isProductChanged(product)) {
            throw new WooWaException("상품이 변경되었습니다.", HttpStatus.CONFLICT);
        }
    }

    private boolean isProductChanged(List<Product> products) {
        return products.stream()
            .anyMatch(this::containChangedCartItem);
    }

    private boolean containChangedCartItem(Product product) {
        return cartItems.stream()
            .filter(cartItem -> cartItem.getProductId().equals(product.getId()))
            .anyMatch(cartItem -> cartItem.isProductChanged(product));
    }

    public List<CartItem> getCartItems() {
        return Collections.unmodifiableList(cartItems);
    }
}
