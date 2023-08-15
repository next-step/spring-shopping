package shopping.domain;

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

    protected CartItem() {
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

    public CartItem updateQuantity(Integer quantity) {
        return new CartItem(this.id, this.user, this.product, quantity);
    }

    public boolean isDifferentUser(User user) {
        if (this.user == null) {
            return true;
        }
        return !this.user.equals(user);
    }

    public Long totalPrice() {
        return quantity.getQuantity() * product.getPrice();
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
        return Objects.equals(id, cartItem.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
