package shopping.cart.service;

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
import shopping.auth.domain.entity.UserEntity;
import shopping.auth.repository.UserRepository;
import shopping.cart.domain.entity.CartItemEntity;
import shopping.cart.domain.entity.ProductEntity;
import shopping.cart.dto.request.CartItemAddRequest;
import shopping.cart.dto.request.CartItemUpdateRequest;
import shopping.cart.dto.response.CartItemResponse;
import shopping.cart.repository.CartItemRepository;
import shopping.cart.repository.ProductRepository;

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
        CartItemEntity cartItemChicken = new CartItemEntity(1L, user, chicken, 1);
        CartItemEntity cartItemPizza = new CartItemEntity(2L, user, pizza, 1);
        List<CartItemEntity> cartItems = List.of(cartItemChicken, cartItemPizza);
        when(cartItemRepository.findByUserId(userId)).thenReturn(cartItems);

        /* when */
        List<CartItemResponse> cartItemResponses = cartService.getCartItems(userId);

        /* then */
        verify(cartItemRepository).findByUserId(userId);
        assertThat(cartItemResponses).hasSize(cartItems.size());
    }

    @Test
    @DisplayName("성공 : 장바구니 상품 갯수를 수정한다.")
    void successUpdateCartItem() {
        /* given */
        Long userId = 1L;
        UserEntity user = new UserEntity(userId, "test_email@woowafriends.com", "test_password!");
        ProductEntity chicken = new ProductEntity(1L, "치킨", "fried_chicken.png", 20000);
        CartItemEntity cartItemChicken = new CartItemEntity(1L, user, chicken, 1);

        Long cartItemId = cartItemChicken.getId();
        int updateQuantity = 3;
        when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.of(cartItemChicken));

        /* when */
        cartService.updateCartItemQuantity(cartItemId, new CartItemUpdateRequest(updateQuantity),
            userId);

        /* then */
        assertThat(cartItemChicken.getQuantity()).isEqualTo(updateQuantity);

    }

    @Test
    @DisplayName("성공 : 장바구니 상품을 삭제한다.")
    void successDeleteCartItem() {
        /* given */
        Long userId = 1L;
        UserEntity user = new UserEntity(userId, "test_email@woowafriends.com", "test_password!");
        ProductEntity chicken = new ProductEntity(1L, "치킨", "fried_chicken.png", 20000);
        CartItemEntity cartItemChicken = new CartItemEntity(1L, user, chicken, 1);

        Long cartItemId = cartItemChicken.getId();
        when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.of(cartItemChicken));

        /* when */
        cartService.removeCartItem(cartItemId, userId);

        /* then */
        verify(cartItemRepository).findById(cartItemId);
        verify(cartItemRepository).delete(cartItemChicken);
    }
}
