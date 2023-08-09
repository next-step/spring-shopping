package shopping.order.dto.response;

import java.util.List;
import java.util.stream.Collectors;
import shopping.order.domain.Order;
import shopping.order.domain.OrderItem;

public class OrderDetailResponse {

    private Long id;
    private List<OrderItemDetailResponse> orderItems;
    private String totalPrice;

    private OrderDetailResponse() {
    }

    public OrderDetailResponse(Order order) {
        this.id = order.getId();
        this.orderItems = order.getOrderItems().getOrderItems().stream()
            .map(OrderItemDetailResponse::new)
            .collect(Collectors.toList());
        this.totalPrice = order.getTotalPrice().getAmount().toPlainString();
    }

    public Long getId() {
        return id;
    }

    public List<OrderItemDetailResponse> getOrderItems() {
        return orderItems;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public static class OrderItemDetailResponse {

        private Long id;
        private Long productId;
        private String productName;
        private String productPrice;
        private String productImage;
        private int quantity;

        private OrderItemDetailResponse() {
        }

        public OrderItemDetailResponse(OrderItem orderItem) {
            this.id = orderItem.getId();
            this.productId = orderItem.getProductId();
            this.productName = orderItem.getProductName();
            this.productPrice = orderItem.getProductPrice().getAmount().toPlainString();
            this.productImage = orderItem.getProductImage().getImagePath();
            this.quantity = orderItem.getQuantity().getValue();
        }

        public Long getId() {
            return id;
        }

        public Long getProductId() {
            return productId;
        }

        public String getProductName() {
            return productName;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public String getProductImage() {
            return productImage;
        }

        public int getQuantity() {
            return quantity;
        }
    }

}
