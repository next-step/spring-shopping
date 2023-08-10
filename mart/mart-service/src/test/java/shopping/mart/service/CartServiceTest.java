package shopping.mart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
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
import shopping.mart.app.api.cart.response.CartResponse;
import shopping.mart.app.domain.Cart;
import shopping.mart.app.domain.Product;
import shopping.mart.app.exception.DoesNotExistProductException;
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

    @Nested
    @DisplayName("getCart 메소드는")
    class getCart_method {

        @Test
        @DisplayName("userId에 해당하는 cart가 없을경우, user가 갖고있는 empty cart를 생성해 반환한다")
        void return_empty_cart_response_when_cannot_find_cart_by_user_id() {
            // given
            long userId = 1L;
            long cartId = 1L;
            Cart emptyCart = new Cart(cartId, userId);

            when(cartRepository.existCartByUserId(userId)).thenReturn(false);
            when(cartRepository.newCart(userId)).thenReturn(emptyCart);

            CartResponse expected = new CartResponse(cartId, List.of());

            // when
            CartResponse result = cartService.getCart(userId);

            // then
            Assertions.assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        }

        @Test
        @DisplayName("userId에 해당하는 cart가 있다면, CartResponse를 반환한다.")
        void return_cart_response_when_cart_exists() {
            // given
            long userId = 1L;
            long cartId = 1L;
            long productId = 1L;
            
            Cart cart = new Cart(cartId, userId);
            Product product = new Product(productId, "product", "default-image", "1000");
            cart.addProduct(product);

            when(cartRepository.existCartByUserId(userId)).thenReturn(true);
            when(cartRepository.getByUserId(userId)).thenReturn(cart);

            CartResponse expected = getCartResponse(cart, product);

            // when
            CartResponse result = cartService.getCart(userId);

            // then
            assertThat(result).usingRecursiveComparison().isEqualTo(expected);
        }

        private CartResponse getCartResponse(Cart cart, Product product) {
            return new CartResponse(cart.getCartId(),
                    List.of(new CartResponse.ProductResponse(product.getId(), 1, product.getImageUrl(),
                            product.getName(), product.getPrice())));
        }

    }

}
