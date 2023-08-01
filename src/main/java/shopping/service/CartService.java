package shopping.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.entity.CartItemEntity;
import shopping.domain.entity.ProductEntity;
import shopping.domain.entity.UserEntity;
import shopping.dto.request.CartItemAddRequest;
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
            .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 상품 ID입니다."));
        final CartItemEntity cartItemEntity = new CartItemEntity(userProxy, product);
        cartItemRepository.save(cartItemEntity);
    }

}
