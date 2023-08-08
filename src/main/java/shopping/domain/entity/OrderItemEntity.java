package shopping.domain.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "order_item")
public class OrderItemEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_product_id")
    private OrderProductEntity orderProduct;

    private int quantity;

    protected OrderItemEntity() {
    };

    public OrderItemEntity(Long id, OrderEntity order, OrderProductEntity orderProduct, int quantity) {
        this.id = id;
        this.order = order;
        this.orderProduct = orderProduct;
        this.quantity = quantity;
    }

    public OrderItemEntity(OrderEntity order, OrderProductEntity orderProduct, int quantity) {
        this(null, order, orderProduct, quantity);
    }

    public Long getId() {
        return id;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public OrderProductEntity getOrderProduct() {
        return orderProduct;
    }

    public int getQuantity() {
        return quantity;
    }
}
