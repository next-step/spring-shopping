package shopping.domain.cart;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
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

    @Embedded
    @AttributeOverride(name = "rate", column = @Column(name = "exchange_rate"))
    @AttributeOverride(name = "source", column = @Column(name = "source"))
    @AttributeOverride(name = "target", column = @Column(name = "target"))
    private ExchangeRate exchangeRate;

    protected Order() {
    }

    private Order(Long id, Long userId, ExchangeRate exchangeRate) {
        this.id = id;
        this.userId = userId;
        this.exchangeRate = exchangeRate;
    }

    public Order(Long userId) {
        this(null, userId, null);
    }

    public Order(Long userId, ExchangeRate exchangeRate) {
        this(null, userId, exchangeRate);
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }
}
