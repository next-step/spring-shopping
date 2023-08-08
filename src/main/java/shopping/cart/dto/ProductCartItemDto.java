package shopping.cart.dto;

import shopping.cart.domain.CartItem;
import shopping.product.domain.Product;

public class ProductCartItemDto {

    private Product product;
    private CartItem cartItem;

    public ProductCartItemDto(Product product, CartItem cartItem) {
        this.product = product;
        this.cartItem = cartItem;
    }

    public Product getProduct() {
        return product;
    }

    public CartItem getCartItem() {
        return cartItem;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }
}
