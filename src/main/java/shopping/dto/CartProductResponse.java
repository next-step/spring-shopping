package shopping.dto;

import shopping.domain.CartProduct;

public class CartProductResponse {

    private final long id;
    private final String name;
    private final long productId;
    private final String imageUrl;
    private final long price;
    private final int quantity;

    private CartProductResponse(long id, String name, long productId, String imageUrl, long price, int quantity) {
        this.id = id;
        this.name = name;
        this.productId = productId;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
    }

    public static CartProductResponse from(CartProduct cartProduct) {
        return new CartProductResponse(
                cartProduct.getId(),
                cartProduct.getProduct().getName(),
                cartProduct.getProduct().getId(),
                cartProduct.getProduct().getImageUrl(),
                cartProduct.getProduct().getPrice(),
                cartProduct.getQuantity()
        );
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getProductId() {
        return productId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public long getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
