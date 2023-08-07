package shopping.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static shopping.exception.ShoppingErrorType.FORBIDDEN_MODIFY_CART_ITEM;
import static shopping.exception.ShoppingErrorType.NOT_FOUND_MEMBER_ID;
import static shopping.exception.ShoppingErrorType.NOT_FOUND_PRODUCT_ID;
import static shopping.fixture.CartItemFixture.createCartItem;
import static shopping.fixture.MemberFixture.MEMBER_ID;
import static shopping.fixture.MemberFixture.createMember;
import static shopping.fixture.ProductFixture.CHICKEN_ID;
import static shopping.fixture.ProductFixture.CHICKEN_IMAGE;
import static shopping.fixture.ProductFixture.CHICKEN_NAME;
import static shopping.fixture.ProductFixture.CHICKEN_PRICE;
import static shopping.fixture.ProductFixture.PIZZA_IMAGE;
import static shopping.fixture.ProductFixture.PIZZA_NAME;
import static shopping.fixture.ProductFixture.PIZZA_PRICE;
import static shopping.fixture.ProductFixture.createChicken;
import static shopping.fixture.ProductFixture.createPizza;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import shopping.domain.cart.CartItem;
import shopping.domain.cart.Quantity;
import shopping.domain.member.Member;
import shopping.domain.product.Product;
import shopping.dto.request.CartItemAddRequest;
import shopping.dto.request.CartItemUpdateRequest;
import shopping.dto.response.CartItemResponse;
import shopping.dto.response.CartItemResponses;
import shopping.exception.ShoppingException;
import shopping.repository.CartItemRepository;
import shopping.repository.MemberRepository;
import shopping.repository.ProductRepository;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CartItemService.class)
class CartItemServiceTest {

    @Autowired
    private CartItemService cartItemService;

    @MockBean
    private CartItemRepository cartItemRepository;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private MemberRepository memberRepository;

    @Test
    @DisplayName("장바구니에 담긴 상품들 조회 테스트")
    void readCartItems() {
        final Member member = createMember(MEMBER_ID);
        final Quantity chickenQuantity = Quantity.from(10);
        final Quantity pizzaQuantity = Quantity.from(5);
        final List<CartItem> cartItems = List.of(
                createCartItem(member, createChicken(), chickenQuantity, 1L),
                createCartItem(member, createPizza(), pizzaQuantity, 2L)
        );
        when(cartItemRepository.findAllByMemberId(MEMBER_ID)).thenReturn(cartItems);

        final CartItemResponses cartItemResponses = cartItemService.readCartItems(MEMBER_ID);

        assertThat(cartItemResponses.getCartItems()).hasSize(2);
        assertThat(cartItemResponses.getCartItems())
                .extracting("cartItemId", "quantity", "product.image", "product.name", "product.price")
                .contains(
                        tuple(1L, chickenQuantity.getValue(), CHICKEN_IMAGE, CHICKEN_NAME, CHICKEN_PRICE),
                        tuple(2L, pizzaQuantity.getValue(), PIZZA_IMAGE, PIZZA_NAME, PIZZA_PRICE)
                );
    }

