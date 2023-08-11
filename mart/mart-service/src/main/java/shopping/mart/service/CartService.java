package shopping.mart.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.mart.domain.usecase.cart.CartUseCase;
import shopping.mart.domain.usecase.cart.request.CartAddRequest;
import shopping.mart.domain.usecase.cart.request.CartUpdateRequest;
import shopping.mart.domain.usecase.cart.response.CartResponse;
import shopping.mart.domain.Cart;
import shopping.mart.domain.Product;
import shopping.mart.domain.exception.DoesNotExistProductException;
import shopping.mart.domain.repository.CartRepository;
import shopping.mart.domain.repository.ProductRepository;

@Service
@Transactional(readOnly = true)
public class CartService implements CartUseCase {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    public CartService(ProductRepository productRepository,
            final CartRepository cartRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    @Transactional
    public void addProduct(long userId, CartAddRequest request) {
        Cart cart = getCartByUserId(userId);
        Product product = getProductById(request.getProductId());

        cart.addProduct(product);

        cartRepository.persistCart(cart);
    }

    @Override
    @Transactional
    public void updateProduct(final long userId, final CartUpdateRequest request) {
        Cart cart = getCartByUserId(userId);
        Product product = getProductById(request.getProductId());

        cart.updateProduct(product, request.getCount());

        cartRepository.persistCart(cart);
    }

    @Override
    @Transactional
    public void deleteProduct(long userId, long productId) {
        Cart cart = getCartByUserId(userId);
        Product product = getProductById(productId);

        cart.deleteProduct(product);

        cartRepository.persistCart(cart);
    }

    @Override
    public CartResponse getCart(long userId) {
        Cart cart = getCartByUserId(userId);

        List<CartResponse.ProductResponse> products = cart.getProductCounts().entrySet().stream()
                .map(entry -> new CartResponse.ProductResponse(entry.getKey().getId(), entry.getValue(),
                        entry.getKey().getImageUrl(), entry.getKey().getName(), entry.getKey().getPrice()))
                .collect(Collectors.toList());

        return new CartResponse(cart.getCartId(), products);
    }

    @Override
    @Transactional
    public void clearCart(long userId) {
        Cart cart = cartRepository.getByUserId(userId);

        cart.deleteAllProducts();

        cartRepository.persistCart(cart);
    }

    private Cart getCartByUserId(long userId) {
        if (cartRepository.existCartByUserId(userId)) {
            return cartRepository.getByUserId(userId);
        }
        return cartRepository.newCart(userId);
    }

    private Product getProductById(long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new DoesNotExistProductException(
                        MessageFormat.format("productId \"{0}\"에 해당하는 Product를 찾을 수 없습니다.", productId)));
    }

}
