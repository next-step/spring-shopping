package shopping.application;

import java.util.Optional;
import org.springframework.stereotype.Service;
import shopping.domain.CartItem;
import shopping.domain.Product;
import shopping.domain.User;
import shopping.dto.CartItemCreateRequest;
import shopping.exception.UserNotFoundException;
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
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        Product product = productRepository.getReferenceById(cartItemCreateRequest.getProductId());

        Optional<CartItem> originalCartItem = cartItemRepository.findByUserAndProduct(user,
                product);

        CartItem cartItem = originalCartItem
                .map(item -> item.addQuantity(1))
                .orElseGet(() -> new CartItem(user, product));

        cartItemRepository.save(cartItem);
    }
}
