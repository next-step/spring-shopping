package shopping.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.CartItem;
import shopping.domain.Product;
import shopping.domain.User;
import shopping.dto.CartCreateRequest;
import shopping.dto.CartResponse;
import shopping.dto.CartUpdateRequest;
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
        return userRepository.findById(userId)
                .orElseThrow()
                .getCartItems()
                .stream()
                .map(CartResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void update(CartUpdateRequest request, Long cartItemId, Long userId) {
        User user = findUserById(userId);
        CartItem item = findCartItemById(cartItemId);

        if (!user.containsCartItem(item)) {
            throw new ShoppingException("유효하지 않은 cart item 입니다 : " + cartItemId);
        }

        item.updateQuantity(request.getQuantity());
    }

    @Transactional
    public void delete(Long cartItemId, Long userId) {
        User user = findUserById(userId);
        CartItem item = findCartItemById(cartItemId);

        if (!user.containsCartItem(item)) {
            throw new ShoppingException("유효하지 않은 cart item 입니다 : " + cartItemId);
        }

        cartItemRespository.delete(item);
    }

    private CartItem findCartItemById(Long id) {
        return cartItemRespository.findById(id)
                .orElseThrow(() -> new ShoppingException("존재하지 않는 장바구니 상품 id입니다 : " + id));
    }

    private User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ShoppingException("존재하지 않는 사용자 id입니다 : " + id));
    }

    private Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ShoppingException("존재하지 않는 상품 id입니다 : " + id));
    }
}
