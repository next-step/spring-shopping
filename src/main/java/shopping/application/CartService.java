package shopping.application;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import shopping.domain.cart.CartItem;
import shopping.domain.user.Email;
import shopping.domain.cart.Product;
import shopping.domain.user.User;
import shopping.dto.request.CartItemCreateRequest;
import shopping.dto.request.CartItemUpdateRequest;
import shopping.dto.response.CartItemResponse;
import shopping.exception.CartItemNotFoundException;
import shopping.exception.UserNotFoundException;
import shopping.exception.UserNotMatchException;
import shopping.repository.CartItemRepository;
import shopping.repository.ProductRepository;
import shopping.repository.UserRepository;

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

    public void createCartItem(String email, CartItemCreateRequest cartItemCreateRequest) {
        User user = userRepository.findByEmail(new Email(email))
                .orElseThrow(() -> new UserNotFoundException(email));
        Product product = productRepository
                .getReferenceById(cartItemCreateRequest.getProductId());

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

    public void updateCartItemQuantity(String email, Long cartId, CartItemUpdateRequest cartItemUpdateRequest) {

        CartItem cartItem = findCartItem(email, cartId);
        CartItem updatedCartItem = cartItem.updateQuantity(cartItemUpdateRequest.getQuantity());
        cartItemRepository.save(updatedCartItem);
    }

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
