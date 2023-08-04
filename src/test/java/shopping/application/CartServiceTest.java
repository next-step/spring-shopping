package shopping.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shopping.auth.PasswordEncoder;
import shopping.domain.cart.CartItem;
import shopping.domain.cart.Product;
import shopping.domain.user.User;
import shopping.dto.request.CartItemCreateRequest;
import shopping.dto.request.CartItemUpdateRequest;
import shopping.exception.CartItemNotFoundException;
import shopping.exception.ProductNotFoundException;
import shopping.exception.UserNotFoundException;
import shopping.exception.UserNotMatchException;
import shopping.repository.CartItemRepository;
import shopping.repository.ProductRepository;
import shopping.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DisplayName("장바구니 서비스 통합 테스트")
class CartServiceTest extends ServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Nested
    @DisplayName("장바구니 상품 추가")
    class WhenCreateCartItem {

        @DisplayName("정상 추가")
        @Test
        void createCartItem() {
            // given
            String email = "test@example.com";
            String password = "1234";
            String digest = passwordEncoder.encode(password);

            User user = new User(email, digest);
            userRepository.save(user);

            String name = "치킨";
            String imageUrl = "/chicken.jpg";
            Long price = 10_000L;

            Product product = new Product(name, imageUrl, price);
            Product savedProduct = productRepository.save(product);

            CartItemCreateRequest cartItemCreateRequest = new CartItemCreateRequest(savedProduct.getId());

            // when
            cartService.createCartItem(email, cartItemCreateRequest);

            // then
            List<CartItem> cartItems = cartItemRepository.findAll();
            assertThat(cartItems).hasSize(1);
            CartItem cartItem = cartItems.get(0);
            assertThat(cartItem.getProduct())
                    .extracting(Product::getName, Product::getImageUrl, Product::getPrice)
                    .containsExactly(name, imageUrl, price);
            assertThat(cartItem.getUser())
                    .extracting(User::getEmail, User::getPassword)
                    .containsExactly(email, digest);

        }

        @DisplayName("사용자가 존재하지 않으면 예외 발생")
        @Test
        void notUserFoundThenThrow() {
            // given
            String email = "test@example.com";

            String name = "치킨";
            String imageUrl = "/chicken.jpg";
            Long price = 10_000L;

            Product product = new Product(name, imageUrl, price);
            Product savedProduct = productRepository.save(product);

            CartItemCreateRequest cartItemCreateRequest = new CartItemCreateRequest(savedProduct.getId());

            // when, then
            assertThatThrownBy(() -> cartService.createCartItem(email, cartItemCreateRequest))
                    .isInstanceOf(UserNotFoundException.class);
        }

        @DisplayName("상품이 존재하지 않으면 예외 발생")
        @Test
        void notProductFoundThenThrow() {
            // given
            String email = "test@example.com";
            String password = "1234";
            String digest = passwordEncoder.encode(password);

            User user = new User(email, digest);
            userRepository.save(user);

            CartItemCreateRequest cartItemCreateRequest = new CartItemCreateRequest(1L);

            // when & then
            assertThatThrownBy(() -> cartService.createCartItem(email, cartItemCreateRequest))
                    .isInstanceOf(ProductNotFoundException.class);
        }

        @DisplayName("이미 장바구니에 담긴 아이템이면 수량 하나 추가")
        @Test
        void alreadyInCartThenAddQuantity() {
            // given
            String email = "test@example.com";
            String password = "1234";
            String digest = passwordEncoder.encode(password);

            User user = new User(email, digest);
            userRepository.save(user);

            String name = "치킨";
            String imageUrl = "/chicken.jpg";
            Long price = 10_000L;

            Product product = new Product(name, imageUrl, price);
            Product savedProduct = productRepository.save(product);

            CartItem cartItem = new CartItem(user, product);
            Integer quantity = cartItem.getQuantity();
            cartItemRepository.save(cartItem);

            CartItemCreateRequest cartItemCreateRequest = new CartItemCreateRequest(savedProduct.getId());

            // when
            cartService.createCartItem(email, cartItemCreateRequest);

            // then
            List<CartItem> cartItems = cartItemRepository.findAll();
            assertThat(cartItems).hasSize(1);
            CartItem updatedCartItem = cartItems.get(0);
            assertThat(updatedCartItem.getQuantity()).isEqualTo(quantity + 1);
            assertThat(updatedCartItem.getProduct())
                    .extracting(Product::getName, Product::getImageUrl, Product::getPrice)
                    .containsExactly(name, imageUrl, price);
            assertThat(updatedCartItem.getUser())
                    .extracting(User::getEmail, User::getPassword)
                    .containsExactly(email, digest);
        }
    }

    @Nested
    @DisplayName("장바구니 상품 수량 변경")
    class WhenUpdateCartItemQuantity {

        @DisplayName("정상 수량 변경")
        @Test
        void updateCartItemQuantity() {
            // given
            String email = "test@example.com";
            String password = "1234";
            String digest = passwordEncoder.encode(password);

            User user = new User(email, digest);
            userRepository.save(user);

            String name = "치킨";
            String imageUrl = "/chicken.jpg";
            Long price = 10_000L;

            Product product = new Product(name, imageUrl, price);
            productRepository.save(product);

            CartItem cartItem = new CartItem(user, product);
            CartItem savedCartItem = cartItemRepository.save(cartItem);

            Integer newQuantity = 5;
            CartItemUpdateRequest cartItemUpdateRequest = new CartItemUpdateRequest(newQuantity);

            // when
            cartService.updateCartItemQuantity(email, savedCartItem.getId(), cartItemUpdateRequest);

            // then
            Optional<CartItem> optionalCartItem = cartItemRepository.findById(savedCartItem.getId());
            assertThat(optionalCartItem).isPresent();
            CartItem updatedCartItem = optionalCartItem.get();
            assertThat(updatedCartItem.getQuantity()).isEqualTo(newQuantity);
        }

        @DisplayName("사용자가 존재하지 않으면 예외 발생")
        @Test
        void updateCartItemNotUserFound() {
            // given
            String email = "test@example.com";
            Integer newQuantity = 5;
            CartItemUpdateRequest cartItemUpdateRequest = new CartItemUpdateRequest(newQuantity);

            // when, then
            assertThatThrownBy(() -> cartService.updateCartItemQuantity(email, 1L, cartItemUpdateRequest))
                    .isInstanceOf(UserNotFoundException.class);
        }

        @DisplayName("상품이 존재하지 않으면 예외 발생")
        @Test
        void deleteCartItemNotCartItemFound() {
            // given
            String email = "test@example.com";
            String password = "1234";
            String digest = passwordEncoder.encode(password);

            User user = new User(email, digest);
            userRepository.save(user);

            Integer newQuantity = 5;
            CartItemUpdateRequest cartItemUpdateRequest = new CartItemUpdateRequest(newQuantity);

            // when, then
            assertThatCode(() -> cartService.updateCartItemQuantity(email, 1L, cartItemUpdateRequest))
                    .isInstanceOf(CartItemNotFoundException.class);
        }

        @DisplayName("사용자의 장바구니 아이템이 아니면 예외 발생")
        @Test
        void updateCartItemQuantityNotUsersItem() {
            // given
            String email = "test@example.com";
            String password = "1234";
            String digest = passwordEncoder.encode(password);

            User user = new User(email, digest);
            userRepository.save(user);

            String otherEmail = "other@example.com";
            String otherPassword = "other";
            String otherDigest = passwordEncoder.encode(otherPassword);

            User other = new User(otherEmail, otherDigest);
            userRepository.save(other);

            String name = "치킨";
            String imageUrl = "/chicken.jpg";
            Long price = 10_000L;

            Product product = new Product(name, imageUrl, price);
            productRepository.save(product);

            CartItem cartItem = new CartItem(other, product);
            CartItem savedCartItem = cartItemRepository.save(cartItem);
            Long cartId = savedCartItem.getId();

            Integer newQuantity = 5;
            CartItemUpdateRequest cartItemUpdateRequest = new CartItemUpdateRequest(newQuantity);

            // when, then
            assertThatThrownBy(
                    () -> cartService.updateCartItemQuantity(email, cartId, cartItemUpdateRequest))
                    .isInstanceOf(UserNotMatchException.class);
        }
    }


    @Nested
    @DisplayName("장바구니 상품 삭제")
    class WhenDeleteCartItem {

        @DisplayName("정상 삭제")
        @Test
        void deleteCartItem() {
            // given
            String email = "test@example.com";
            String password = "1234";
            String digest = passwordEncoder.encode(password);

            User user = new User(email, digest);
            userRepository.save(user);

            String name = "치킨";
            String imageUrl = "/chicken.jpg";
            Long price = 10_000L;

            Product product = new Product(name, imageUrl, price);
            productRepository.save(product);

            CartItem cartItem = new CartItem(user, product);
            CartItem savedCartItem = cartItemRepository.save(cartItem);
            Long cartId = savedCartItem.getId();

            // when
            cartService.deleteCartItem(email, cartId);

            // then
            Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartId);
            assertThat(optionalCartItem).isEmpty();
        }

        @DisplayName("사용자가 존재하지 않으면 예외 발생")
        @Test
        void deleteCartItemNotUserFound() {
            // given
            String email = "test@example.com";

            // when, then
            assertThatThrownBy(() -> cartService.deleteCartItem(email, 1L))
                    .isInstanceOf(UserNotFoundException.class);
        }

        @DisplayName("상품이 존재하지 않으면 예외 발생")
        @Test
        void deleteCartItemNotCartItemFound() {
            // given
            String email = "test@example.com";
            String password = "1234";
            String digest = passwordEncoder.encode(password);

            User user = new User(email, digest);
            userRepository.save(user);

            // when, then
            assertThatCode(() -> cartService.deleteCartItem(email, 1L))
                    .isInstanceOf(CartItemNotFoundException.class);
        }

        @DisplayName("사용자의 장바구니 아이템이 아니면 예외 발생")
        @Test
        void deleteCartItemNotUsersItem() {
            // given
            String email = "test@example.com";
            String password = "1234";
            String digest = passwordEncoder.encode(password);

            User user = new User(email, digest);
            userRepository.save(user);

            String otherEmail = "other@example.com";
            String otherPassword = "other";
            String otherDigest = passwordEncoder.encode(otherPassword);

            User other = new User(otherEmail, otherDigest);
            userRepository.save(other);

            String name = "치킨";
            String imageUrl = "/chicken.jpg";
            Long price = 10_000L;

            Product product = new Product(name, imageUrl, price);
            productRepository.save(product);

            CartItem cartItem = new CartItem(other, product);
            CartItem savedCartItem = cartItemRepository.save(cartItem);
            Long cartId = savedCartItem.getId();

            // when, then
            assertThatCode(() -> cartService.deleteCartItem(email, cartId))
                    .isInstanceOf(UserNotMatchException.class);
        }
    }
}