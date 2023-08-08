package shopping.mart.repository;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import shopping.mart.app.domain.Cart;
import shopping.mart.app.spi.CartRepository;
import shopping.mart.repository.entity.CartEntity;
import shopping.mart.repository.entity.ProductEntity;

@Repository
public class CartPersistService implements CartRepository {

    private final CartJpaRepository cartJpaRepository;
    private final ProductJpaRepository productJpaRepository;

    public CartPersistService(CartJpaRepository cartJpaRepository,
            ProductJpaRepository productJpaRepository) {
        this.cartJpaRepository = cartJpaRepository;
        this.productJpaRepository = productJpaRepository;
    }

    @Override
    public void persistCart(Cart cart) {
        CartEntity cartEntity = cartJpaRepository.getReferenceById(cart.getCartId());
        cartEntity.persist(cart);
    }

    @Override
    public boolean existCartByUserId(Long userId) {
        return cartJpaRepository.existsByUserId(userId);
    }

    @Override
    public Cart newCart(long userId) {
        cartJpaRepository.findByUserId(userId).ifPresentOrElse(cartEntity -> {
        }, () -> cartJpaRepository.save(new CartEntity(null, userId, null)));

        return getByUserId(userId);
    }

    @Override
    public Cart getByUserId(long userId) {
        CartEntity cartEntity = cartJpaRepository.getReferenceByUserId(userId);

        return addProductToCart(cartEntity);
    }

    @Override
    public Cart getById(long cartId) {
        CartEntity cartEntity = cartJpaRepository.getReferenceById(cartId);

        return addProductToCart(cartEntity);
    }

    private Cart addProductToCart(CartEntity cartEntity) {
        List<ProductEntity> productEntities = productJpaRepository.findAllById(
                cartEntity.getRegisteredProductIds());

        return cartEntity.toDomain(productEntities.stream()
                .map(ProductEntity::toDomain)
                .collect(Collectors.toList()));
    }
}
