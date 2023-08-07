package shopping.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.CartItem;
import shopping.domain.entity.CartItemEntity;
import shopping.domain.entity.ProductEntity;
import shopping.domain.entity.UserEntity;
import shopping.dto.request.CartItemAddRequest;
import shopping.dto.request.CartItemUpdateRequest;
import shopping.dto.response.CartItemResponse;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;
import shopping.repository.CartItemRepository;
import shopping.repository.ProductRepository;
import shopping.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartService(final CartItemRepository cartItemRepository,
        final UserRepository userRepository,
        final ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void addCartItem(CartItemAddRequest cartItemAddRequest, Long userId) {
        final Long productId = cartItemAddRequest.getProductId();
        final ProductEntity product = findProductBy(productId);
        validateDuplicateCartItem(userId, productId);

        final UserEntity userProxy = userRepository.getReferenceById(userId);
        final CartItemEntity cartItemEntity = new CartItemEntity(userProxy, product);
        cartItemRepository.save(cartItemEntity);
    }

    public List<CartItemResponse> getCartItems(final Long userId) {
        return cartItemRepository.findByUserId(userId).stream()
            .map(CartItemResponse::from)
            .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void updateCartItemQuantity(final Long cartItemId,
        final CartItemUpdateRequest cartItemUpdateRequest, final Long userId) {
        final int updateQuantity = cartItemUpdateRequest.getQuantity();
        final CartItemEntity cartItemEntity = findCartItemEntityBy(cartItemId);

        CartItem cartItem = CartItem.of(cartItemEntity, updateQuantity);
        cartItem.validateUser(userId);
        if (cartItem.isQuantityZero()) {
            cartItemRepository.delete(cartItemEntity);
            return;
        }

        cartItemEntity.updateQuantity(updateQuantity);
    }

    @Transactional
    public void removeCartItem(final Long cartItemId, final Long userId) {
        final CartItemEntity cartItemEntity = findCartItemEntityBy(cartItemId);
        final CartItem cartItem = CartItem.from(cartItemEntity);
        cartItem.validateUser(userId);
        cartItemRepository.delete(cartItemEntity);
    }

    private CartItemEntity findCartItemEntityBy(final Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new ShoppingException(ErrorCode.INVALID_CART_ITEM));
    }

    private ProductEntity findProductBy(final Long productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new ShoppingException(ErrorCode.INVALID_PRODUCT));
    }

    private void validateDuplicateCartItem(final Long userId, final Long productId) {
        if (cartItemRepository.existsByUserIdAndProductId(userId, productId)) {
            throw new ShoppingException(ErrorCode.DUPLICATE_CART_ITEM);
        }
    }

}
