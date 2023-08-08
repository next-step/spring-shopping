package shopping.domain.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue
    private Long id;

    private Long totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_item_id")
    private CartItemEntity cartItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    protected OrderEntity() {};

    public OrderEntity(Long id, Long totalPrice, CartItemEntity cartItem, UserEntity user) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.cartItem = cartItem;
        this.user = user;
    }

    public OrderEntity(Long totalPrice, CartItemEntity cartItem, UserEntity user) {
        this(null, totalPrice, cartItem, user);
    }

    public Long getId() {
        return id;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public CartItemEntity getCartItem() {
        return cartItem;
    }

    public UserEntity getUser() {
        return user;
    }
}
