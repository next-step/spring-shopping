package shopping.mart.persist;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    private static final int INITIAL_COUNT = 1;

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
        cartJpaRepository.save(new CartEntity(null, userId));

        return getEmptyCart(userId);
    }

    public Cart getByUserId(long userId) {
        if (!existCartByUserId(userId)) {
            newCart(userId);
        }

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

        updateCount(cart, cartProductEntities);
        cartProductJpaRepository.deleteAllInBatch(getDeletedProducts(cart, cartProductEntities));
    }

    private void updateCount(Cart cart, List<CartProductEntity> cartProductEntities) {
        Map<Long, CartProductEntity> idIndexedCartProductEntities = cartProductEntities.stream()
                .collect(toMap(CartProductEntity::getProductId, identity()));

        cart.getProductCounts().forEach((key, value) -> idIndexedCartProductEntities.get(key.getId()).setCount(value));
    }

    private List<CartProductEntity> getDeletedProducts(Cart cart, List<CartProductEntity> cartProductEntities) {
        List<CartProductEntity> deleteCartProductEntities = getZeroCountedProductEntities(cartProductEntities);

        deleteCartProductEntities.addAll(findDeletedCartProductEntities(cart, cartProductEntities));

        return deleteCartProductEntities;
    }

    private List<CartProductEntity> getZeroCountedProductEntities(List<CartProductEntity> cartProductEntities) {
        return cartProductEntities.stream()
                .filter(CartProductEntity::hasZeroCount)
                .collect(toList());
    }

    private List<CartProductEntity> findDeletedCartProductEntities(Cart cart,
            List<CartProductEntity> cartProductEntities) {
        Set<Long> persistedProductIds = cartProductEntities.stream()
                .map(CartProductEntity::getProductId)
                .collect(toSet());

        Set<Long> domainProductIds = cart.getProductCounts().keySet().stream()
                .map(Product::getId)
                .collect(toSet());

        persistedProductIds.removeAll(domainProductIds);

        Map<Long, CartProductEntity> productIdIndexedCartProductEntities = cartProductEntities.stream()
                .collect(toMap(CartProductEntity::getProductId, identity()));

        return persistedProductIds.stream()
                .map(productIdIndexedCartProductEntities::get)
                .collect(toList());
    }

    public void addProduct(Cart cart, Product product) {
        CartEntity cartEntity = cartJpaRepository.findById(cart.getCartId())
                .orElseThrow(() -> new IllegalStateException(
                        MessageFormat.format("id \"{0}\" 에 해당하는 cart를 찾을 수 없습니다", cart.getCartId())));
        ProductEntity productEntity = productJpaRepository.findById(product.getId())
                .orElseThrow(() -> new IllegalStateException(
                        MessageFormat.format("id \"{0}\" 에 해당하는 product를 찾을 수 없습니다", product.getId())));

        CartProductEntity cartProductEntity = new CartProductEntity(null, cartEntity, productEntity, INITIAL_COUNT);

        cartProductJpaRepository.save(cartProductEntity);
    }

    public void deleteAllProducts(Long userId) {
        CartEntity cartEntity = cartJpaRepository.findByUserId(userId)
                        .orElseThrow(() -> new IllegalStateException(MessageFormat.format(
                                "userId\"{0}\"에 해당하는 Cart가 존재하지 않습니다.", userId)));

        List<CartProductEntity> cartProducts = cartProductJpaRepository.findAllByCartEntity(cartEntity);

        cartProductJpaRepository.deleteAllInBatch(cartProducts);
    }
}
