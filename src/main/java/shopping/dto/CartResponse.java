package shopping.dto;

import shopping.domain.entity.CartItem;

public class CartResponse {

    private Long id;
    private String name;
    private String image;
    private int price;
    private int quantity;

    private CartResponse() {
    }

    public CartResponse(final Long id, final String name, final String image, final int price, final int quantity) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
    }

    public static CartResponse from(final CartItem cartItem) {
        return new CartResponse(
                cartItem.getId(),
                cartItem.getProduct().getName().getName(),
                cartItem.getProduct().getImage().getImage(),
                cartItem.getProduct().getPrice().getPrice(),
                cartItem.getQuantity().getValue()
        );
    }

    public Long getId() {
        return id;
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
