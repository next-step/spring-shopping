package shopping.domain.cart;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    protected Order() {
    }

    private Order(Long id, Long userId) {
        this.id = id;
        this.userId = userId;
    }

    public Order(Long userId) {
        this(null, userId);
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }
}
