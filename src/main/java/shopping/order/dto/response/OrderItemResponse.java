package shopping.order.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import shopping.common.domain.Quantity;
import shopping.order.domain.OrderItem;
import shopping.common.domain.Money;
import shopping.product.domain.vo.Image;

public class OrderItemResponse {

    private Long productId;
    private String name;
    private int price;
    private int quantity;
    private String imageUrl;

    private OrderItemResponse() {
    }

    public OrderItemResponse(Long productId,
                             String name,
                             Money price,
                             Quantity quantity,
                             Image image) {
        this.productId = productId;
        this.name = name;
        this.price = price.intValue();
        this.quantity = quantity.getValue();
        this.imageUrl = image.toUrl();
    }

    public static OrderItemResponse from(OrderItem orderItem) {
        return new OrderItemResponse(orderItem.getProductId(),
                                     orderItem.getName(),
                                     orderItem.getPrice(),
                                     orderItem.getQuantity(),
                                     orderItem.getImage());
    }

    public static List<OrderItemResponse> of(List<OrderItem> orderItems) {
        return orderItems.stream()
            .map(OrderItemResponse::from)
            .collect(Collectors.toUnmodifiableList());
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
