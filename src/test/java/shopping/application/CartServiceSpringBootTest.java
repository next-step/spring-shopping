package shopping.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.CartItem;
import shopping.domain.Email;
import shopping.domain.Product;
import shopping.domain.User;
import shopping.dto.request.CartItemCreateRequest;
import shopping.dto.request.CartItemUpdateRequest;
import shopping.dto.response.CartItemResponse;
import shopping.exception.CartItemNotFoundException;
import shopping.exception.ProductAlreadyInCartException;
import shopping.exception.ProductNotFoundException;
import shopping.exception.UserNotFoundException;
import shopping.exception.UserNotMatchException;
import shopping.repository.CartItemRepository;
import shopping.repository.ProductRepository;
import shopping.repository.UserRepository;

@Transactional
@SpringBootTest
@DisplayName("장바구니 서비스 통합 테스트")
public class CartServiceSpringBootTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Nested
    @DisplayName("장바구니 물건 추가 테스트")
    class WhenCreateCartItem {

        @DisplayName("장바구니에 상품 추가")
        @Test
        void createCartItem() {
            // given
            String email = "test1234@example.com";
            User savedUser = userRepository.save(new User(999L, email, "1234"));
            Product savedProduct = productRepository.save(
                    new Product(987L, "chicken", "/chicken.jpg", 10_000L));

            CartItemCreateRequest cartItemCreateRequest =
                    new CartItemCreateRequest(savedProduct.getId());

            // when
            assertThatCode(() -> cartService.createCartItem(email, cartItemCreateRequest))
                    .doesNotThrowAnyException();

            // then
            assertThat(cartItemRepository.findAllByUserEmail(new Email(email)).get(0))
                    .extracting(CartItem::getProduct, CartItem::getUser, CartItem::getQuantity)
                    .contains(savedProduct, savedUser, 1);
        }

        @DisplayName("데이터베이스에 회원이 없어서 예외 발생")
        @Test
        void notUserFoundThenThrow() {
            // given
            String email = "test1234@example.com";
            Product savedProduct = productRepository.save(
                    new Product(987L, "chicken", "/chicken.jpg", 10_000L));

            CartItemCreateRequest cartItemCreateRequest =
                    new CartItemCreateRequest(savedProduct.getId());

            // when, then
            assertThatCode(() -> cartService.createCartItem(email, cartItemCreateRequest))
                    .isInstanceOf(UserNotFoundException.class);
        }

        @DisplayName("데이터베이스에 상품이 없어서 예외 발생")
        @Test
        void notProductFoundThenThrow() {
            // given
            String email = "test1234@example.com";
            User savedUser = userRepository.save(new User(999L, email, "1234"));

            CartItemCreateRequest cartItemCreateRequest =
                    new CartItemCreateRequest(987L);

            // when & then
            assertThatCode(() -> cartService.createCartItem(email, cartItemCreateRequest))
                    .isInstanceOf(ProductNotFoundException.class);
        }

        @DisplayName("이미 장바구니에 담긴 아이템일 예외 발생")
        @Test
        void alreadyInCartThenAddQuantity() {
            // given
            String email = "test1234@example.com";
            User savedUser = userRepository.save(new User(999L, email, "1234"));
            Product savedProduct = productRepository.save(
                    new Product(987L, "chicken", "/chicken.jpg", 10_000L));

            CartItemCreateRequest cartItemCreateRequest =
                    new CartItemCreateRequest(savedProduct.getId());
            cartService.createCartItem(email, cartItemCreateRequest);

            // when, then
            assertThatCode(() -> cartService.createCartItem(email, cartItemCreateRequest))
                    .isInstanceOf(ProductAlreadyInCartException.class);
        }
    }

    @Nested
    @DisplayName("장바구니 물건 조회 테스트")
    class WhenFindAllByEmail {

        @DisplayName("장바구니에서 상품 조회 성공")
        @Test
        void findAllByEmailThenReturn() {
            // given
            String email = "test1234@example.com";
            User savedUser = userRepository.save(new User(email, "1234"));
            Product savedProduct1 = productRepository.save(
                    new Product(9871L, "chicken1", "/chicken.jpg", 10_000L));
            Product savedProduct2 = productRepository.save(
                    new Product(9872L, "chicken2", "/chicken.jpg", 10_000L));

            CartItemCreateRequest cartItemCreateRequest1 =
                    new CartItemCreateRequest(savedProduct1.getId());
            CartItemCreateRequest cartItemCreateRequest2 =
                    new CartItemCreateRequest(savedProduct2.getId());
            cartService.createCartItem(email, cartItemCreateRequest1);
            cartService.createCartItem(email, cartItemCreateRequest2);

            // when
            List<CartItemResponse> cartItemResponses = cartService.findAllByEmail(email);

            // then
            assertThat(cartItemResponses).hasSize(2);
        }
    }


    @Nested
    @DisplayName("장바구니 물건 수량 변경 테스트")
    class WhenUpdateCartItemQuantity {

        @DisplayName("장바구니에 담긴 아이템 수량 변경 성공")
        @Test
        void updateCartItemQuantity() {
            // given
            CartItemUpdateRequest cartItemUpdateRequest = new CartItemUpdateRequest(5);

            String email = "test1234@example.com";
            User savedUser = userRepository.save(new User(email, "1234"));
            Product savedProduct1 = productRepository.save(
                    new Product(9871L, "chicken1", "/chicken.jpg", 10_000L));

            CartItem cartItem = cartItemRepository.save(new CartItem(savedUser, savedProduct1));

            // when
            assertThatCode(() -> cartService.updateCartItemQuantity(
                    email,
                    cartItem.getId(),
                    cartItemUpdateRequest
            )).doesNotThrowAnyException();

            // then
            Integer changedQuantity = cartItemRepository.findById(cartItem.getId()).get()
                    .getQuantity();
            assertThat(changedQuantity).isEqualTo(5);
        }

        @DisplayName("장바구니에 담긴 아이템 수량 변경시 유저 없으면 예외 발생")
        @Test
        void updateCartItemQuantityNotUserFound() {
            // given
            CartItemUpdateRequest cartItemUpdateRequest = new CartItemUpdateRequest(5);

            // when, then
            assertThatCode(() -> cartService.updateCartItemQuantity(
                    "wrongEmail@email.com",
                    10000L,
                    cartItemUpdateRequest
            )).isInstanceOf(UserNotFoundException.class);
        }

        @DisplayName("장바구니에 담긴 아이템 수량 변경시 장바구니 아이템 없으면 예외 발생")
        @Test
        void updateCartItemQuantityNotCartItemFound() {
            // given
            CartItemUpdateRequest cartItemUpdateRequest = new CartItemUpdateRequest(5);

            String email = "test1234@example.com";
            User savedUser = userRepository.save(new User(email, "1234"));
            Product savedProduct1 = productRepository.save(
                    new Product(9871L, "chicken1", "/chicken.jpg", 10_000L));

            // when, then
            assertThatCode(
                    () -> cartService.updateCartItemQuantity(email, 100000L, cartItemUpdateRequest))
                    .isInstanceOf(CartItemNotFoundException.class);
        }

        @DisplayName("장바구니에 담긴 아이템 수량 변경시 유저의 장바구니 아이템이 아닐 시 접근 제한 예외 발생")
        @Test
        void updateCartItemQuantityNotUsersItem() {
            // given
            CartItemUpdateRequest cartItemUpdateRequest = new CartItemUpdateRequest(5);

            String email = "test1234@example.com";
            String anotherUserEmail = "wrongemail@email.com";
            User savedUser = userRepository.save(new User(email, "1234"));
            User anotherUser = userRepository.save(new User(anotherUserEmail, "1234"));
            Product savedProduct1 = productRepository.save(
                    new Product(9871L, "chicken1", "/chicken.jpg", 10_000L));

            CartItem cartItem = cartItemRepository.save(new CartItem(savedUser, savedProduct1));

            // when, then
            assertThatCode(() -> cartService.updateCartItemQuantity(
                    anotherUserEmail,
                    cartItem.getId(),
                    cartItemUpdateRequest
            )).isInstanceOf(UserNotMatchException.class);
        }
    }

    @Nested
    @DisplayName("장바구니 물건 삭제 테스트")
    class WhenDeleteCartItem {

        @DisplayName("장바구니에 담긴 아이템 삭제")
        @Test
        void deleteCartItem() {
            // given
            String email = "test1234@example.com";
            User savedUser = userRepository.save(new User(email, "1234"));
            Product savedProduct1 = productRepository.save(
                    new Product(9871L, "chicken1", "/chicken.jpg", 10_000L));

            CartItem cartItem = cartItemRepository.save(new CartItem(savedUser, savedProduct1));

            // when
            assertThatCode(() -> cartService.deleteCartItem(email, cartItem.getId()))
                    .doesNotThrowAnyException();

            // then
            assertThat(cartItemRepository.findById(cartItem.getId())).isEmpty();
        }

        @DisplayName("장바구니에 담긴 아이템 삭제시 유저 없으면 예외 발생")
        @Test
        void deleteCartItemNotUserFound() {
            // given
            String email = "test1234@example.com";

            // when, then
            assertThatCode(() -> cartService.deleteCartItem(email, 19999L))
                    .isInstanceOf(UserNotFoundException.class);
        }

        @DisplayName("장바구니에 담긴 아이템 삭제시 장바구니 아이템 없으면 예외 발생")
        @Test
        void deleteCartItemNotCartItemFound() {
            // given
            String email = "test1234@example.com";
            User savedUser = userRepository.save(new User(email, "1234"));

            // when, then
            assertThatCode(() -> cartService.deleteCartItem(email, 19999L))
                    .isInstanceOf(CartItemNotFoundException.class);
        }

        @DisplayName("장바구니에 담긴 아이템 삭제시 유저의 장바구니 아이템이 아닐 시 접근 제한 예외 발생")
        @Test
        void deleteCartItemNotUsersItem() {
            // given
            String email = "test1234@example.com";
            String anotherUserEmail = "wrongemail@email.com";
            User savedUser = userRepository.save(new User(email, "1234"));
            User anotherUser = userRepository.save(new User(anotherUserEmail, "1234"));
            Product savedProduct1 = productRepository.save(
                    new Product(9871L, "chicken1", "/chicken.jpg", 10_000L));

            CartItem cartItem = cartItemRepository.save(new CartItem(savedUser, savedProduct1));

            // when, then
            assertThatCode(() -> cartService.deleteCartItem(anotherUserEmail, cartItem.getId()))
                    .isInstanceOf(UserNotMatchException.class);
        }
    }
}
