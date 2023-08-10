package shopping.domain.order;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import shopping.domain.member.Member;

@Entity
@Table(name = "ORDERS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Embedded
    private ExchangeRate exchangeRate;

    protected Order() {
    }

    public Order(final Member member, final List<OrderItem> orderItems) {
        this(member, orderItems, 0.0);
    }

    public Order(final Member member, final List<OrderItem> orderItems, final double exchangeRate) {
        this.member = member;
        this.setOrderItems(orderItems);
        this.exchangeRate = ExchangeRate.from(exchangeRate);
    }

    private void setOrderItems(final List<OrderItem> orderItems) {
        this.orderItems.addAll(orderItems);
        this.orderItems.forEach(orderItem -> orderItem.setOrder(this));
    }

    public Long getId() {
        return this.id;
    }

    public Member getMember() {
        return this.member;
    }

    public List<OrderItem> getOrderItems() {
        return this.orderItems;
    }

    public double getExchangeRate() {
        return round(this.exchangeRate.getValue());
    }

    public long getOrderTotalPrice() {
        return orderItems.stream().mapToLong(OrderItem::getOrderItemTotalPrice).sum();
    }

    public double getConvertedOrderTotalPrice() {
        final long orderKRWTotalPrice = this.getOrderTotalPrice();
        return round(orderKRWTotalPrice / this.getExchangeRate());
    }

    private double round(final double origin) {
        return Math.round(origin * 100.0) / 100.0;
    }
}
