package shopping.dto.response;

import shopping.domain.OrderItem;

public class OrderItemResponse {

    private final Long id;

    private final String name;

    private final String imageUrl;

    private final Long price;

    private final Integer quantity;

    private OrderItemResponse(Long id, String name, String imageUrl, Long price, Integer quantity) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
    }

    public static OrderItemResponse of(OrderItem orderItem) {
        return new OrderItemResponse(orderItem.getId(),
                orderItem.getName(),
                orderItem.getImageUrl(),
                orderItem.getPrice(),
                orderItem.getQuantity());
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

    public Long getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
