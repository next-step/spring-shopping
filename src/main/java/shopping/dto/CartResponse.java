package shopping.dto;

public class CartResponse {
    private Long cartItemId;
    private String name;
    private String image;
    private int price;
    private int quantity;

    public CartResponse(Long cartItemId, String name, String image, int price, int quantity) {
        this.cartItemId = cartItemId;
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
    }
}
