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

    public OrderEntity(
        int totalPrice,
        double totalPriceUSD,
        UserEntity user
    ) {
        this(null, totalPrice, totalPriceUSD, user, null);
    }

    public static OrderEntity by(UserEntity user) {
        return new OrderEntity(
            0,
            0D,
            user
        );
    }

    public void updatePrices(
        List<CartItemEntity> cartItems,
        Double currencyRatio,
        CurrencyPoint currencyPoint
    ) {
        updateTotalPrice(cartItems);
        updateTotalPriceUSD(currencyRatio, currencyPoint);
    }

    private void updateTotalPrice(List<CartItemEntity> cartItems) {
        this.totalPrice = cartItems.stream()
            .map(CartItemEntity::calculatePrice)
            .reduce(Integer::sum)
            .get();
    }

    private void updateTotalPriceUSD(double currencyRatio, CurrencyPoint currencyPoint) {
        this.totalPriceUSD = Math.round(currencyPoint.getDigits() * totalPrice / currencyRatio) / currencyPoint.getDigits();
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
