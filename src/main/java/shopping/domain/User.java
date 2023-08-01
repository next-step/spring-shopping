package shopping.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50)
    private String email;
    @Column(nullable = false)
    private String password;
    @OneToMany
    @JoinColumn(name = "user_id")
    private List<CartItem> cartItems = new ArrayList<>();

    protected User() {

    }

    public User(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public CartItem addCartItem(Product product) {
        CartItem item = findCartItem(product)
                .orElseGet(() -> {
                    CartItem newItem = new CartItem(this, product, 0);
                    cartItems.add(newItem);
                    return newItem;
                });
        item.increaseQuantity();
        return item;
    }

    private Optional<CartItem> findCartItem(Product product) {
        return cartItems.stream()
                .filter(cartItem -> cartItem.getProduct().equals(product))
                .findAny();
    }

    public boolean containsCartItem(CartItem cartItem) {
        return cartItems.contains(cartItem);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }
}
