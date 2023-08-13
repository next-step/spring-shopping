package shopping.dto.web.response;

import shopping.domain.cart.OrderItem;

public class OrderItemResponse {
    private Long id;
    private String name;
    private String imageUrl;
    private Long price;
    private Integer quantity;

    private OrderItemResponse(Long id, String name, String imageUrl, Long price, Integer quantity) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
    }

    public static OrderItemResponse of(OrderItem orderItem) {
        return new OrderItemResponse(
                orderItem.getId(),
                orderItem.getProduct().getName(),
                orderItem.getProduct().getImageUrl(),
                orderItem.getProduct().getPrice().getPrice().longValue(),
                orderItem.getQuantity().getQuantity()
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

    public Long getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
