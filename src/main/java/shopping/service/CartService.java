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
        final ProductEntity product = productRepository.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 장바구니 상품입니다."));

        if (cartItemRepository.existsByUserIdAndProductId(userId, productId)) {
            throw new ShoppingException(ErrorCode.DUPULICATE_CART_ITEM);
        }

        final CartItemEntity cartItemEntity = new CartItemEntity(userProxy, product);
        cartItemRepository.save(cartItemEntity);
    }

    public List<CartItemResponse> getCartItems(final Long userId) {
        return cartItemRepository.findByUserId(userId).stream()
            .map(CartItemResponse::of)
            .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void updateCartItemQuantity(final Long cartItemId,
        final CartItemUpdateRequest cartItemUpdateRequest, final Long userId) {
        final CartItemEntity cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 장바구니 상품입니다."));

        if (!Objects.equals(cartItem.getUser().getId(), userId)) {
            throw new IllegalArgumentException("유저의 장바구니 상품이 아닙니다.");
        }

        if (cartItemUpdateRequest.getQuantity() < ITEM_MIN_QUANTITY
            || ITEM_MAX_QUANTITY < cartItemUpdateRequest.getQuantity()) {
            throw new ShoppingException(ErrorCode.INVALID_CART_ITEM_QUANTITY);
        }

        cartItem.updateQuantity(cartItemUpdateRequest.getQuantity());
    }

    @Transactional
    public void removeCartItem(final Long cartItemId, final Long userId) {
        final CartItemEntity cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 장바구니 상품입니다."));
        if (!Objects.equals(cartItem.getUser().getId(), userId)) {
            throw new IllegalArgumentException("유저의 장바구니 상품이 아닙니다.");
        }
        cartItemRepository.delete(cartItem);
    }
}
