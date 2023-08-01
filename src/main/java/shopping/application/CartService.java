package shopping.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.CartItem;
import shopping.domain.Product;
import shopping.domain.User;
import shopping.dto.CartItemQuantityRequest;
import shopping.dto.CartRequest;
import shopping.dto.CartResponse;
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
    public void addProduct(CartRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Product product = productRepository.findById(request.getProductId()).orElseThrow();

        CartItem cartItem = user.addCartItem(product);
        cartItemRespository.save(cartItem);
    }

    @Transactional(readOnly = true)
    public List<CartResponse> findAll(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow()
                .getCartItems()
                .stream()
                .map(CartResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void update(CartItemQuantityRequest request, Long cartItemId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        CartItem item = cartItemRespository.findById(cartItemId).orElseThrow();

        item.updateQuantity(request.getQuantity());
    }
}
