package shopping.domain.order;

import javax.persistence.*;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Embedded
    private OrderItemName orderItemName;

    @Embedded
    private OrderItemPrice orderItemPrice;

    @Embedded
    private OrderItemImage orderItemImage;

    @Embedded
    private OrderItemQuantity orderItemQuantity;

    protected OrderItem() {
    }

    public OrderItem(final String orderItemName,
                     final int orderItemPrice,
                     final String orderItemImage,
                     final int orderItemQuantity,
                     final Order order
    ) {
        this.orderItemName = OrderItemName.from(orderItemName);
        this.orderItemPrice = OrderItemPrice.from(orderItemPrice);
        this.orderItemImage = OrderItemImage.from(orderItemImage);
        this.orderItemQuantity = OrderItemQuantity.from(orderItemQuantity);
        this.order = order;
        order.addOrderItem(this);
    }

    public int getTotalPrice() {
        return getOrderItemPrice() * getOrderItemQuantity();
    }

    public Long getId() {
        return id;
    }

    public String getOrderItemName() {
        return orderItemName.getValue();
    }

    public int getOrderItemPrice() {
        return orderItemPrice.getValue();
    }

    public String getOrderItemImage() {
        return orderItemImage.getValue();
    }

    public int getOrderItemQuantity() {
        return orderItemQuantity.getValue();
    }
}
