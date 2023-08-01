package shopping.persist;

import java.text.MessageFormat;
import java.util.List;
import org.springframework.stereotype.Repository;
import shopping.domain.Cart;
import shopping.domain.Product;
import shopping.persist.entity.CartEntity;
import shopping.persist.entity.CartProductEntity;
import shopping.persist.entity.ProductEntity;
import shopping.persist.repository.CartJpaRepository;
import shopping.persist.repository.CartProductJpaRepository;

@Repository
public class CartRepository {

    private static final Object LOCK = new Object();

    private final CartJpaRepository cartJpaRepository;
    private final CartProductJpaRepository cartProductJpaRepository;

    CartRepository(CartJpaRepository cartJpaRepository, CartProductJpaRepository cartProductJpaRepository) {
        this.cartJpaRepository = cartJpaRepository;
        this.cartProductJpaRepository = cartProductJpaRepository;
    }

    public boolean existCartByUserId(long userId) {
        return cartJpaRepository.existsByUserId(userId);
    }

    public Cart newCart(long userId) {
        synchronized (LOCK) {
            if (cartJpaRepository.findByUserId(userId).isEmpty()) {
                cartJpaRepository.save(new CartEntity(null, userId));
            }
        }
        return getEmptyCart(userId);
    }

    public Cart getByUserId(long userId) {
        List<CartProductEntity> cartProductEntities = cartProductJpaRepository.findAllByUserId(userId);
        if (cartProductEntities.isEmpty()) {
            return getEmptyCart(userId);
        }

        CartEntity persistCart = cartProductEntities.get(0).getCartEntity();
        Cart cart = new Cart(persistCart.getId(), persistCart.getUserId());
        cartProductEntities.forEach(
                cartProductEntity -> cart.addProduct(productEntityToDomain(cartProductEntity.getProductEntity())));
        return cart;
    }

    private Cart getEmptyCart(long userId) {
        CartEntity cartEntity = cartJpaRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException(MessageFormat.format(
                        "userId\"{0}\"에 해당하는 Cart가 존재하지 않습니다.", userId)));
        return new Cart(cartEntity.getId(), cartEntity.getUserId());
    }

    private Product productEntityToDomain(ProductEntity productEntity) {
        return new Product(productEntity.getId(), productEntity.getName(), productEntity.getImageUrl(),
                productEntity.getPrice());
    }

    public void updateCart(Cart cart) {
    }
}
