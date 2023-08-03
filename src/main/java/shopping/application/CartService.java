package shopping.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.CartItem;
import shopping.domain.CartItems;
import shopping.domain.Product;
import shopping.domain.User;
import shopping.dto.CartCreateRequest;
import shopping.dto.CartResponse;
import shopping.dto.CartUpdateRequest;
import shopping.exception.ErrorType;
import shopping.exception.ShoppingException;
import shopping.repository.CartItemRepository;
import shopping.repository.ProductRepository;
import shopping.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(UserRepository userRepository,
                       ProductRepository productRepository,
                       CartItemRepository cartItemRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public void addProduct(CartCreateRequest request, Long userId) {
        User user = findUserById(userId);
        Product product = findProductById(request.getProductId());
        CartItems items = new CartItems(cartItemRepository.findAllByUserId(userId));
        CartItem item = new CartItem(user, product, 1);

        items.add(item);

        CartItem persistenceItem = items.findSameProduct(item)
                .orElseThrow();

        cartItemRepository.save(persistenceItem);
    }

    @Transactional(readOnly = true)
    public List<CartResponse> findAll(Long userId) {
        return cartItemRepository.findAllByUserId(userId)
                .stream()
                .map(CartResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void update(CartUpdateRequest request, Long cartItemId, Long userId) {
        CartItems items = new CartItems(cartItemRepository.findAllByUserId(userId));
        CartItem item = findCartItemById(cartItemId);

        validateUserContainsItem(items, item);

        item.updateQuantity(request.getQuantity());
    }

    @Transactional
    public void delete(Long cartItemId, Long userId) {
        CartItems items = new CartItems(cartItemRepository.findAllByUserId(userId));
        CartItem item = findCartItemById(cartItemId);

        validateUserContainsItem(items, item);

        cartItemRepository.delete(item);
    }

    private void validateUserContainsItem(CartItems items, CartItem item) {
        if (!items.contains(item)) {
            throw new ShoppingException(ErrorType.USER_NOT_CONTAINS_ITEM, item.getId());
        }
    }

    private CartItem findCartItemById(Long id) {
        return cartItemRepository.findById(id)
                .orElseThrow(() -> new ShoppingException(ErrorType.CART_ITEM_NO_EXIST, id));
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ShoppingException(ErrorType.USER_NO_EXIST, id));
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ShoppingException(ErrorType.PRODUCT_NO_EXIST, id));
    }
}
