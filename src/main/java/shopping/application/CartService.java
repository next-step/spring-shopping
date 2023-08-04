package shopping.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.cart.CartItem;
import shopping.domain.cart.Product;
import shopping.domain.user.Email;
import shopping.domain.user.User;
import shopping.dto.request.CartItemCreateRequest;
import shopping.dto.request.CartItemUpdateRequest;
import shopping.dto.response.CartItemResponse;
import shopping.exception.CartItemNotFoundException;
import shopping.exception.ProductNotFoundException;
import shopping.exception.UserNotFoundException;
import shopping.exception.UserNotMatchException;
import shopping.repository.CartItemRepository;
import shopping.repository.ProductRepository;
import shopping.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartService(CartItemRepository cartItemRepository, UserRepository userRepository,
            ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void createCartItem(String email, CartItemCreateRequest cartItemCreateRequest) {
        User user = userRepository.findByEmail(new Email(email))
                .orElseThrow(() -> new UserNotFoundException(email));
        Product product = productRepository
                .findById(cartItemCreateRequest.getProductId())
                .orElseThrow(ProductNotFoundException::new);

        Optional<CartItem> originalCartItem = cartItemRepository
                .findByUserAndProduct(user, product);

        originalCartItem.ifPresentOrElse(
                cartItem -> cartItemRepository.save(cartItem.addQuantity()),
                () -> cartItemRepository.save(new CartItem(user, product)));
    }

    public List<CartItemResponse> findAllByEmail(String email) {
        List<CartItem> cartItems = cartItemRepository.findAllByUserEmail(new Email(email));
        return cartItems.stream()
                .map(CartItemResponse::of)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void updateCartItemQuantity(String email, Long cartId, CartItemUpdateRequest cartItemUpdateRequest) {
        CartItem cartItem = findCartItem(email, cartId);
        CartItem updatedCartItem = cartItem.updateQuantity(cartItemUpdateRequest.getQuantity());
        cartItemRepository.save(updatedCartItem);
    }

    @Transactional
    public void deleteCartItem(String email, Long id) {
        findCartItem(email, id);
        cartItemRepository.deleteById(id);
    }

    private CartItem findCartItem(String email, Long cartId) {
        User user = userRepository.findByEmail(new Email(email))
                .orElseThrow(() -> new UserNotFoundException(email));
        CartItem cartItem = cartItemRepository.findById(cartId)
                .orElseThrow(() -> new CartItemNotFoundException(String.valueOf(cartId)));

        validateUserMatch(user, cartItem);
        return cartItem;
    }

    private void validateUserMatch(User user, CartItem cartItem) {
        if (cartItem.isNotUserMatch(user)) {
            throw new UserNotMatchException();
        }
    }
}
