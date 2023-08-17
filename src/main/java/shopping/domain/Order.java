package shopping.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private final List<OrderItem> orderItems = new ArrayList<>();

    @Column(nullable = false)
    private ExchangeRate exchangeRate;

    protected Order() {
    }

    public Order(Member member, List<OrderItem> orderItems, ExchangeRate exchangeRate) {
        this(null, member, orderItems, exchangeRate);
    }

    public Order(Long id, Member member, List<OrderItem> orderItems, ExchangeRate exchangeRate) {
        this.id = id;
        this.member = member;
        setOrderItems(orderItems);
        this.exchangeRate = exchangeRate;
    }

    public boolean isOwner(Long memberId) {
        return this.member.matchId(memberId);
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

    public List<OrderItem> getOrderItems() {
        return Collections.unmodifiableList(orderItems);
    }

    public long calculateTotalPrice() {
        return orderItems.stream()
            .mapToLong(OrderItem::calculateTotalPrice)
            .sum();
    }

    private void setOrderItems(List<OrderItem> orderItems) {
        orderItems.forEach(orderItem -> {
            orderItem.setOrder(this);
            this.orderItems.add(orderItem);
        });
    }
}
