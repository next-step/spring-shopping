package shopping.mart.persist;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import shopping.mart.domain.Cart;
import shopping.mart.domain.Product;
import shopping.mart.persist.entity.CartEntity;
import shopping.mart.persist.entity.CartProductEntity;
import shopping.mart.persist.entity.ProductEntity;
import shopping.mart.persist.repository.CartJpaRepository;
import shopping.mart.persist.repository.ProductJpaRepository;

@Repository
public class CartPersistService {

    private static final Object LOCK = new Object();

    private final CartJpaRepository cartJpaRepository;
    private final ProductJpaRepository productJpaRepository;

    CartPersistService(CartJpaRepository cartJpaRepository,
        ProductJpaRepository productJpaRepository) {
        this.cartJpaRepository = cartJpaRepository;
        this.productJpaRepository = productJpaRepository;
    }

    public void persistCart(Cart cart) {
        CartEntity cartEntity = cartJpaRepository.getReferenceById(cart.getCartId());
        cartEntity.persist(cart);
    }

    public boolean existCartByUserId(Long userId) {
        return cartJpaRepository.existsByUserId(userId);
    }

    public Cart newCart(long userId) {
        synchronized (LOCK) {
            if (cartJpaRepository.findByUserId(userId).isEmpty()) {
                cartJpaRepository.save(new CartEntity(null, userId, null));
            }
        }
        return getEmptyCart(userId);
    }

    private Cart getEmptyCart(long userId) {
        CartEntity cartEntity = cartJpaRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalStateException(MessageFormat.format(
                "userId\"{0}\"에 해당하는 Cart가 존재하지 않습니다.", userId)));
        return new Cart(cartEntity.getId(), cartEntity.getUserId());
    }

    public Cart getByUserId(long userId) {
        CartEntity cartEntity = cartJpaRepository.getReferenceByUserId(userId);
        return cartEntityToCart(cartEntity);
    }

    private Cart cartEntityToCart(CartEntity cartEntity) {
        Cart cart = new Cart(cartEntity.getId(), cartEntity.getUserId());
        if (cartEntity.isEmptyCart()) {
            return cart;
        }

        List<ProductEntity> productEntities =
            productJpaRepository.findAllById(cartEntity.getCartProductEntities().stream()
                .map(CartProductEntity::getProductId)
                .collect(Collectors.toList()));

        addToCart(cart,
            productEntitiesToProducts(productEntities, cartEntity.getCartProductEntities()));
        return cart;
    }

    private void addToCart(Cart cart, Map<Product, Integer> products) {
        products.forEach((key, value) -> cart.addProduct(key));
        products.forEach(cart::updateProduct);
    }

    private Map<Product, Integer> productEntitiesToProducts(List<ProductEntity> productEntities,
        List<CartProductEntity> cartProductEntities) {

        Map<Long, Integer> productEntityCounts = cartProductEntities.stream()
            .collect(Collectors.toMap(CartProductEntity::getProductId,
                CartProductEntity::getCount));

        return productEntities.stream().collect(Collectors.toMap(
            productEntity -> new Product(productEntity.getId(), productEntity.getName(),
                productEntity.getImageUrl(), productEntity.getPrice()),
            productEntity -> productEntityCounts.get(productEntity.getId())
        ));
    }
}
