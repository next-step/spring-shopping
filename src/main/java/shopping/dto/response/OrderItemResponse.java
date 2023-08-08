package shopping.dto.response;

import shopping.domain.OrderItem;

public class OrderItemResponse {

    private Long id;
    private long productId;

    private String name;

    private long price;

    private int quantity;

    private String imageUrl;

    private OrderItemResponse() {
    }

    public OrderItemResponse(Long id, long productId, String name, long price, int quantity,
            String imageUrl) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageUrl = imageUrl;
    }

    public static OrderItemResponse of(OrderItem orderItem) {
        return new OrderItemResponse(
            orderItem.getId(),
            orderItem.getProduct().getId(),
            orderItem.getName(),
            orderItem.getPrice(),
            orderItem.getQuantity(),
            orderItem.getImageUrl()
        );
    }

    public Long getId() {
        return id;
    }

    public long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
