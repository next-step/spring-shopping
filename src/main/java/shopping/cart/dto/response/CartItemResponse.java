package shopping.cart.dto.response;

import shopping.cart.domain.entity.CartItemEntity;

public class CartItemResponse {

    private Long cartItemId;
    private String name;
    private String imageUuidFileName;
    private int price;
    private int quantity;

    public CartItemResponse() {
    }

    private CartItemResponse(final Long cartItemId, final String name,
        final String imageUuidFileName, final int price,
        final int quantity) {
        this.cartItemId = cartItemId;
        this.name = name;
        this.imageUuidFileName = imageUuidFileName;
        this.price = price;
        this.quantity = quantity;
    }

    public static CartItemResponse from(CartItemEntity cartItem) {
        return new CartItemResponse(
            cartItem.getId(),
            cartItem.getProduct().getName(),
            cartItem.getProduct().getImageUuidFileName(),
            cartItem.getProduct().getPrice(),
            cartItem.getQuantity()
        );
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public String getName() {
        return name;
    }

    public String getImageUuidFileName() {
        return imageUuidFileName;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
