package shopping.dto.web.response;

import shopping.domain.cart.CartItem;

public class CartItemResponse {

    private Long id;
    private String name;
    private String imageUrl;
    private Integer quantity;

    private CartItemResponse(Long id, String name, String imageUrl, Integer quantity) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public static CartItemResponse of(CartItem cartItem) {
        return new CartItemResponse(
                cartItem.getId(),
                cartItem.getProduct().getName(),
                cartItem.getProduct().getImageUrl(),
                cartItem.getQuantity().getQuantity()
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
