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

    private Double exchangeRate;

    protected Order() {
    }

    private Order(Long id, Long userId, Double exchangeRate) {
        this.id = id;
        this.userId = userId;
        this.exchangeRate = exchangeRate;
    }

    public Order(Long userId) {
        this(null, userId, null);
    }

    public Order(Long userId, Double exchangeRate) {
        this(null, userId, exchangeRate);
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }
}
