package shopping.order.domain;

import java.util.Collections;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import shopping.product.domain.vo.Money;

@Entity(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ElementCollection
    @CollectionTable(name = "order_item",  joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderItem> orderItems;
    @Embedded
    @AttributeOverride(name = "amount", column = @Column(name = "total_price"))
    private Money totalPrice;

    public Order(List<OrderItem> orderItems, Money totalPrice) {
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
    }

    public Order(List<OrderItem> orderItems) {
        this(orderItems, calculateTotalMoney(orderItems));
    }

    protected Order() {
    }

    private static Money calculateTotalMoney(List<OrderItem> orderItems) {
        return orderItems.stream()
            .map(OrderItem::getPrice)
            .reduce(Money.ZERO, Money::plus);
    }

    public Long getId() {
        return id;
    }

    public List<OrderItem> getOrderItems() {
        return Collections.unmodifiableList(orderItems);
    }

    public Money getTotalPrice() {
        return totalPrice;
    }
}
