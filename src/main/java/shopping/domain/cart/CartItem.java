package shopping.domain.cart;

import shopping.domain.user.User;

import java.util.Objects;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cart_item")
public class CartItem {

    private static final int ADD_COUNT = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Embedded
    @AttributeOverrides(@AttributeOverride(name = "quantity", column = @Column(name = "quantity", nullable = false)))
    private Quantity quantity;

    public CartItem() {
    }

    public CartItem(Long id, User user, Product product, Integer quantity) {
        this(user, product);
        this.id = id;
        this.quantity = new Quantity(quantity);
    }

    public CartItem(User user, Product product) {
        this.user = user;
        this.product = product;
        this.quantity = new Quantity();
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity.getQuantity();
    }

    public CartItem addQuantity() {
        return new CartItem(this.id, this.user, this.product, this.getQuantity() + ADD_COUNT);
    }

    public CartItem updateQuantity(Integer quantity) {
        return new CartItem(this.id, this.user, this.product, quantity);
    }

    public boolean isNotUserMatch(User user) {
        return !this.user.equals(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartItem cartItem = (CartItem) o;
        return Objects.equals(id, cartItem.id) && Objects.equals(user,
                cartItem.user) && Objects.equals(product, cartItem.product)
                && Objects.equals(quantity, cartItem.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, product, quantity);
    }
}
