package shopping.order.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import shopping.common.vo.Money;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long memberId;
    @Embedded
    private OrderItems orderItems = new OrderItems();

    protected Order() {
    }

    public Order(Long memberId) {
        this.memberId = memberId;
    }

    public Money getTotalPrice() {
        return orderItems.getAllOrderMoney();
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
}