    @Test
    @DisplayName("장바구니에 상품 추가 테스트")
    void addCartItem() {
        final Long cartItemId = 1L;
        final Member member = createMember(MEMBER_ID);
        final Product product = createChicken();
        when(productRepository.findById(CHICKEN_ID)).thenReturn(Optional.of(product));
        when(cartItemRepository.findAllByMemberId(MEMBER_ID)).thenReturn(Collections.emptyList());
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));
        when(cartItemRepository.save(any())).thenReturn(createCartItem(member, product, cartItemId));

        final CartItemResponse cartItemResponse = cartItemService.addCartItem(MEMBER_ID,
                new CartItemAddRequest(CHICKEN_ID));

        assertThat(cartItemResponse)
                .extracting("cartItemId", "quantity", "product.image", "product.name", "product.price")
                .contains(cartItemId, 1, CHICKEN_IMAGE, CHICKEN_NAME, CHICKEN_PRICE);
    }

    @Test
    @DisplayName("장바구니에 해당 상품이 존재하는 경우 개수 추가 테스트")
    void addCartItemWithContainsProduct() {
        final Long cartItemId = 1L;
        final Member member = createMember(MEMBER_ID);
        final Product product = createChicken();
        final CartItem cartItem = createCartItem(member, product, cartItemId);
        when(productRepository.findById(CHICKEN_ID)).thenReturn(Optional.of(product));
        when(cartItemRepository.findAllByMemberId(MEMBER_ID)).thenReturn(List.of(cartItem));

        final CartItemResponse cartItemResponse = cartItemService.addCartItem(MEMBER_ID,
                new CartItemAddRequest(CHICKEN_ID));

        assertThat(cartItemResponse)
                .extracting("cartItemId", "quantity", "product.image", "product.name", "product.price")
                .contains(cartItemId, 2, CHICKEN_IMAGE, CHICKEN_NAME, CHICKEN_PRICE);
    }

    @Test
    @DisplayName("장바구니에 상품 추가 시 추가할 상품이 존재하지 않는 경우 예외를 던진다.")
    void addCartItemWithThrowNotFoundProductException() {
        when(productRepository.findById(CHICKEN_ID)).thenReturn(Optional.empty());

        final Exception exception = catchException(
                () -> cartItemService.addCartItem(MEMBER_ID, new CartItemAddRequest(CHICKEN_ID)));

        assertThat(exception).isInstanceOf(ShoppingException.class);
        assertThat(exception.getMessage()).isEqualTo(NOT_FOUND_PRODUCT_ID.getMessage());
    }

    @Test
    @DisplayName("장바구니에 상품 추가 시 잘못된 회원의 정보가 들어온 경우 예외를 던진다.")
    void addCartItemWithThrowNotFoundMemberException() {
        final Product product = createChicken();
        when(productRepository.findById(CHICKEN_ID)).thenReturn(Optional.of(product));
        when(cartItemRepository.findAllByMemberId(MEMBER_ID)).thenReturn(Collections.emptyList());
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.empty());

        final Exception exception = catchException(
                () -> cartItemService.addCartItem(MEMBER_ID, new CartItemAddRequest(CHICKEN_ID)));

        assertThat(exception).isInstanceOf(ShoppingException.class);
        assertThat(exception.getMessage()).isEqualTo(NOT_FOUND_MEMBER_ID.getMessage());
    }

    @Test
    @DisplayName("장바구니의 상품 개수 수정 테스트")
    void updateCartItem() {
        final Long cartItemId = 1L;
        final int updateQuantity = 30;
        final Member member = createMember(MEMBER_ID);
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));
        when(cartItemRepository.findOneWithProductAndMemberById(cartItemId))
                .thenReturn(Optional.of(createCartItem(member, createChicken(), cartItemId)));

        final CartItemResponse cartItemResponse = cartItemService.updateCartItem(MEMBER_ID, cartItemId,
                new CartItemUpdateRequest(updateQuantity));

        assertThat(cartItemResponse)
                .extracting("cartItemId", "quantity", "product.image", "product.name", "product.price")
                .contains(cartItemId, updateQuantity, CHICKEN_IMAGE, CHICKEN_NAME, CHICKEN_PRICE);
    }

    @Test
    @DisplayName("장바구니의 상품 개수 수정시 권한이 없는 회원이 삭제하려는 경우 예외를 던진다.")
    void updateCartItemWithException() {
        final Long cartItemId = 1L;
        final Long differentMemberId = 2L;
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(createMember(MEMBER_ID)));
        when(cartItemRepository.findOneWithProductAndMemberById(cartItemId))
                .thenReturn(Optional.of(createCartItem(createMember(differentMemberId), createChicken(), cartItemId)));

        final Exception exception = catchException(
                () -> cartItemService.updateCartItem(MEMBER_ID, cartItemId, new CartItemUpdateRequest(30)));

        assertThat(exception).isInstanceOf(ShoppingException.class);
        assertThat(exception.getMessage()).isEqualTo(FORBIDDEN_MODIFY_CART_ITEM.getMessage());
    }

    @Test
    @DisplayName("장바구니의 상품 삭제 테스트")
    void deleteCartItem() {
        final Long cartItemId = 1L;
        final Member member = createMember(MEMBER_ID);
        final CartItem cartItem = createCartItem(member, createChicken(), cartItemId);
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(member));
        when(cartItemRepository.findOneWithProductAndMemberById(cartItemId))
                .thenReturn(Optional.of(cartItem));

        cartItemService.deleteCartItem(MEMBER_ID, cartItemId);

        verify(cartItemRepository, times(1)).delete(cartItem);
    }


    @Test
    @DisplayName("장바구니의 상품 삭제 시 권한이 없는 회원이 삭제하려는 경우 예외를 던진다.")
    void deleteCartItemWithException() {
        final Long cartItemId = 1L;
        final Long differentMemberId = 2L;
        final Member differentMember = createMember(differentMemberId);
        final CartItem cartItem = createCartItem(differentMember, createChicken(), cartItemId);
        when(memberRepository.findById(MEMBER_ID)).thenReturn(Optional.of(createMember(MEMBER_ID)));
        when(cartItemRepository.findOneWithProductAndMemberById(cartItemId)).thenReturn(Optional.of(cartItem));

        final Exception exception = catchException(
                () -> cartItemService.deleteCartItem(MEMBER_ID, cartItemId));

        assertThat(exception).isInstanceOf(ShoppingException.class);
        assertThat(exception.getMessage()).isEqualTo(FORBIDDEN_MODIFY_CART_ITEM.getMessage());
    }
}
