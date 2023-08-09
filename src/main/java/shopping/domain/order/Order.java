package shopping.domain.order;

import shopping.domain.member.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Embedded
    private OrderPrice orderPrice;

    @Embedded
    private ExchangeRate exchangeRate;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems = new ArrayList<>();


    protected Order() {
    }

    public Order(final Member member, final double exchangeRate) {
        this.member = member;
        this.orderPrice = OrderPrice.defaultValue();
        this.exchangeRate = ExchangeRate.from(exchangeRate);
    }

    public void addOrderItem(final OrderItem orderItem) {
        orderItems.add(orderItem);
        orderPrice = orderPrice.plusPrice(orderItem.getTotalPrice());
    }

    public int getOrderPrice() {
        return orderPrice.getValue();
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public double getExchangeRate() {
        return exchangeRate.getValue();
    }

    public List<OrderItem> getOrderItems() {
        return new ArrayList<>(orderItems);
    }
}
