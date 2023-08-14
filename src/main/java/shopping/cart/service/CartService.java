package shopping.cart.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.cart.domain.CartProduct;
import shopping.cart.dto.request.CartProductCreateRequest;
import shopping.cart.dto.request.CartProductQuantityUpdateRequest;
import shopping.cart.dto.response.CartResponse;
import shopping.cart.repository.CartProductRepository;
import shopping.global.exception.ShoppingException;
import shopping.product.domain.Product;
import shopping.product.repository.ProductRepository;

@Service
@Transactional(readOnly = true)
public class CartService {

    private final CartProductRepository cartProductRepository;
    private final ProductRepository productRepository;

    public CartService(
        final CartProductRepository cartProductRepository,
        final ProductRepository productRepository
    ) {
        this.cartProductRepository = cartProductRepository;
        this.productRepository = productRepository;
    }

    public List<CartResponse> findAllCartProducts(final Long memberId) {
        List<CartProduct> cartProducts = cartProductRepository.findAllByMemberId(
            memberId);
        return cartProducts.stream()
            .map(CartResponse::from)
            .collect(Collectors.toList());
    }

    @Transactional
    public CartProduct createCartProduct(
        final Long memberId,
        final CartProductCreateRequest cartProductCreateRequest
    ) {
        final Long productId = cartProductCreateRequest.getProductId();

        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ShoppingException("존재하지 않는 상품입니다. 입력값: " + productId));
        cartProductRepository.findByMemberIdAndProduct(memberId, product)
            .ifPresent(cartProduct -> {
                throw new ShoppingException(
                    "이미 장바구니에 담긴 상품입니다. 입력값: " + cartProduct.getProduct().getId());
            });

        return cartProductRepository.save(new CartProduct(memberId, product));
    }

    @Transactional
    public void updateCartProductQuantity(
        final Long cartProductId,
        final Long memberId,
        final CartProductQuantityUpdateRequest request
    ) {
        if (request.getQuantity() == 0) {
            deleteCartProduct(cartProductId, memberId);
            return;
        }

        final CartProduct cartProduct = cartProductRepository
            .findByIdAndMemberId(cartProductId, memberId)
            .orElseThrow(
                () -> new ShoppingException("존재하지 않는 장바구니 상품입니다. 장바구니 상품 정보: " + cartProductId)
            );

        cartProduct.updateQuantity(request.getQuantity());
    }

    @Transactional
    public void deleteCartProduct(final Long cartProductId, final Long memberId) {
        cartProductRepository.deleteByIdAndMemberId(cartProductId, memberId);
    }
}

