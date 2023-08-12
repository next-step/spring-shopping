package shopping.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.CartItems;
import shopping.domain.entity.CartItem;
import shopping.domain.entity.Product;
import shopping.domain.entity.User;
import shopping.domain.vo.Quantity;
import shopping.dto.CartCreateRequest;
import shopping.dto.CartResponse;
import shopping.dto.QuantityUpdateRequest;
import shopping.exception.ProductNotFoundException;
import shopping.exception.UserNotFoundException;
import shopping.repository.CartItemRepository;
import shopping.repository.ProductRepository;
import shopping.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(final UserRepository userRepository,
                       final ProductRepository productRepository,
                       final CartItemRepository cartItemRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public void addProduct(final CartCreateRequest request, final Long userId) {
        final User user = findUserById(userId);
        final Product product = findProductById(request.getProductId());
        final CartItems items = findCartItemsByUserId(userId);
        final CartItem newItem = new CartItem(user, product, Quantity.ONE);

        items.add(newItem);
        checkItemIsNew(items, newItem);
    }

    @Transactional(readOnly = true)
    public List<CartResponse> findAll(final Long userId) {
        return cartItemRepository.findAllByUserId(userId)
                .stream()
                .map(CartResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void update(final QuantityUpdateRequest request, final Long cartItemId, final Long userId) {
        final CartItems items = findCartItemsByUserId(userId);
        final CartItem item = items.find(cartItemId);

        item.updateQuantity(request.getQuantity());
    }

    @Transactional
    public void delete(final Long cartItemId, final Long userId) {
        final CartItems items = findCartItemsByUserId(userId);
        final CartItem item = items.find(cartItemId);

        cartItemRepository.delete(item);
    }

    private void checkItemIsNew(final CartItems items, final CartItem newItem) {
        if (items.contains(newItem)) {
            cartItemRepository.save(newItem);
        }
    }

    private CartItems findCartItemsByUserId(final Long userId) {
        return new CartItems(cartItemRepository.findAllByUserId(userId));
    }

    private User findUserById(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private Product findProductById(final Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }
}
