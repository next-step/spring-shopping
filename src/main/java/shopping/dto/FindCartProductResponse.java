package shopping.dto;

import shopping.domain.CartProduct;

public class FindCartProductResponse {

    private long id;
    private String name;
    private long productId;
    private String imageUrl;
    private long price;
    private int quantity;

    private FindCartProductResponse(long id, String name, long productId, String imageUrl, long price, int quantity) {
        this.id = id;
        this.name = name;
        this.productId = productId;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
    }

    public static FindCartProductResponse from(CartProduct cartProduct) {
        return new FindCartProductResponse(
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
