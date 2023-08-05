package shopping.application;

import org.springframework.data.domain.Pageable;
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

@Transactional(readOnly = true)
@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartItemService(CartItemRepository cartItemRepository, UserRepository userRepository,
                           ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public CartItemResponse createCartItem(String email, CartItemCreateRequest cartItemCreateRequest) {
        User user = findUserOrThrow(email);
        Product product = productRepository
                .findById(cartItemCreateRequest.getProductId())
                .orElseThrow(ProductNotFoundException::new);

        Optional<CartItem> originalCartItem = cartItemRepository
                .findByUserAndProduct(user, product);

        if (originalCartItem.isPresent()) {
            return CartItemResponse.of(
                    cartItemRepository.save(originalCartItem.get().addQuantity())
            );
        }

        return CartItemResponse.of(cartItemRepository.save(new CartItem(user, product)));
    }

    public List<CartItemResponse> findAllByEmail(String email, Pageable pageable) {
        findUserOrThrow(email);
        return cartItemRepository.findAllByUserEmail(new Email(email), pageable)
                .map(CartItemResponse::of)
                .getContent();
    }

    @Transactional
    public CartItemResponse updateCartItemQuantity(String email, Long cartItemId, CartItemUpdateRequest cartItemUpdateRequest) {
        CartItem cartItem = findCartItem(email, cartItemId);
        CartItem updatedCartItem = cartItem.updateQuantity(cartItemUpdateRequest.getQuantity());
        return CartItemResponse.of(cartItemRepository.save(updatedCartItem));
    }

    @Transactional
    public void deleteCartItem(String email, Long cartItemId) {
        findCartItem(email, cartItemId);
        cartItemRepository.deleteById(cartItemId);
    }

    private CartItem findCartItem(String email, Long cartItemId) {
        User user = findUserOrThrow(email);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new CartItemNotFoundException(String.valueOf(cartItemId)));

        validateUserMatch(user, cartItem);
        return cartItem;
    }

    private User findUserOrThrow(String email) {
        return userRepository.findByEmail(new Email(email))
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    private void validateUserMatch(User user, CartItem cartItem) {
        if (cartItem.isNotUserMatch(user)) {
            throw new UserNotMatchException();
        }
    }
}
