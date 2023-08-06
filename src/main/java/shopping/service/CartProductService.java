package shopping.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.cart.CartProduct;
import shopping.dto.request.CartProductCreateRequest;
import shopping.dto.request.CartProductQuantityUpdateRequest;
import shopping.exception.CartExceptionType;
import shopping.exception.ProductExceptionType;
import shopping.exception.ShoppingException;
import shopping.repository.CartProductRepository;
import shopping.repository.ProductRepository;

@Service
@Transactional(readOnly = true)
public class CartProductService {

    private final CartProductRepository cartProductRepository;
    private final ProductRepository productRepository;

    public CartProductService(
        final CartProductRepository cartProductRepository,
        final ProductRepository productRepository
    ) {
        this.cartProductRepository = cartProductRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public CartProduct createCartProduct(
        final Long memberId,
        final CartProductCreateRequest cartProductCreateRequest
    ) {
        final Long productId = cartProductCreateRequest.getProductId();

        productRepository.findById(productId)
            .orElseThrow(
                () -> new ShoppingException(ProductExceptionType.NOT_FOUND_PRODUCT, productId)
            );

        cartProductRepository.findByMemberIdAndProductId(memberId, productId)
            .ifPresent(cartProduct -> {
                throw new ShoppingException(
                    CartExceptionType.DUPLICATED_CART_PRODUCT, cartProduct.getProductId()
                );
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
                () -> new ShoppingException(
                    CartExceptionType.NOT_FOUND_CART_PRODUCT, cartProductId
                )
            );

        cartProduct.updateQuantity(request.getQuantity());
    }

    @Transactional
    public void deleteCartProduct(final Long cartProductId, final Long memberId) {
        cartProductRepository.deleteByIdAndMemberId(cartProductId, memberId);
    }
}
