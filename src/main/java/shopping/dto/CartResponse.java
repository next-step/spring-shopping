package shopping.dto;

import shopping.domain.CartItem;

public class CartResponse {
    private Long cartItemId;
    private String name;
    private String image;
    private int price;
    private int quantity;

    private CartResponse() {
    }

    public CartResponse(Long cartItemId, String name, String image, int price, int quantity) {
        this.cartItemId = cartItemId;
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
    }

    public static CartResponse from(CartItem cartItem) {
        return new CartResponse(
                cartItem.getId(),
                cartItem.getProduct().getName(),
                cartItem.getProduct().getImage(),
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

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
