package shopping.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.CartItem;
import shopping.domain.Product;
import shopping.domain.User;
import shopping.dto.CartCreateRequest;
import shopping.dto.CartResponse;
import shopping.dto.CartUpdateRequest;
import shopping.exception.ErrorType;
import shopping.exception.ShoppingException;
import shopping.repository.CartItemRespository;
import shopping.repository.ProductRepository;
import shopping.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRespository cartItemRespository;

    public CartService(UserRepository userRepository,
                       ProductRepository productRepository,
                       CartItemRespository cartItemRespository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.cartItemRespository = cartItemRespository;
    }

    @Transactional
    public void addProduct(CartCreateRequest request, Long userId) {
        User user = findUserById(userId);
        Product product = findProductById(request.getProductId());

        CartItem cartItem = user.addCartItem(product);
        cartItemRespository.save(cartItem);
    }

    @Transactional(readOnly = true)
    public List<CartResponse> findAll(Long userId) {
        return findUserById(userId)
                .getCartItems()
                .stream()
                .map(CartResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void update(CartUpdateRequest request, Long cartItemId, Long userId) {
        User user = findUserById(userId);
        CartItem item = findCartItemById(cartItemId);

        validateUserContainsItem(user, item);

        item.updateQuantity(request.getQuantity());
    }

    @Transactional
    public void delete(Long cartItemId, Long userId) {
        User user = findUserById(userId);
        CartItem item = findCartItemById(cartItemId);

        validateUserContainsItem(user, item);

        cartItemRespository.delete(item);
    }

    private void validateUserContainsItem(User user, CartItem item) {
        if (!user.containsCartItem(item)) {
            throw new ShoppingException(ErrorType.USER_NOT_CONTAINS_ITEM, item.getId());
        }
    }

    private CartItem findCartItemById(Long id) {
        return cartItemRespository.findById(id)
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
