package shopping.mart.persist.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import shopping.mart.domain.Cart;
import shopping.mart.domain.Product;

@Entity
@Table(name = "cart")
public class CartEntity extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "user_id")
    private Long userId;

    @Column(name = "cart_product_entities")
    @OneToMany(mappedBy = "cartEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartProductEntity> cartProductEntities;

    private CartEntity() {
    }

    public void persist(Cart cart) {
        setNewCartProductEntities(cart.getProductCounts());
        updateCartProductEntities(cart.getProductCounts());
        deleteCartProductEntities(cart.getProductCounts());
    }

    private void setNewCartProductEntities(Map<Product, Integer> productCounts) {
        Map<Long, CartProductEntity> existCartProductEntityFinder = getCartProductEntityFinder();

        productCounts.entrySet().stream()
            .filter(entry -> !existCartProductEntityFinder.containsKey(entry.getKey().getId()))
            .forEach(entry -> this.cartProductEntities.add(
                new CartProductEntity(this, entry.getKey().getId(), entry.getValue())));
    }

    private void updateCartProductEntities(Map<Product, Integer> productCounts) {
        Map<Long, CartProductEntity> existCartProductEntityFinder = getCartProductEntityFinder();

        productCounts.forEach((key, value) -> existCartProductEntityFinder.get(key.getId())
            .setCount(value));
    }

    private Map<Long, CartProductEntity> getCartProductEntityFinder() {
        return this.cartProductEntities.stream()
            .collect(Collectors.toMap(CartProductEntity::getProductId,
                cartProductEntity -> cartProductEntity));
    }

    private void deleteCartProductEntities(Map<Product, Integer> productCounts) {
        Set<Long> productIds = productCounts.keySet().stream()
            .map(Product::getId)
            .collect(Collectors.toSet());

        List<CartProductEntity> removeTargets = cartProductEntities.stream()
            .filter(cartProductEntity -> !productIds.contains(cartProductEntity.getProductId()))
            .collect(Collectors.toList());

        removeTargets.forEach(cartProductEntities::remove);
    }

    public CartEntity(Long id, Long userId, List<CartProductEntity> cartProductEntities) {
        this.id = id;
        this.userId = userId;
        this.cartProductEntities =
            cartProductEntities == null ? new ArrayList<>() : cartProductEntities;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public List<CartProductEntity> getCartProductEntities() {
        return cartProductEntities;
    }

    public boolean isEmptyCart() {
        return cartProductEntities.isEmpty();
    }
}
