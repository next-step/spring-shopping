package shopping.cart.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import shopping.auth.domain.entity.User;
import shopping.auth.repository.UserRepository;
import shopping.cart.domain.entity.CartItem;
import shopping.cart.domain.entity.Product;
import shopping.cart.domain.vo.Quantity;
import shopping.cart.dto.request.CartItemInsertRequest;
import shopping.cart.dto.request.CartItemUpdateRequest;
import shopping.cart.dto.response.CartItemResponse;
import shopping.cart.repository.CartItemRepository;
import shopping.cart.repository.ProductRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static shopping.TestUtils.createProductWithoutId;
import static shopping.TestUtils.createUserWithoutId;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = CartService.class))
@DisplayName("CartService 통합 테스트")
@ActiveProfiles("no-init-test")
class CartServiceTest {

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartService cartService;

    @Test
    @DisplayName("장바구니에 상품을 추가한다.")
    void insertCartItem() {
        /* given */
        final User user = userRepository.save(createUserWithoutId());
        final Product product = productRepository.save(createProductWithoutId("chicken", 10000));

        final CartItemInsertRequest request = new CartItemInsertRequest(product.getId());

        /* when */
        cartService.insertCartItem(request, user.getId());

        /* then */
        final List<CartItem> cartItems = cartItemRepository.findByUserId(user.getId());
        assertThat(cartItems).hasSize(1);
        final CartItem firstCartItem = cartItems.get(0);
        assertThat(firstCartItem.getProduct()).isEqualTo(product);
        assertThat(firstCartItem.getUser()).isEqualTo(user);
        assertThat(firstCartItem.getQuantity()).isEqualTo(new Quantity(1));
    }

    @Test
    @DisplayName("장바구니 상품을 조회한다.")
    void readCartItems() {
        /* given */
        final User user = userRepository.save(createUserWithoutId());
        final Product product = productRepository.save(createProductWithoutId("chicken", 10000));
        final CartItem cartItem = cartItemRepository.save(new CartItem(user, product));

        /* when */
        List<CartItemResponse> cartItemResponses = cartService.getCartItems(user.getId());

        /* then */
        assertThat(cartItemResponses).hasSize(1);
        final CartItemResponse cartItemResponse = cartItemResponses.get(0);
        assertThat(cartItemResponse.getCartItemId()).isEqualTo(cartItem.getId());
        assertThat(cartItemResponse.getName()).isEqualTo("chicken");
        assertThat(cartItemResponse.getPrice()).isEqualTo(10000);
        assertThat(cartItemResponse.getQuantity()).isEqualTo(1);
    }

    @Test
    @DisplayName("장바구니 상품 수량을 수정한다.")
    void updateCartItem() {
        /* given */
        final User user = userRepository.save(createUserWithoutId());
        final Product product = productRepository.save(createProductWithoutId("chicken", 10000));
        final CartItem cartItem = cartItemRepository.save(new CartItem(user, product));

        /* when */
        cartService.updateCartItemQuantity(cartItem.getId(), new CartItemUpdateRequest(3), user.getId());

        /* then */
        assertThat(cartItem.getQuantity()).isEqualTo(new Quantity(3));
    }

    @Test
    @DisplayName("장바구니 상품을 삭제한다.")
    void deleteCartItem() {
        /* given */
        final User user = userRepository.save(createUserWithoutId());
        final Product product = productRepository.save(createProductWithoutId("chicken", 10000));
        final CartItem cartItem = cartItemRepository.save(new CartItem(user, product));

        /* when */
        cartService.removeCartItem(cartItem.getId(), user.getId());

        /* then */
        assertThat(cartItemRepository.findByUserId(user.getId())).isEmpty();
    }
}
