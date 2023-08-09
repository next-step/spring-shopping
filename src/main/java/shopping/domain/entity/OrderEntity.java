package shopping.domain.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue
    private Long id;

    private int totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> orderItems = new ArrayList<>();

    protected OrderEntity() {};

    public OrderEntity(Long id, int totalPrice, UserEntity user,
        List<OrderItemEntity> orderItems) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.user = user;
        this.orderItems = orderItems;
    }

    public OrderEntity(int totalPrice, UserEntity user) {
        this(null, totalPrice, user, null);
    }

    public void addOrderItems(List<OrderItemEntity> orderItems) {
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public UserEntity getUser() {
        return user;
    }

    public List<OrderItemEntity> getOrderItems() {
        return orderItems;
    }
}
