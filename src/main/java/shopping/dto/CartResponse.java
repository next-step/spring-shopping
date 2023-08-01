package shopping.dto;

import shopping.domain.CartItem;

public class CartResponse {
    private Long id;
    private String name;
    private String image;
    private int price;
    private int quantity;

    private CartResponse() {
    }

    public CartResponse(Long id, String name, String image, int price, int quantity) {
        this.id = id;
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