package shopping.cart.domain;

import shopping.product.domain.Product;

public class CartProductWithProduct {
    private CartProduct cartProduct;
    private Product product;

    public CartProductWithProduct(final CartProduct cartProduct, final Product product) {
        this.cartProduct = cartProduct;
        this.product = product;
    }

    public CartProduct getCartProduct() {
        return cartProduct;
    }

    public Product getProduct() {
        return product;
    }
}
