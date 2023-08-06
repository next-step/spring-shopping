package shopping.mart.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.mart.domain.Cart;
import shopping.mart.domain.Product;
import shopping.mart.domain.exception.DoesNotExistProductException;
import shopping.mart.service.dto.CartAddRequest;
import shopping.mart.service.dto.CartResponse;
import shopping.mart.service.dto.CartUpdateRequest;
import shopping.mart.service.spi.CartRepository;
import shopping.mart.service.spi.ProductRepository;

@Service
public class CartService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public CartService(final ProductRepository productRepository,
        final CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    @Transactional
    public void addProduct(final long userId, final CartAddRequest request) {
        Cart cart = getCartByUserId(userId);
        Product product = getProductById(request.getProductId());

        cart.addProduct(product);

        cartRepository.persistCart(cart);
    }

    @Transactional
    public void updateProduct(final long userId, final CartUpdateRequest request) {
        Cart cart = getCartByUserId(userId);
        Product product = getProductById(request.getProductId());

        cart.updateProduct(product, request.getCount());

        cartRepository.persistCart(cart);
    }

    @Transactional
    public void deleteProduct(final long userId, final long productId) {
        Cart cart = getCartByUserId(userId);
        Product product = getProductById(productId);

        cart.deleteProduct(product);

        cartRepository.persistCart(cart);
    }

    @Transactional
    public CartResponse getCart(final long userId) {
        Cart cart = getCartByUserId(userId);

        List<CartResponse.ProductResponse> products = cart.getProductCounts().entrySet().stream()
            .map(entry -> new CartResponse.ProductResponse(entry.getKey().getId(), entry.getValue(),
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
            () -> new DoesNotExistProductException(
                MessageFormat.format("productId \"{0}\"에 해당하는 Product를 찾을 수 없습니다.", productId)));
    }

}
