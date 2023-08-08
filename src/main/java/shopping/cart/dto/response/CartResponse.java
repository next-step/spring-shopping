package shopping.cart.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import shopping.cart.domain.CartProduct;
import shopping.cart.domain.CartProductWithProduct;
import shopping.product.domain.Product;

public class CartResponse {

    private final Long cartProductId;
    private final String productImage;
    private final String productName;
    private final int cartProductQuantity;

    @JsonCreator
    public CartResponse(
        final Long cartProductId,
        final String productImage,
        final String productName,
        final int cartProductQuantity) {
        this.cartProductId = cartProductId;
        this.productImage = productImage;
        this.productName = productName;
        this.cartProductQuantity = cartProductQuantity;
    }


    public static CartResponse from(final CartProductWithProduct cartProductWithProduct) {
        return new CartResponse(
            cartProductWithProduct.getCartProduct(),
            cartProductWithProduct.getProduct()
        );
    }

    private CartResponse(final CartProduct cartProduct, final Product product) {
        this(
            cartProduct.getProductId(),
            product.getImage(),
            product.getName(),
            cartProduct.getQuantity()
        );
    }

    public Long getCartProductId() {
        return this.cartProductId;
    }

    public String getProductImage() {
        return this.productImage;
    }

    public String getProductName() {
        return this.productName;
    }

    public int getCartProductQuantity() {
        return this.cartProductQuantity;
    }
}

