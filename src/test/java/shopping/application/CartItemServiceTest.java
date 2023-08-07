package shopping.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import shopping.auth.PasswordEncoder;
import shopping.domain.cart.CartItem;
import shopping.domain.cart.Price;
import shopping.domain.cart.Product;
import shopping.domain.user.User;
import shopping.dto.request.CartItemCreateRequest;
import shopping.dto.request.CartItemUpdateRequest;
import shopping.dto.response.CartItemResponse;
import shopping.exception.CartItemNotFoundException;
import shopping.exception.ProductNotFoundException;
import shopping.exception.UserNotMatchException;
import shopping.repository.CartItemRepository;
import shopping.repository.ProductRepository;
import shopping.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("장바구니 서비스 통합 테스트")
class CartItemServiceTest extends ServiceTest {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Nested
    @DisplayName("장바구니 상품 조회")
    class WhenGetCartItem {

        @DisplayName("정상 조회")
        @Test
        void getCartItems() {
            // given
            String email = "test@example.com";
            String password = "1234";
            User savedUser = saveUser(email, password);

            Product chicken = new Product("치킨", "/chicken.jpg", 10_000L);
            Product pizza = new Product("피자", "/pizza.jpg", 20_000L);
            Product salad = new Product("샐러드", "/salad.jpg", 5_000L);
            List<Product> productList = List.of(chicken, pizza, salad);
            productRepository.saveAll(productList);

            List<CartItem> cartItems = List.of(
                    new CartItem(savedUser.getId(), chicken),
                    new CartItem(savedUser.getId(), pizza),
                    new CartItem(savedUser.getId(), salad)
            );
            List<CartItem> savedItems = cartItemRepository.saveAll(cartItems);

            // when
            List<CartItemResponse> responses = cartItemService.findAllByUserId(savedUser.getId(), PageRequest.of(0, 3));

            // then
            assertThat(responses).usingRecursiveComparison().isEqualTo(
                    savedItems.stream()
                            .map(CartItemResponse::of)
                            .collect(Collectors.toList())
            );
        }
    }

    @Nested
    @DisplayName("장바구니 상품 추가")
    class WhenCreateCartItem {

        @DisplayName("정상 추가")
        @Test
        void createCartItem() {
            // given
            String email = "test@example.com";
            String password = "1234";
            User savedUser = saveUser(email, password);

            String name = "치킨";
            String imageUrl = "/chicken.jpg";
            Long price = 10_000L;

            Product product = new Product(name, imageUrl, price);
            Product savedProduct = productRepository.save(product);

            CartItemCreateRequest cartItemCreateRequest = new CartItemCreateRequest(savedProduct.getId());

            // when
            cartItemService.createCartItem(savedUser.getId(), cartItemCreateRequest);

            // then
            List<CartItem> cartItems = cartItemRepository.findAll();
            assertThat(cartItems).hasSize(1);
            CartItem cartItem = cartItems.get(0);
            assertThat(cartItem.getProduct())
                    .extracting(Product::getName, Product::getImageUrl, Product::getPrice)
                    .containsExactly(name, imageUrl, new Price(price));
            assertThat(cartItem.getUserId()).isEqualTo(savedUser.getId());

        }

        @DisplayName("상품이 존재하지 않으면 예외 발생")
        @Test
        void notProductFoundThenThrow() {
            // given
            String email = "test@example.com";
            String password = "1234";
            User savedUser = saveUser(email, password);
            Long userId = savedUser.getId();

            CartItemCreateRequest cartItemCreateRequest = new CartItemCreateRequest(1L);

            // when & then
            assertThatThrownBy(() -> cartItemService.createCartItem(userId, cartItemCreateRequest))
                    .isInstanceOf(ProductNotFoundException.class);
        }

        @DisplayName("이미 장바구니에 담긴 아이템이면 수량 하나 추가")
        @Test
        void alreadyInCartThenAddQuantity() {
            // given
            String email = "test@example.com";
            String password = "1234";
            User savedUser = saveUser(email, password);

            String name = "치킨";
            String imageUrl = "/chicken.jpg";
            Long price = 10_000L;

            Product product = new Product(name, imageUrl, price);
            Product savedProduct = productRepository.save(product);

            CartItem cartItem = new CartItem(savedUser.getId(), product);
            int quantity = cartItem.getQuantity().getQuantity();
            cartItemRepository.save(cartItem);

            CartItemCreateRequest cartItemCreateRequest = new CartItemCreateRequest(savedProduct.getId());

            // when
            cartItemService.createCartItem(savedUser.getId(), cartItemCreateRequest);

            // then
            List<CartItem> cartItems = cartItemRepository.findAll();
            assertThat(cartItems).hasSize(1);
            CartItem updatedCartItem = cartItems.get(0);
            assertThat(updatedCartItem.getQuantity().getQuantity()).isEqualTo(quantity + 1);
            assertThat(updatedCartItem.getProduct())
                    .extracting(Product::getName, Product::getImageUrl, Product::getPrice)
                    .containsExactly(name, imageUrl, new Price(price));
            assertThat(updatedCartItem.getUserId()).isEqualTo(savedUser.getId());
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
            User savedUser = saveUser(email, password);

            String name = "치킨";
            String imageUrl = "/chicken.jpg";
            Long price = 10_000L;

            Product product = new Product(name, imageUrl, price);
            productRepository.save(product);

            CartItem cartItem = new CartItem(savedUser.getId(), product);
            CartItem savedCartItem = cartItemRepository.save(cartItem);

            Integer newQuantity = 5;
            CartItemUpdateRequest cartItemUpdateRequest = new CartItemUpdateRequest(newQuantity);

            // when
            cartItemService.updateCartItemQuantity(savedUser.getId(), savedCartItem.getId(), cartItemUpdateRequest);

            // then
            Optional<CartItem> optionalCartItem = cartItemRepository.findById(savedCartItem.getId());
            assertThat(optionalCartItem).isPresent();
            CartItem updatedCartItem = optionalCartItem.get();
            assertThat(updatedCartItem.getQuantity().getQuantity()).isEqualTo(newQuantity);
        }

