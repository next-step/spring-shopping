package shopping.order.domain;

import java.util.Collections;
import java.util.List;
import javax.persistence.*;

import shopping.common.converter.MoneyConverter;
import shopping.common.domain.Money;

@Entity(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ElementCollection
    @CollectionTable(name = "order_item",  joinColumns = @JoinColumn(name = "order_id"))
    private List<OrderItem> orderItems;
    @Convert(converter = MoneyConverter.class)
    private Money totalPrice;
    private Long memberId;

    public Order(List<OrderItem> orderItems, Money totalPrice, Long memberId) {
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
        this.memberId = memberId;
    }

    public Order(List<OrderItem> orderItems, Long memberId) {
        this(orderItems, calculateTotalMoney(orderItems), memberId);
    }

    protected Order() {
    }

    private static Money calculateTotalMoney(List<OrderItem> orderItems) {
        return orderItems.stream()
            .map(OrderItem::getTotalPrice)
            .reduce(Money.ZERO, Money::plus);
    }

    public boolean isOwner(Long memberId) {
        return this.memberId.equals(memberId);
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
