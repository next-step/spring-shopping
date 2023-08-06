package shopping.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.cart.Cart;
import shopping.domain.cart.CartProduct;
import shopping.domain.product.Product;
import shopping.dto.request.CartProductCreateRequest;
import shopping.dto.request.CartProductQuantityUpdateRequest;
import shopping.dto.response.CartResponse;
import shopping.exception.ShoppingException;
import shopping.repository.CartProductRepository;
import shopping.repository.ProductRepository;

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
        final List<CartProduct> cartProducts = cartProductRepository.findByMemberId(memberId);
        final Cart cart = new Cart(cartProducts);

        final List<Long> cartProductIds = cartProducts.stream()
            .map(CartProduct::getProductId)
            .collect(Collectors.toList());
        final List<Product> products = productRepository.findAllById(cartProductIds);

        return products.stream()
            .map(product -> CartResponse.of(cart.findByProductId(product.getId()), product))
            .collect(Collectors.toList());
    }

    @Transactional
    public CartProduct createCartProduct(
        final Long memberId,
        final CartProductCreateRequest cartProductCreateRequest
    ) {
        final Long productId = cartProductCreateRequest.getProductId();

        productRepository.findById(productId)
            .orElseThrow(() -> new ShoppingException("존재하지 않는 상품입니다. 입력값: " + productId));

        cartProductRepository.findByMemberIdAndProductId(memberId, productId)
            .ifPresent(cartProduct -> {
                throw new ShoppingException(
                    "이미 장바구니에 담긴 상품입니다. 입력값: " + cartProduct.getProductId());
            });

        return cartProductRepository.save(new CartProduct(memberId, productId));
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

