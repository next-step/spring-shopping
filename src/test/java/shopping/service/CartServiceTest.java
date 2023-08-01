package shopping.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shopping.domain.entity.ProductEntity;
import shopping.domain.entity.UserEntity;
import shopping.dto.request.CartItemAddRequest;
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
    void successAddItem() {
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
}
