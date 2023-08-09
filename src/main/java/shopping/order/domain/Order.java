package shopping.order.domain;

import java.math.BigDecimal;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import shopping.common.vo.Money;
import shopping.order.domain.vo.ExchangeRate;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    @Embedded
    private OrderItems orderItems = new OrderItems();
    @Embedded
    private ExchangeRate exchangeRate;

    protected Order() {
    }

    public Order(Long memberId) {
        this.memberId = memberId;
    }

    public Order(Long memberId, BigDecimal exchangeRate) {
        this.memberId = memberId;
        this.exchangeRate = new ExchangeRate(exchangeRate);
    }

    public Money getTotalPrice() {
        return orderItems.getAllOrderMoney();
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.updateOrder(this);
    }

    public Long getId() {
        return id;
    }

    public Long getMemberId() {
        return memberId;
    }

    public OrderItems getOrderItems() {
        return orderItems;
    }

    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }
}
