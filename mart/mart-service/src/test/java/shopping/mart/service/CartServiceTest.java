package shopping.mart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shopping.mart.app.api.cart.request.CartAddRequest;
import shopping.mart.app.api.cart.request.CartUpdateRequest;
import shopping.mart.app.domain.Cart;
import shopping.mart.app.domain.exception.DoesNotExistProductException;
import shopping.mart.app.spi.CartRepository;
import shopping.mart.app.spi.ProductRepository;

@ExtendWith(SpringExtension.class)
@DisplayName("CartService 테스트")
@ContextConfiguration(classes = {CartService.class})
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @MockBean
    private CartRepository cartRepository;

    @MockBean
    private ProductRepository productRepository;

    @Nested
    @DisplayName("addProduct 메소드는")
    class addProduct_method {

        @Test
        @DisplayName("product를 찾을 수 없으면, DoesNotExistProductException을 던진다.")
        void throw_DoesNotExistProductException_when_product_not_found() {
            // given
            CartAddRequest request = new CartAddRequest(9999999999L);
            Cart cart = new Cart(1L, 1L);

            when(cartRepository.getByUserId(anyLong())).thenReturn(cart);
            when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

            // when
            Exception exception = catchException(() -> cartService.addProduct(1L, request));

            // then
            assertThat(exception).isInstanceOf(DoesNotExistProductException.class);
        }
    }

    @Nested
    @DisplayName("updateProduct 메소드는")
    class updateProduct_method {

        @Test
        @DisplayName("product를 찾을 수 없으면, DoesNotExistProductException을 던진다.")
        void throw_DoesNotExistProductException_when_product_not_found() {
            // given
            CartUpdateRequest request = new CartUpdateRequest(9999999999L, 100);
            Cart cart = new Cart(1L, 1L);

            when(cartRepository.getByUserId(anyLong())).thenReturn(cart);
            when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

            // when
            Exception exception = catchException(() -> cartService.updateProduct(1L, request));

            // then
            assertThat(exception).isInstanceOf(DoesNotExistProductException.class);
        }
    }

    @Nested
    @DisplayName("deleteProduct 메소드는")
    class deleteProduct_method {

        @Test
        @DisplayName("product를 찾을 수 없으면, DoesNotExistProductException을 던진다.")
        void throw_DoesNotExistProductException_when_product_not_found() {
            // given
            long userId = 999999L;
            long productId = 99999999L;
            Cart cart = new Cart(1L, 1L);

            when(cartRepository.getByUserId(anyLong())).thenReturn(cart);
            when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

            // when
            Exception exception = catchException(
                    () -> cartService.deleteProduct(userId, productId));

            // then
            assertThat(exception).isInstanceOf(DoesNotExistProductException.class);
        }
    }
}
