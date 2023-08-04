package shopping.mart.persist;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import shopping.core.entity.CartEntity;
import shopping.core.entity.CartProductEntity;
import shopping.core.entity.ProductEntity;
import shopping.mart.domain.Cart;
import shopping.mart.domain.Product;
import shopping.mart.persist.repository.CartJpaRepository;
import shopping.mart.persist.repository.CartProductJpaRepository;
import shopping.mart.persist.repository.ProductJpaRepository;

@Repository
public class CartRepository {

    private static final Object LOCK = new Object();

    private final CartJpaRepository cartJpaRepository;
    private final CartProductJpaRepository cartProductJpaRepository;
    private final ProductJpaRepository productJpaRepository;

    CartRepository(CartJpaRepository cartJpaRepository, CartProductJpaRepository cartProductJpaRepository,
            ProductJpaRepository productJpaRepository) {
        this.cartJpaRepository = cartJpaRepository;
        this.cartProductJpaRepository = cartProductJpaRepository;
        this.productJpaRepository = productJpaRepository;
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
        cartProductEntities.forEach(cartProductEntity -> addProductToCart(cart, cartProductEntity));

        return cart;
    }

    private Cart getEmptyCart(long userId) {
        CartEntity cartEntity = cartJpaRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException(MessageFormat.format(
                        "userId\"{0}\"에 해당하는 Cart가 존재하지 않습니다.", userId)));

        return new Cart(cartEntity.getId(), cartEntity.getUserId());
    }

    private void addProductToCart(Cart cart, CartProductEntity cartProductEntity) {

        ProductEntity productEntity = cartProductEntity.getProductEntity();

        Product product = new Product(productEntity.getId(), productEntity.getName(), productEntity.getImageUrl(),
                productEntity.getPrice());

        cart.addProduct(product);
        cart.updateProduct(product, cartProductEntity.getCount());
    }

    public void updateCart(Cart cart) {
        CartEntity cartEntity = cartJpaRepository.findById(cart.getCartId())
                .orElseThrow(() -> new IllegalStateException(
                        MessageFormat.format("cartId\"{0}\" 에 해당하는 Cart를 찾을 수 없습니다.", cart.getCartId())));
        List<CartProductEntity> cartProductEntities = cartProductJpaRepository.findAllByCartEntity(cartEntity);

        Map<Long, CartProductEntity> idIndexedCartProductEntities = cartProductEntities.stream()
                .collect(Collectors.toMap(cartProductEntity -> cartProductEntity.getProductEntity().getId(),
                        cartProductEntity -> cartProductEntity));

        cart.getProductCounts().forEach((key, value) -> idIndexedCartProductEntities.get(key.getId()).setCount(value));

        List<CartProductEntity> deleteCartProductEntities = getDeletedProducts(cart, cartProductEntities);

        cartProductJpaRepository.deleteAllInBatch(deleteCartProductEntities);
    }

    private List<CartProductEntity> getDeletedProducts(Cart cart, List<CartProductEntity> cartProductEntities) {
        List<CartProductEntity> deleteCartProductEntities = getZeroCountedProductEntities(
                cartProductEntities);

        addDeletedCartProductEntities(cart, cartProductEntities, deleteCartProductEntities);

        return deleteCartProductEntities;
    }

    private List<CartProductEntity> getZeroCountedProductEntities(List<CartProductEntity> cartProductEntities) {
        return cartProductEntities.stream()
                .filter(cartProductEntity -> cartProductEntity.getCount() == 0)
                .collect(Collectors.toList());
    }

    private void addDeletedCartProductEntities(Cart cart, List<CartProductEntity> cartProductEntities,
            List<CartProductEntity> deleteCartProductEntities) {
        Set<Long> persistedProductIds = cartProductEntities.stream()
                .map(cartProductEntity -> cartProductEntity.getProductEntity().getId())
                .collect(Collectors.toSet());

        Set<Long> domainProductIds = cart.getProductCounts().keySet().stream()
                .map(Product::getId)
                .collect(Collectors.toSet());

        persistedProductIds.removeAll(domainProductIds);

        Map<Long, CartProductEntity> productIdIndexedCartProductEntities = cartProductEntities.stream()
                .collect(Collectors.toMap(cartProductEntity -> cartProductEntity.getProductEntity().getId(),
                        cartProductEntity -> cartProductEntity));

        persistedProductIds.forEach(deleteTarget -> deleteCartProductEntities.add(
                productIdIndexedCartProductEntities.get(deleteTarget)));
    }

    public void addProduct(Cart cart, Product product) {
        CartEntity cartEntity = cartJpaRepository.findById(cart.getCartId())
                .orElseThrow(() -> new IllegalStateException(
                        MessageFormat.format("id \"{0}\" 에 해당하는 cart를 찾을 수 없습니다", cart.getCartId())));
        ProductEntity productEntity = productJpaRepository.findById(product.getId())
                .orElseThrow(() -> new IllegalStateException(
                        MessageFormat.format("id \"{0}\" 에 해당하는 product를 찾을 수 없습니다", product.getId())));

        CartProductEntity cartProductEntity = new CartProductEntity(null, cartEntity, productEntity, 1);

        cartProductJpaRepository.save(cartProductEntity);
    }
}
