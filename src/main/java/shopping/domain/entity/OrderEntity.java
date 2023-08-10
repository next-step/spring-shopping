package shopping.domain.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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

    private Double totalPriceUSD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> orderItems = new ArrayList<>();

    protected OrderEntity() {};

    public OrderEntity(Long id, int totalPrice, Double totalPriceUSD,UserEntity user,
        List<OrderItemEntity> orderItems) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.totalPriceUSD = totalPriceUSD;
        this.user = user;
        this.orderItems = orderItems;
    }

    public OrderEntity(int totalPrice, Double totalPriceUSD, UserEntity user) {
        this(null, totalPrice, totalPriceUSD, user, null);
    }

    public static OrderEntity from(UserEntity user,
        List<CartItemEntity> cartItems,
        Double currencyRatio
    ) {
        int totalPrice = cartItems.stream()
            .map(CartItemEntity::calculatePrice)
            .reduce((i1, i2) -> i1 + i2)
            .get();
        Double totalPriceUSD = totalPrice / currencyRatio;
        return new OrderEntity(
            totalPrice,
            totalPriceUSD,
            user
        );
    }

    public void addOrderItems(
        List<CartItemEntity> cartItems,
        Double currencyRatio
    ) {
        this.orderItems = cartItems.stream()
            .map(cartItem -> OrderItemEntity.from(cartItem, this, currencyRatio))
            .collect(Collectors.toUnmodifiableList());
    }

    public Long getId() {
        return id;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public Double getTotalPriceUSD() {
        return totalPriceUSD;
    }

    public UserEntity getUser() {
        return user;
    }

    public List<OrderItemEntity> getOrderItems() {
        return orderItems;
    }
}
