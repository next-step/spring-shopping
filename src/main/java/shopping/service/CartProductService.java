package shopping.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.cart.CartProduct;
import shopping.dto.request.CartProductRequest;
import shopping.repository.CartProductRepository;
import shopping.repository.ProductRepository;

@Service
@Transactional(readOnly = true)
public class CartProductService {

    private final CartProductRepository cartProductRepository;
    private final ProductRepository productRepository;

    public CartProductService(final CartProductRepository cartProductRepository,
        final ProductRepository productRepository) {
        this.cartProductRepository = cartProductRepository;
        this.productRepository = productRepository;
    }

    public CartProduct createCartProduct(
        final Long memberId,
        final CartProductRequest cartProductRequest
    ) {
        final Long productId = cartProductRequest.getProductId();

        // TODO: 언해피케이스 때 수정
        productRepository.findById(productId);
        cartProductRepository.findByMemberIdAndProductId(memberId, productId);

        final CartProduct cartProduct = new CartProduct(memberId, productId);

        return cartProductRepository.save(cartProduct);
    }
}
