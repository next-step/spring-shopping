package shopping.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Embedded
    @AttributeOverrides(@AttributeOverride(name = "price", column = @Column(name = "total_price", nullable = false)))
    private Price totalPrice;

    @Embedded
    @AttributeOverrides(@AttributeOverride(name = "ratio", column = @Column(name = "ratio", nullable = false)))
    private Ratio ratio;

    @CreatedDate
    @Column(name = "created_date")
    private Instant createdDate;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<OrderItem> orderItems;

    protected Order() {
    }

    public Order(User user, Price totalPrice, Ratio ratio) {
        this.user = user;
        this.totalPrice = totalPrice;
        this.ratio = ratio;
        this.orderItems = new ArrayList<>();
    }

    public Order(Long id, User user, Price totalPrice, Ratio ratio, List<OrderItem> orderItems) {
        this(user, totalPrice, ratio);
        this.id = id;
        this.orderItems = orderItems;
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

    public Double getRatio() {
        return ratio.getRatio();
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    public boolean isDifferentUser(User user) {
        return !this.user.equals(user);
    }
}
