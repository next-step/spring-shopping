package shopping.application;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shopping.domain.Product;
import shopping.domain.User;
import shopping.dto.CartItemCreateRequest;
import shopping.exception.UserNotFoundException;
import shopping.repository.CartItemRepository;
import shopping.repository.ProductRepository;
import shopping.repository.UserRepository;

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

    @DisplayName("장바구니에 상품 추가")
    @Test
    void createCartItem() {
        // given
        CartItemCreateRequest cartItemCreateRequest = new CartItemCreateRequest(1L);
        String email = "test@example.com";
        when(userRepository.findByEmail(email))
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
        when(userRepository.findByEmail(email))
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
        when(userRepository.findByEmail(email))
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
        when(userRepository.findByEmail(email))
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
