package shopping.dto.response;

import shopping.domain.cart.CartProduct;

public class CartResponse {

    private final Long cartProductId;
    private final String productImage;
    private final String productName;
    private final int cartProductQuantity;

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

    public static CartResponse from(final CartProduct cartProduct) {
        return new CartResponse(
            cartProduct.getId(),
            cartProduct.getProductImage(),
            cartProduct.getProductName(),
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

