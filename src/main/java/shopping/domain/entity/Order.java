package shopping.domain.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    private Set<OrderItem> items = new HashSet<>();

    @Column(nullable = false)
    private Price totalPrice;

    protected Order() {
    }

    public Order(final Long id,
                 final Long userId,
                 final Set<OrderItem> items,
                 final Price totalPrice) {
        this.id = id;
        this.userId = userId;
        this.items = items;
        this.totalPrice = totalPrice;
    }

    public Order(final Long userId,
                 final Set<OrderItem> items,
                 final Price totalPrice) {
        throw new UnsupportedOperationException();
    }
}
