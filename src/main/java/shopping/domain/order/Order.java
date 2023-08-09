package shopping.domain.order;

import shopping.exception.ErrorType;
import shopping.exception.ShoppingException;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    @OrderColumn(name = "item_index")
    private List<OrderItem> orderItems;

    @Column(name = "total_price")
    private long totalPrice;

    @Column(name = "exchange_rate")
    private double exchangeRate;

    protected Order() {

    }

    public Order(final Long id,
                 final Long userId,
                 final List<OrderItem> orderItems,
                 final long totalPrice,
                 final double exchangeRate) {
        validateNotEmpty(orderItems);

        this.id = id;
        this.userId = userId;
        this.orderItems = orderItems;
        this.totalPrice = totalPrice;
        this.exchangeRate = exchangeRate;
    }

    private void validateNotEmpty(final List<OrderItem> orderItems) {
        if (orderItems.isEmpty()) {
            throw new ShoppingException(ErrorType.CART_NO_ITEM);
        }
    }

    public Order(final Long userId, final List<OrderItem> orderItems, final long totalPrice, final double exchangeRate) {
        this(null, userId, orderItems, totalPrice, exchangeRate);
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }
}