        @DisplayName("상품이 존재하지 않으면 예외 발생")
        @Test
        void deleteCartItemNotCartItemFound() {
            // given
            String email = "test@example.com";
            String password = "1234";
            User savedUser = saveUser(email, password);
            Long userId = savedUser.getId();

            Integer newQuantity = 5;
            CartItemUpdateRequest cartItemUpdateRequest = new CartItemUpdateRequest(newQuantity);

            // when, then
            assertThatThrownBy(
                    () -> cartItemService.updateCartItemQuantity(userId, 1L, cartItemUpdateRequest))
                    .isInstanceOf(CartItemNotFoundException.class);
        }

        @DisplayName("사용자의 장바구니 아이템이 아니면 예외 발생")
        @Test
        void updateCartItemQuantityNotUsersItem() {
            // given
            String email = "test@example.com";
            String password = "1234";
            User savedUser = saveUser(email, password);
            Long userId = savedUser.getId();

            String otherEmail = "other@example.com";
            String otherPassword = "other";
            User savedOther = saveUser(otherEmail, otherPassword);

            String name = "치킨";
            String imageUrl = "/chicken.jpg";
            Long price = 10_000L;

            Product product = new Product(name, imageUrl, price);
            productRepository.save(product);

            CartItem cartItem = new CartItem(savedOther.getId(), product);
            CartItem savedCartItem = cartItemRepository.save(cartItem);
            Long cartItemId = savedCartItem.getId();

            Integer newQuantity = 5;
            CartItemUpdateRequest cartItemUpdateRequest = new CartItemUpdateRequest(newQuantity);

            // when, then
            assertThatThrownBy(
                    () -> cartItemService.updateCartItemQuantity(userId, cartItemId, cartItemUpdateRequest))
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
            User savedUser = saveUser(email, password);

            String name = "치킨";
            String imageUrl = "/chicken.jpg";
            Long price = 10_000L;

            Product product = new Product(name, imageUrl, price);
            productRepository.save(product);

            CartItem cartItem = new CartItem(savedUser.getId(), product);
            CartItem savedCartItem = cartItemRepository.save(cartItem);
            Long cartItemId = savedCartItem.getId();

            // when
            cartItemService.deleteCartItem(savedUser.getId(), cartItemId);

            // then
            Optional<CartItem> optionalCartItem = cartItemRepository.findById(cartItemId);
            assertThat(optionalCartItem).isEmpty();
        }

        @DisplayName("상품이 존재하지 않으면 예외 발생")
        @Test
        void deleteCartItemNotCartItemFound() {
            // given
            String email = "test@example.com";
            String password = "1234";
            User savedUser = saveUser(email, password);
            Long userId = savedUser.getId();

            // when, then
            assertThatThrownBy(() -> cartItemService.deleteCartItem(userId, 1L))
                    .isInstanceOf(CartItemNotFoundException.class);
        }

        @DisplayName("사용자의 장바구니 아이템이 아니면 예외 발생")
        @Test
        void deleteCartItemNotUsersItem() {
            // given
            String email = "test@example.com";
            String password = "1234";
            User savedUser = saveUser(email, password);
            Long userId = savedUser.getId();

            String otherEmail = "other@example.com";
            String otherPassword = "other";
            User savedOther = saveUser(otherEmail, otherPassword);

            String name = "치킨";
            String imageUrl = "/chicken.jpg";
            Long price = 10_000L;

            Product product = new Product(name, imageUrl, price);
            productRepository.save(product);

            CartItem cartItem = new CartItem(savedOther.getId(), product);
            CartItem savedCartItem = cartItemRepository.save(cartItem);
            Long cartItemId = savedCartItem.getId();

            // when, then
            assertThatThrownBy(() -> cartItemService.deleteCartItem(userId, cartItemId))
                    .isInstanceOf(UserNotMatchException.class);
        }

    }

    private User saveUser(String email, String digest) {
        User user = new User(email, digest);
        return userRepository.save(user);
    }
}