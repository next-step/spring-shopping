package shopping.domain.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.ORDERED;

    @Column(name = "total_price")
    @Embedded
    private OrderProductPrice totalPrice;

    @Column(name = "currency_rate")
    @Embedded
    private OrderCurrencyRate orderCurrencyRate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    protected Order() {
    }

    public Order(final Long memberId, final List<OrderProduct> orderProducts) {
        this.memberId = memberId;
        this.orderProducts = orderProducts;

        this.totalPrice = new OrderProductPrice(computeTotalPrice(orderProducts));
        orderProducts.forEach(orderProduct -> orderProduct.updateOrder(this));
    }

    private long computeTotalPrice(final List<OrderProduct> orderProducts) {
        return orderProducts.stream()
            .mapToLong(OrderProduct::computeTotalPrice)
            .sum();
    }

    public Long getId() {
        return this.id;
    }

    public long getTotalPrice() {
        return this.totalPrice.getPrice();
    }

    public List<OrderProduct> getOrderProducts() {
        return this.orderProducts;
    }
}
