package shopping.mart.service;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.core.exception.BadRequestException;
import shopping.mart.domain.Cart;
import shopping.mart.domain.Product;
import shopping.mart.domain.status.CartExceptionStatus;
import shopping.mart.dto.CartAddRequest;
import shopping.mart.dto.CartResponse;
import shopping.mart.dto.CartResponse.ProductResponse;
import shopping.mart.dto.CartUpdateRequest;
import shopping.mart.persist.CartRepository;
import shopping.mart.persist.ProductRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartService(final CartRepository cartRepository, final ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void addProduct(final long userId, final CartAddRequest request) {
        Cart cart = cartRepository.getByUserId(userId);
        Product product = findProductById(request.getProductId());

        cart.addProduct(product);

        cartRepository.addProduct(cart, product);
    }

    @Transactional
    public void updateProduct(final long userId, final CartUpdateRequest request) {
        Cart cart = cartRepository.getByUserId(userId);
        Product product = findProductById(request.getProductId());

        cart.updateProduct(product, request.getCount());

        cartRepository.updateCart(cart);
    }

    @Transactional
    public void deleteProduct(final long userId, final long productId) {
        Cart cart = cartRepository.getByUserId(userId);
        Product product = findProductById(productId);

        cart.deleteProduct(product);

        cartRepository.updateCart(cart);
    }

    @Transactional
    public CartResponse findCart(final long userId) {
        Cart cart = cartRepository.getByUserId(userId);

        List<ProductResponse> products = cart.getProductCounts().entrySet().stream()
                .map(entry -> new ProductResponse(entry.getKey().getId(), entry.getValue(),
                        entry.getKey().getImageUrl(), entry.getKey().getName()))
                .collect(Collectors.toList());

        return new CartResponse(cart.getCartId(), products);
    }

    private Product findProductById(long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new BadRequestException(
                        MessageFormat.format("productId \"{0}\"에 해당하는 Product를 찾을 수 없습니다.", productId),
                        CartExceptionStatus.NOT_FOUND.getStatus()));
    }
}
