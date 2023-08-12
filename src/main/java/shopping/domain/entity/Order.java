package shopping.domain.entity;

import shopping.domain.vo.ExchangeRate;
import shopping.domain.vo.Price;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id", nullable = false)
    private List<OrderItem> items = new ArrayList<>();

    @Column(nullable = false)
    @AttributeOverride(name = "value", column = @Column(name = "total_price"))
    private Price totalPrice;

    @Column(nullable = false)
    @AttributeOverride(name = "value", column = @Column(name = "exchange_rate"))
    private ExchangeRate exchangeRate;

    protected Order() {
    }

    private Order(final Long id,
                  final Long userId,
                  final List<OrderItem> items,
                  final Price totalPrice,
                  final ExchangeRate exchangeRate) {
        this.id = id;
        this.userId = userId;
        this.items = items;
        this.totalPrice = totalPrice;
        this.exchangeRate = exchangeRate;
    }

    private Order(final Long userId,
                  final List<OrderItem> items,
                  final Price totalPrice,
                  final ExchangeRate exchangeRate) {
        this(null, userId, items, totalPrice, exchangeRate);
    }

    public static Order of(final Long id,
                           final Long userId,
                           final List<OrderItem> orderItems,
                           final ExchangeRate exchangeRate) {
        return new Order(id, userId, orderItems, calculateTotalPrice(orderItems), exchangeRate);
    }

    public static Order of(final Long userId,
                           final List<OrderItem> orderItems,
                           final ExchangeRate exchangeRate) {
        return new Order(userId, orderItems, calculateTotalPrice(orderItems), exchangeRate);
    }

    public double applyExchangeRate() {
        return exchangeRate.apply(totalPrice);
    }

    private static Price calculateTotalPrice(final List<OrderItem> orderItems) {
        return Price.sum(orderItems, OrderItem::calculateTotalPrice);
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public Price getTotalPrice() {
        return totalPrice;
    }

    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }
}
