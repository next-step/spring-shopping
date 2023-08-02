package shopping.application;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import shopping.domain.CartItem;
import shopping.domain.Email;
import shopping.domain.Product;
import shopping.domain.User;
import shopping.dto.CartItemCreateRequest;
import shopping.dto.CartItemResponse;
import shopping.dto.CartItemUpdateRequest;
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
        Product product = productRepository.getReferenceById(cartItemCreateRequest.getProductId());

        Optional<CartItem> originalCartItem = cartItemRepository.findByUserAndProduct(user,
                product);

        CartItem cartItem = originalCartItem
                .map(item -> item.addQuantity(1))
                .orElseGet(() -> new CartItem(user, product));

        cartItemRepository.save(cartItem);
    }

    public List<CartItemResponse> findAllByEmail(String email) {
        List<CartItem> cartItems = cartItemRepository.findAllByUserEmail(new Email(email));
        return cartItems.stream()
                .map(CartItemResponse::of)
                .collect(Collectors.toUnmodifiableList());
    }

    public void updateCartItemQuantity(
            String email,
            Long id,
            CartItemUpdateRequest cartItemUpdateRequest) {

        User user = userRepository.findByEmail(new Email(email))
                .orElseThrow(() -> new UserNotFoundException(email));
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new CartItemNotFoundException(String.valueOf(id)));

        if (!cartItem.getUser().equals(user)) {
            throw new UserNotMatchException();
        }

        CartItem updatedCartItem = cartItem.updateQuantity(cartItemUpdateRequest.getQuantity());
        cartItemRepository.save(updatedCartItem);
    }

    public void deleteCartItem(String email, Long id) {
        User user = userRepository.findByEmail(new Email(email))
                .orElseThrow(() -> new UserNotFoundException(email));
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new CartItemNotFoundException(String.valueOf(id)));

        if (!cartItem.getUser().equals(user)) {
            throw new UserNotMatchException();
        }

        cartItemRepository.deleteById(id);
    }
}
