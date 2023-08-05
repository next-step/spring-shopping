package shopping.cart.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.auth.domain.entity.User;
import shopping.auth.repository.UserRepository;
import shopping.cart.domain.entity.CartItem;
import shopping.cart.domain.entity.Product;
import shopping.cart.dto.request.CartItemInsertRequest;
import shopping.cart.dto.request.CartItemUpdateRequest;
import shopping.cart.dto.response.CartItemResponse;
import shopping.cart.repository.CartItemRepository;
import shopping.cart.repository.ProductRepository;
import shopping.common.exception.ErrorCode;
import shopping.common.exception.ShoppingException;

@Service
@Transactional(readOnly = true)
public class CartService {

    public static final int ITEM_MIN_QUANTITY = 1;
    public static final int ITEM_MAX_QUANTITY = 1000;
    public static final int QUANTITY_ZERO = 0;

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
    public void insertCartItem(CartItemInsertRequest cartItemInsertRequest, Long userId) {
        final Long productId = cartItemInsertRequest.getProductId();
        final Product product = findProductBy(productId);
        validateDuplicateCartItem(userId, productId);

        final User userProxy = userRepository.getReferenceById(userId);
        final CartItem cartItem = new CartItem(userProxy, product);
        cartItemRepository.save(cartItem);
    }

    public List<CartItemResponse> getCartItems(final Long userId) {
        return cartItemRepository.findByUserId(userId).stream()
            .map(CartItemResponse::from)
            .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void updateCartItemQuantity(final Long cartItemId,
        final CartItemUpdateRequest cartItemUpdateRequest, final Long userId) {
        final CartItem cartItem = findCartItemEntityBy(cartItemId);
        validateUserHasCartItem(userId, cartItem);

        final int updateQuantity = cartItemUpdateRequest.getQuantity();

        if (updateQuantity == QUANTITY_ZERO) {
            cartItemRepository.delete(cartItem);
            return;
        }

        validateCartItemQuantity(updateQuantity);
        cartItem.updateQuantity(updateQuantity);
    }

    @Transactional
    public void removeCartItem(final Long cartItemId, final Long userId) {
        final CartItem cartItem = findCartItemEntityBy(cartItemId);
        validateUserHasCartItem(userId, cartItem);
        cartItemRepository.delete(cartItem);
    }

    private void validateUserHasCartItem(final Long userId, final CartItem cartItem) {
        if (!Objects.equals(cartItem.getUser().getId(), userId)) {
            throw new ShoppingException(ErrorCode.INVALID_CART_ITEM);
        }
    }

    private CartItem findCartItemEntityBy(final Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new ShoppingException(ErrorCode.INVALID_CART_ITEM));
    }

    private Product findProductBy(final Long productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new ShoppingException(ErrorCode.INVALID_PRODUCT));
    }

    private void validateDuplicateCartItem(final Long userId, final Long productId) {
        if (cartItemRepository.existsByUserIdAndProductId(userId, productId)) {
            throw new ShoppingException(ErrorCode.DUPLICATE_CART_ITEM);
        }
    }

    private void validateCartItemQuantity(
        final int quantity) {
        if (isOutOfBound(quantity)) {
            throw new ShoppingException(ErrorCode.INVALID_CART_ITEM_QUANTITY);
        }
    }

    private boolean isOutOfBound(final int quantity) {
        return quantity < ITEM_MIN_QUANTITY || ITEM_MAX_QUANTITY < quantity;
    }
}
