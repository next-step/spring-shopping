package shopping.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.cart.CartItem;
import shopping.domain.cart.CartItemRepository;
import shopping.domain.cart.CartItems;
import shopping.domain.product.Product;
import shopping.domain.product.ProductRepository;
import shopping.dto.CartCreateRequest;
import shopping.dto.CartResponse;
import shopping.dto.QuantityUpdateRequest;
import shopping.exception.ErrorType;
import shopping.exception.ShoppingException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(final ProductRepository productRepository,
                       final CartItemRepository cartItemRepository) {
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public Long addProduct(final CartCreateRequest request, final Long userId) {
        final Product product = findProductById(request.getProductId());
        final CartItems items = findCartItemsByUserId(userId);
        final CartItem item = new CartItem(userId, product);

        CartItem affectedItem = items.add(item);

        return cartItemRepository.save(affectedItem).getId();
    }

    @Transactional(readOnly = true)
    public CartResponse findById(final Long id) {
        return CartResponse.from(cartItemRepository.findById(id)
                .orElseThrow(() -> new ShoppingException(ErrorType.CART_ITEM_NO_EXIST, id)));
    }

    @Transactional(readOnly = true)
    public List<CartResponse> findAll(final Long userId) {
        return cartItemRepository.findAllByUserId(userId)
                .stream()
                .map(CartResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void updateQuantity(final QuantityUpdateRequest request, final Long cartItemId, final Long userId) {
        final CartItems items = findCartItemsByUserId(userId);
        final CartItem item = findCartItemById(cartItemId);

        validateUserContainsItem(items, item);

        item.updateQuantity(request.getQuantity());
    }

    @Transactional
    public void delete(final Long cartItemId, final Long userId) {
        final CartItems items = findCartItemsByUserId(userId);
        final CartItem item = findCartItemById(cartItemId);

        validateUserContainsItem(items, item);

        cartItemRepository.delete(item);
    }

    private CartItems findCartItemsByUserId(final Long userId) {
        return new CartItems(cartItemRepository.findAllByUserId(userId));
    }

    private void validateUserContainsItem(final CartItems items, final CartItem item) {
        if (!items.contains(item)) {
            throw new ShoppingException(ErrorType.USER_NOT_CONTAINS_ITEM, item.getId());
        }
    }

    private CartItem findCartItemById(final Long id) {
        return cartItemRepository.findById(id)
                .orElseThrow(() -> new ShoppingException(ErrorType.CART_ITEM_NO_EXIST, id));
    }

    private Product findProductById(final Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ShoppingException(ErrorType.PRODUCT_NO_EXIST, id));
    }
}
