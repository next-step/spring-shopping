package shopping.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Embedded
    @AttributeOverrides(@AttributeOverride(name = "price", column = @Column(name = "total_price", nullable = false)))
    private Price totalPrice;

    protected Order() {
    }

    public Order(User user, Price totalPrice) {
        this.user = user;
        this.totalPrice = totalPrice;
    }

    public Order(Long id, User user, Price totalPrice) {
        this(user, totalPrice);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Price getTotalPrice() {
        return totalPrice;
    }

    public boolean isDifferentUser(User user) {
        return !this.user.equals(user);
    }
}
