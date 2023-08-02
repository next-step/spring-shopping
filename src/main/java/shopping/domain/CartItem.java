package shopping.domain;

import javax.persistence.Column;
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

    @Column(name = "quantity", nullable = false)
    private Integer quantity = 1;

    public CartItem() {
    }

    public CartItem(Long id, User user, Product product, Integer quantity) {
        this(user, product);
        this.id = id;
        this.quantity = quantity;
    }

    public CartItem(User user, Product product) {
        this.user = user;
        this.product = product;
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
        return quantity;
    }

    public CartItem addQuantity(Integer count) {
        return new CartItem(this.id, this.user, this.product, this.quantity + count);
    }

    public CartItem updateQuantity(Integer quantity) {
        return new CartItem(this.id, this.user, this.product, quantity);
    }
}
