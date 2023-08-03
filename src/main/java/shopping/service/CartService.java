package shopping.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import shopping.domain.cart.Cart;
import shopping.domain.cart.CartProduct;
import shopping.domain.product.Product;
import shopping.dto.response.CartResponse;
import shopping.repository.CartProductRepository;
import shopping.repository.ProductRepository;

// TODO: 테스트
@Service
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
        final List<Long> cartProductIds = cartProducts.stream()
            .map(CartProduct::getProductId)
            .collect(Collectors.toList());

        final List<Product> products = productRepository.findAllById(cartProductIds);
        final Cart cart = new Cart(cartProducts);

        return products.stream()
            .map(product -> CartResponse.of(cart.find(product.getId()), product))
            .collect(Collectors.toList());
    }
}
