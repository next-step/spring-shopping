package shopping.mart.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.core.exception.StatusCodeException;
import shopping.mart.domain.Cart;
import shopping.mart.domain.Product;
import shopping.mart.dto.CartAddRequest;
import shopping.mart.dto.CartResponse;
import shopping.mart.dto.CartResponse.ProductResponse;
import shopping.mart.dto.CartUpdateRequest;
import shopping.mart.persist.CartRepository;
import shopping.mart.persist.ProductRepository;

@Service
public class CartService {

    private static final String PRODUCT_NOT_FOUND = "CART-SERVICE-401";

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(final CartRepository cartRepository, final ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void addProduct(final long userId, final CartAddRequest request) {
        Cart cart = getCartByUserId(userId);
        Product product = getProductById(request.getProductId());

        cart.addProduct(product);

        cartRepository.addProduct(cart, product);
    }

    @Transactional
    public void updateProduct(final long userId, final CartUpdateRequest request) {
        Cart cart = getCartByUserId(userId);
        Product product = getProductById(request.getProductId());

        cart.updateProduct(product, request.getCount());

        cartRepository.updateCart(cart);
    }

    @Transactional
    public void deleteProduct(final long userId, final long productId) {
        Cart cart = getCartByUserId(userId);
        Product product = getProductById(productId);

        cart.deleteProduct(product);

        cartRepository.updateCart(cart);
    }

    @Transactional
    public CartResponse getCart(final long userId) {
        Cart cart = getCartByUserId(userId);

        List<ProductResponse> products = cart.getProductCounts().entrySet().stream()
                .map(entry -> new ProductResponse(entry.getKey().getId(), entry.getValue(),
                        entry.getKey().getImageUrl(), entry.getKey().getName()))
                .collect(Collectors.toList());

        return new CartResponse(cart.getCartId(), products);
    }

    private Cart getCartByUserId(long userId) {
        if (!cartRepository.existCartByUserId(userId)) {
            cartRepository.newCart(userId);
        }
        return cartRepository.getByUserId(userId);
    }

    private Product getProductById(long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new StatusCodeException(
                        MessageFormat.format("productId \"{0}\"에 해당하는 Product를 찾을 수 없습니다.", productId),
                        PRODUCT_NOT_FOUND));
    }

}
