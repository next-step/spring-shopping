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
        final UserEntity userProxy = userRepository.getReferenceById(userId);
        final ProductEntity product = productRepository.findById(cartItemAddRequest.getProductId())
            .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 장바구니 상품입니다."));
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
        cartItem.updateQuantity(cartItemUpdateRequest.getQuantity());
    }
}
