package shopping.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shopping.domain.entity.CartItemEntity;
import shopping.domain.entity.ProductEntity;
import shopping.domain.entity.UserEntity;
import shopping.dto.request.CartItemAddRequest;
import shopping.dto.response.CartItemResponse;
import shopping.repository.CartItemRepository;
import shopping.repository.ProductRepository;
import shopping.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("CartServiceTest")
class CartServiceTest {

    @Mock
    private CartItemRepository cartItemRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private CartService cartService;

    @Test
    @DisplayName("성공 : 장바구니에 상품을 추가한다.")
    void successAddCartItem() {
        /* given */
        Long productId = 1L;
        Long userId = 1L;
        UserEntity user = new UserEntity(userId, "test_email@woowafriends.com", "test_password!");
        ProductEntity product = new ProductEntity(productId, "치킨", "fried_chicken.png", 20000);
        CartItemAddRequest cartRequest = new CartItemAddRequest(productId);
        when(userRepository.getReferenceById(userId)).thenReturn(user);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        /* when */
        cartService.addCartItem(cartRequest, userId);

        /* then */
        verify(userRepository).getReferenceById(userId);
        verify(productRepository).findById(productId);
        verify(cartItemRepository).save(any());
    }

    @Test
    @DisplayName("성공 : 장바구니 상품을 조회한다.")
    void successReadCartItems() {
        /* given */
        Long userId = 1L;
        UserEntity user = new UserEntity(userId, "test_email@woowafriends.com", "test_password!");
        ProductEntity chicken = new ProductEntity(1L, "치킨", "fried_chicken.png", 20000);
        ProductEntity pizza = new ProductEntity(2L, "피자", "pizza.png", 25000);
        CartItemEntity cartItemEntityChicken = new CartItemEntity(1L, user, chicken, 1);
        CartItemEntity cartItemEntityPizza = new CartItemEntity(2L, user, pizza, 1);
        List<CartItemEntity> cartItems = List.of(cartItemEntityChicken, cartItemEntityPizza);
        when(cartItemRepository.findByUserId(userId)).thenReturn(cartItems);

        /* when */
        List<CartItemResponse> cartItemResponses = cartService.getCartItems(userId);

        /* then */
        verify(cartItemRepository).findByUserId(userId);
        assertThat(cartItemResponses).hasSize(cartItems.size());
    }
}
