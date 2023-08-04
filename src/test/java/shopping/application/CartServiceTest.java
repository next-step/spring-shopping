package shopping.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("장바구니 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @InjectMocks
    private CartService cartService;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Nested
    @DisplayName("장바구니 물건 추가 테스트")
    class WhenCreateCartItem {

        @DisplayName("장바구니에 상품 추가")
        @Test
        void createCartItem() {
            // given
            CartItemCreateRequest cartItemCreateRequest = new CartItemCreateRequest(1L);
            String email = "test@example.com";
            when(userRepository.findByEmail(new Email(email)))
                    .thenReturn(Optional.of(new User(1L, email, "1234")));
            when(productRepository.getReferenceById(cartItemCreateRequest.getProductId()))
                    .thenReturn(new Product(1L, "chicken", "/chicken.jpg", 10_000L));

            // when
            assertThatCode(() -> cartService.createCartItem(email, cartItemCreateRequest))
                    .doesNotThrowAnyException();

            // then
            verify(cartItemRepository, times(1)).save(any());
        }

        @DisplayName("데이터베이스에 회원이 없어서 예외 발생")
        @Test
        void notUserFoundThenThrow() {
            // given
            CartItemCreateRequest cartItemCreateRequest = new CartItemCreateRequest(1L);
            String email = "test@example.com";
            when(userRepository.findByEmail(new Email(email)))
                    .thenReturn(Optional.empty());

            // when, then
            assertThatCode(() -> cartService.createCartItem(email, cartItemCreateRequest))
                    .isInstanceOf(UserNotFoundException.class);
        }

        @DisplayName("데이터베이스에 상품이 없어서 예외 발생")
        @Test
        void notProductFoundThenThrow() {
            // given
            CartItemCreateRequest cartItemCreateRequest = new CartItemCreateRequest(1L);
            String email = "test@example.com";
            when(userRepository.findByEmail(new Email(email)))
                    .thenReturn(Optional.of(new User(1L, email, "1234")));
            when(productRepository.getReferenceById(cartItemCreateRequest.getProductId()))
                    .thenThrow(EntityNotFoundException.class);

            // when & then
            assertThatCode(() -> cartService.createCartItem(email, cartItemCreateRequest))
                    .isInstanceOf(EntityNotFoundException.class);
        }

        @DisplayName("이미 장바구니에 담긴 아이템일 경우 수량 하나 추가")
        @Test
        void alreadyInCartThenAddQuantity() {
            // given
            CartItemCreateRequest cartItemCreateRequest = new CartItemCreateRequest(1L);
            String email = "test@example.com";
            when(userRepository.findByEmail(new Email(email)))
                    .thenReturn(Optional.of(new User(1L, email, "1234")));
            when(productRepository.getReferenceById(cartItemCreateRequest.getProductId()))
                    .thenReturn(new Product(1L, "chicken", "/chicken.jpg", 10_000L));

            // when
            assertThatCode(() -> cartService.createCartItem(email, cartItemCreateRequest))
                    .doesNotThrowAnyException();

            // then
            verify(cartItemRepository, times(1)).save(any());
        }
    }

    @Nested
    @DisplayName("장바구니 물건 수량 변경 테스트")
    class WhenUpdateCartItemQuantity {

        @DisplayName("장바구니에 담긴 아이템 수량 변경")
        @Test
        void updateCartItemQuantity() {
            // given
            CartItemUpdateRequest cartItemUpdateRequest = new CartItemUpdateRequest(5);
            String email = "test@example.com";
            User user = new User(1L, email, "1234");
            Product product = new Product(1L, "chicken", "/chicken.jpg", 10_000L);
            when(userRepository.findByEmail(new Email(email)))
                    .thenReturn(Optional.of(user));
            when(cartItemRepository.findById(1L))
                    .thenReturn(Optional.of(new CartItem(1L, user, product, 1)));

            // when
            assertThatCode(
                    () -> cartService.updateCartItemQuantity(email, 1L, cartItemUpdateRequest))
                    .doesNotThrowAnyException();

            // then
            verify(cartItemRepository, times(1)).save(any());
        }

        @DisplayName("장바구니에 담긴 아이템 수량 변경시 유저 없으면 예외 발생")
        @Test
        void updateCartItemQuantityNotUserFound() {
            // given
            CartItemUpdateRequest cartItemUpdateRequest = new CartItemUpdateRequest(5);
            String email = "test@example.com";
            when(userRepository.findByEmail(new Email(email)))
                    .thenReturn(Optional.empty());

            // when, then
            assertThatCode(
                    () -> cartService.updateCartItemQuantity(email, 1L, cartItemUpdateRequest))
                    .isInstanceOf(UserNotFoundException.class);
        }

        @DisplayName("장바구니에 담긴 아이템 수량 변경시 장바구니 아이템 없으면 예외 발생")
        @Test
        void updateCartItemQuantityNotCartItemFound() {
            // given
            CartItemUpdateRequest cartItemUpdateRequest = new CartItemUpdateRequest(5);
            String email = "test@example.com";
            User user = new User(1L, email, "1234");
            when(userRepository.findByEmail(new Email(email)))
                    .thenReturn(Optional.of(user));
            when(cartItemRepository.findById(1L))
                    .thenReturn(Optional.empty());

            // when, then
            assertThatCode(
                    () -> cartService.updateCartItemQuantity(email, 1L, cartItemUpdateRequest))
                    .isInstanceOf(CartItemNotFoundException.class);
        }

        @DisplayName("장바구니에 담긴 아이템 수량 변경시 유저의 장바구니 아이템이 아닐 시 접근 제한 예외 발생")
        @Test
        void updateCartItemQuantityNotUsersItem() {
            // given
            CartItemUpdateRequest cartItemUpdateRequest = new CartItemUpdateRequest(5);
            String email = "test@example.com";
            User user1 = new User(1L, email, "1234");
            User user2 = new User(2L, "aaa@bbb.ccc", "1234");
            Product product = new Product(1L, "chicken", "/chicken.jpg", 10_000L);
            when(userRepository.findByEmail(new Email(email)))
                    .thenReturn(Optional.of(user1));
            when(cartItemRepository.findById(1L))
                    .thenReturn(Optional.of(new CartItem(1L, user2, product, 1)));

            // when, then
            assertThatCode(
                    () -> cartService.updateCartItemQuantity(email, 1L, cartItemUpdateRequest))
                    .isInstanceOf(UserNotMatchException.class);
        }
    }

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
