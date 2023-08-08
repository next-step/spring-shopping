package shopping.dto.request;

import shopping.domain.Order;
import shopping.domain.OrderItem;
import shopping.domain.Product;

public class OrderItemRequest {

    private long productId;
    private long price;
    private int quantity;
    private String name;
    private String imageUrl;

    public OrderItem toEntity(Order order, Product product) {
        return new OrderItem(
            order,
            product,
            name,
            price,
            quantity,
            imageUrl
        );
    }

    public OrderItemRequest(long productId, long price, int quantity, String name, String imageUrl) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    private OrderItemRequest() {
    }

    public long getProductId() {
        return productId;
    }

    public long getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
