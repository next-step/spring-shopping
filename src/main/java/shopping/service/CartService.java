package shopping.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.Cart;
import shopping.domain.Product;
import shopping.domain.exception.StatusCodeException;
import shopping.dto.CartAddRequest;
import shopping.dto.CartResponse;
import shopping.dto.CartResponse.ProductResponse;
import shopping.dto.CartUpdateRequest;
import shopping.persist.CartRepository;
import shopping.persist.ProductRepository;

@Service
public class CartService {

    private static final String PRODUCT_NOT_FOUND = "CART-SERVICE-401";
    private static final String CART_NOT_FOUND = "CART-SERVICE-402";

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(final CartRepository cartRepository, final ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void addProduct(final long userId, final CartAddRequest request) {
        Cart cart = findCartByUserId(userId);
        Product product = findProductById(request.getProductId());

        cart.addProduct(product);

        cartRepository.updateCart(cart);
    }

    @Transactional
    public void updateProduct(final long userId, final CartUpdateRequest request) {
        Cart cart = findCartByUserId(userId);
        Product product = findProductById(request.getProductId());

        cart.updateProduct(product, request.getCount());

        cartRepository.updateCart(cart);
    }

    @Transactional
    public void deleteProduct(final long userId, final long productId) {
        Cart cart = findCartByUserId(userId);
        Product product = findProductById(productId);

        cart.deleteProduct(product);

        cartRepository.updateCart(cart);
    }

    @Transactional(readOnly = true)
    public CartResponse findCart(final long userId) {
        Cart cart = findCartByUserId(userId);

        List<ProductResponse> products = cart.getProductCounts().entrySet().stream()
                .map(entry -> new ProductResponse(entry.getKey().getId(), entry.getValue(),
                        entry.getKey().getImageUrl(), entry.getKey().getName()))
                .collect(Collectors.toList());

        return new CartResponse(cart.getCartId(), products);
    }

    private Cart findCartByUserId(long userId) {
        return cartRepository.findByUserId(userId).orElseThrow(
                () -> new StatusCodeException(
                        MessageFormat.format("userId \"{0}\"에 해당하는 Cart를 찾을 수 없습니다.", userId),
                        CART_NOT_FOUND));
    }

    private Product findProductById(long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new StatusCodeException(
                        MessageFormat.format("productId \"{0}\"에 해당하는 Product를 찾을 수 없습니다.", productId),
                        PRODUCT_NOT_FOUND));
    }

}
