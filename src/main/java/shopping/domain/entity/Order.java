package shopping.domain.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderItem> items = new ArrayList<>();

    @Column(nullable = false)
    private Price totalPrice;

    protected Order() {
    }

    private Order(final Long id,
                  final Long userId,
                  final List<OrderItem> items,
                  final Price totalPrice) {
        this.id = id;
        this.userId = userId;
        this.items = items;
        this.totalPrice = totalPrice;
    }

    private Order(final Long userId,
                  final List<OrderItem> items,
                  final Price totalPrice) {
        this(null, userId, items, totalPrice);
    }

    public static Order of(final Long userId, final List<OrderItem> orderItems) {
        return new Order(userId, orderItems, calculateTotalPrice(orderItems));
    }

    private static Price calculateTotalPrice(final List<OrderItem> items) {
        return null;
    }
}
