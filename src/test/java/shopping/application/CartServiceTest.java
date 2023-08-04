package shopping.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shopping.auth.PasswordEncoder;
import shopping.domain.cart.CartItem;
import shopping.domain.cart.Product;
import shopping.domain.user.Email;
import shopping.domain.user.User;
import shopping.dto.request.CartItemCreateRequest;
import shopping.dto.request.CartItemUpdateRequest;
import shopping.exception.CartItemNotFoundException;
import shopping.exception.UserNotFoundException;
import shopping.exception.UserNotMatchException;
import shopping.repository.CartItemRepository;
import shopping.repository.ProductRepository;
import shopping.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("장바구니 서비스 통합 테스트")
@SpringBootTest
class CartServiceTest {

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

    @BeforeEach
    void setUp() {
        cartItemRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Nested
    @DisplayName("장바구니 물건 추가 테스트")
    class WhenCreateCartItem {

        @DisplayName("장바구니에 상품 추가")
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

        @DisplayName("데이터베이스에 회원이 없어서 예외 발생")
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

        @DisplayName("데이터베이스에 상품이 없어서 예외 발생")
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
                    .isInstanceOf(EntityNotFoundException.class);
        }

        @DisplayName("이미 장바구니에 담긴 아이템일 경우 수량 하나 추가")
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
    @DisplayName("장바구니 물건 수량 변경 테스트")
    class WhenUpdateCartItemQuantity {

        @DisplayName("장바구니에 담긴 아이템 수량 변경")
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

        @DisplayName("장바구니에 담긴 아이템 수량 변경시 유저의 장바구니 아이템이 아닐 시 접근 제한 예외 발생")
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

    // TODO: 장바구니 물건 삭제 테스트 수정
    @Nested
    @DisplayName("장바구니 물건 삭제 테스트")
    class WhenDeleteCartItem {

        @DisplayName("장바구니에 담긴 아이템 삭제")
        @Test
        void deleteCartItem() {
            // given
            String email = "test@example.com";
            User user = new User(1L, email, "1234");
            Product product = new Product(1L, "chicken", "/chicken.jpg", 10_000L);
            when(userRepository.findByEmail(new Email(email)))
                    .thenReturn(Optional.of(user));
            when(cartItemRepository.findById(1L))
                    .thenReturn(Optional.of(new CartItem(1L, user, product, 1)));

            // when
            assertThatCode(() -> cartService.deleteCartItem(email, 1L))
                    .doesNotThrowAnyException();

            // then
            verify(cartItemRepository, times(1)).deleteById(1L);
        }

        @DisplayName("장바구니에 담긴 아이템 삭제시 유저 없으면 예외 발생")
        @Test
        void deleteCartItemNotUserFound() {
            // given
            String email = "test@example.com";
            when(userRepository.findByEmail(new Email(email)))
                    .thenReturn(Optional.empty());

            // when, then
            assertThatCode(() -> cartService.deleteCartItem(email, 1L))
                    .isInstanceOf(UserNotFoundException.class);
        }

        @DisplayName("장바구니에 담긴 아이템 삭제시 장바구니 아이템 없으면 예외 발생")
        @Test
        void deleteCartItemNotCartItemFound() {
            // given
            String email = "test@example.com";
            User user = new User(1L, email, "1234");
            when(userRepository.findByEmail(new Email(email)))
                    .thenReturn(Optional.of(user));
            when(cartItemRepository.findById(1L))
                    .thenReturn(Optional.empty());

            // when, then
            assertThatCode(() -> cartService.deleteCartItem(email, 1L))
                    .isInstanceOf(CartItemNotFoundException.class);
        }

        @DisplayName("장바구니에 담긴 아이템 삭제시 유저의 장바구니 아이템이 아닐 시 접근 제한 예외 발생")
        @Test
        void deleteCartItemNotUsersItem() {
            // given
            String email = "test@example.com";
            User user1 = new User(1L, email, "1234");
            User user2 = new User(2L, "aaa@bbb.ccc", "1234");
            Product product = new Product(1L, "chicken", "/chicken.jpg", 10_000L);
            when(userRepository.findByEmail(new Email(email)))
                    .thenReturn(Optional.of(user1));
            when(cartItemRepository.findById(1L))
                    .thenReturn(Optional.of(new CartItem(1L, user2, product, 1)));

            // when, then
            assertThatCode(() -> cartService.deleteCartItem(email, 1L))
                    .isInstanceOf(UserNotMatchException.class);
        }
    }
}