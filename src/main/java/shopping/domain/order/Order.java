package shopping.domain.order;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public Order(
        final Long memberId,
        final Double usdRate,
        final List<OrderProduct> orderProducts
    ) {
        this.memberId = memberId;
        this.orderCurrencyRate = usdRate == null ? null : new OrderCurrencyRate(usdRate);
        this.orderProducts = orderProducts;

        this.totalPrice = new OrderProductPrice(computeTotalPrice(orderProducts));
        orderProducts.forEach(orderProduct -> orderProduct.updateOrder(this));
    }

    public Order(
        final Long memberId,
        final List<OrderProduct> orderProducts
    ) {
        this(memberId, null, orderProducts);
    }

    private long computeTotalPrice(final List<OrderProduct> orderProducts) {
        return orderProducts.stream()
            .mapToLong(OrderProduct::computeTotalPrice)
            .sum();
    }

    public Long getId() {
        return this.id;
    }

    public Long getMemberId() {
        return this.memberId;
    }

    public long getTotalPrice() {
        return this.totalPrice.getPrice();
    }

    public Double getOrderCurrencyRate() {
        return this.orderCurrencyRate.getCurrencyRate();
    }

    public List<OrderProduct> getOrderProducts() {
        return this.orderProducts;
    }
}
