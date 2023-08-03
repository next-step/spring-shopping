package shopping.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public static final int ITEM_MIN_QUANTITY = 1;
    public static final int ITEM_MAX_QUANTITY = 1000;
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
        final UserEntity userProxy = userRepository.getReferenceById(userId);
        final Long productId = cartItemAddRequest.getProductId();
        final ProductEntity product = findProductBy(productId);

        validateDuplicateCartItem(userId, productId);

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
        final CartItemEntity cartItem = findCartItemEntityBy(cartItemId);

        validateUserHasCartItem(userId, cartItem);

        validateCartItemQuantity(cartItemUpdateRequest);

        cartItem.updateQuantity(cartItemUpdateRequest.getQuantity());
    }

    @Transactional
    public void removeCartItem(final Long cartItemId, final Long userId) {
        final CartItemEntity cartItem = findCartItemEntityBy(cartItemId);
        validateUserHasCartItem(userId, cartItem);
        cartItemRepository.delete(cartItem);
    }

    private void validateUserHasCartItem(final Long userId, final CartItemEntity cartItem) {
        if (!Objects.equals(cartItem.getUser().getId(), userId)) {
            throw new ShoppingException(ErrorCode.INVALID_CART_ITEM);
        }
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

    private static void validateCartItemQuantity(
        final CartItemUpdateRequest cartItemUpdateRequest) {
        if (cartItemUpdateRequest.getQuantity() < ITEM_MIN_QUANTITY
            || ITEM_MAX_QUANTITY < cartItemUpdateRequest.getQuantity()) {
            throw new ShoppingException(ErrorCode.INVALID_CART_ITEM_QUANTITY);
        }
    }
}
