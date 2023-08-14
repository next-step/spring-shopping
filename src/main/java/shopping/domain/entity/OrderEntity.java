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
import shopping.domain.CurrencyPoint;

@Entity
@Table(name = "order_info")
public class OrderEntity {

    @Id
    @GeneratedValue
    private Long id;

    private int totalPrice;

    private double totalPriceUSD;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> orderItems = new ArrayList<>();

    protected OrderEntity() {}

    public OrderEntity(
        Long id,
        int totalPrice,
        double totalPriceUSD,
        UserEntity user,
        List<OrderItemEntity> orderItems
    ) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.totalPriceUSD = totalPriceUSD;
        this.user = user;
        this.orderItems = orderItems;
    }

    private OrderEntity(
        int totalPrice,
        double totalPriceUSD,
        UserEntity user
    ) {
        this(null, totalPrice, totalPriceUSD, user, null);
    }

    public static OrderEntity from(
        UserEntity user,
        List<CartItemEntity> cartItems,
        Double currencyRatio,
        CurrencyPoint currencyPoint
    ) {
        int totalPrice = getTotalPrice(cartItems);
        double totalPriceUSD = getTotalPriceUSD(totalPrice, currencyRatio, currencyPoint);
        return new OrderEntity(
            totalPrice,
            totalPriceUSD,
            user
        );
    }

    private static int getTotalPrice(List<CartItemEntity> cartItems) {
        return cartItems.stream()
            .map(CartItemEntity::calculatePrice)
            .reduce(Integer::sum)
            .get();
    }

    private static double getTotalPriceUSD(int totalPrice, double currencyRatio, CurrencyPoint currencyPoint) {
        return Math.round(currencyPoint.getDigits() * totalPrice / currencyRatio) / currencyPoint.getDigits();
    }

    public void addOrderItems(
        List<CartItemEntity> cartItems,
        double currencyRatio,
        CurrencyPoint currencyPoint
    ) {
        this.orderItems = cartItems.stream()
            .map(cartItem -> OrderItemEntity.from(cartItem, this, currencyRatio, currencyPoint))
            .collect(Collectors.toUnmodifiableList());
    }

    public Long getId() {
        return id;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public double getTotalPriceUSD() {
        return totalPriceUSD;
    }

    public UserEntity getUser() {
        return user;
    }

    public List<OrderItemEntity> getOrderItems() {
        return orderItems;
    }
}
