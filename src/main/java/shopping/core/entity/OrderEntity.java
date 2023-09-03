package shopping.core.entity;

import javax.persistence.*;

@Entity
@Table(name = "orders")
public class OrderEntity extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "currency")
    private Double currencyByUsd;

    protected OrderEntity() {
    }

    public OrderEntity(Long userId, Double currencyByUsd) {
        this(null, userId, currencyByUsd);
    }

    public OrderEntity(Long id, Long userId, Double currencyByUsd) {
        this.id = id;
        this.userId = userId;
        this.currencyByUsd = currencyByUsd;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public Double getCurrencyByUsd() {
        return currencyByUsd;
    }
}
