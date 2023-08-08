package shopping.mart.repository.entity;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import shopping.mart.app.domain.Cart;
import shopping.mart.app.domain.Product;

@Entity
@Table(name = "cart")
public class CartEntity extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "user_id", unique = true)
    private Long userId;

    @Column(name = "cart_product_entities")
    @OneToMany(mappedBy = "cartEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartProductEntity> cartProductEntities;

    public CartEntity() {
    }

    public CartEntity(Long id, Long userId, List<CartProductEntity> cartProductEntities) {
        this.id = id;
        this.userId = userId;
        this.cartProductEntities =
            cartProductEntities == null ? new ArrayList<>() : cartProductEntities;
    }

    public Cart toDomain(List<Product> products) {
        Cart cart = new Cart(id, userId);
        products.forEach(cart::addProduct);
        products.forEach(product -> cart.updateProduct(product, getProductCount(product)));
        return cart;
    }

    private int getProductCount(Product product) {
        return cartProductEntities.stream()
            .filter(cartProductEntity -> cartProductEntity.isMatchedProduct(product))
            .findAny()
            .orElseThrow(() -> new IllegalStateException(MessageFormat.format(
                "product id\"{0}\"에 해당하는 product가 cart에 저장되어 있지 않음", product.getId())))
            .getCount();
    }

    public List<Long> getRegisteredProductIds() {
        return cartProductEntities.stream()
            .map(CartProductEntity::getProductId)
            .collect(Collectors.toList());
    }

    public void persist(Cart cart) {
        setNewCartProductEntities(cart.getProductCounts());
        updateCartProductEntities(cart.getProductCounts());
        deleteCartProductEntities(cart.getProductCounts());
    }

    private void setNewCartProductEntities(Map<Product, Integer> productCounts) {
        for (Entry<Product, Integer> entry : productCounts.entrySet()) {
            if (existProduct(entry.getKey().getId())) {
                continue;
            }
            cartProductEntities.add(
                new CartProductEntity(this, entry.getKey().getId(), entry.getValue()));
        }
    }

    private boolean existProduct(long productId) {
        return cartProductEntities.stream()
            .anyMatch(cartProductEntity -> cartProductEntity.getProductId().equals(productId));
    }

    private void updateCartProductEntities(Map<Product, Integer> productCounts) {
        for (Entry<Product, Integer> entry : productCounts.entrySet()) {
            cartProductEntities.stream()
                .filter(cartProductEntity -> cartProductEntity.getProductId()
                    .equals(entry.getKey().getId()))
                .findAny()
                .ifPresent(cartProductEntity -> cartProductEntity.setCount(entry.getValue()));
        }
    }

    private void deleteCartProductEntities(Map<Product, Integer> productCounts) {
        Set<Long> productIds = productsToIds(productCounts);

        List<CartProductEntity> removeTargets = new ArrayList<>();
        for (CartProductEntity cartProductEntity : cartProductEntities) {
            if (productIds.contains(cartProductEntity.getProductId())) {
                continue;
            }
            removeTargets.add(cartProductEntity);
        }

        removeTargets.forEach(cartProductEntities::remove);
    }

    private Set<Long> productsToIds(Map<Product, Integer> productCounts) {
        return productCounts.keySet().stream()
            .map(Product::getId)
            .collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }
}
