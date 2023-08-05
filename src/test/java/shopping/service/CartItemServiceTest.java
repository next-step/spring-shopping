package shopping.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import shopping.domain.cart.CartItem;
import shopping.domain.member.Member;
import shopping.domain.product.Product;
import shopping.dto.request.CartItemAddRequest;
import shopping.dto.request.CartItemUpdateRequest;
import shopping.dto.response.CartItemResponse;
import shopping.dto.response.CartItemResponses;
import shopping.dto.response.ProductResponse;
import shopping.exception.ShoppingException;
import shopping.repository.CartItemRepository;
import shopping.repository.MemberRepository;
import shopping.repository.ProductRepository;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.groups.Tuple.tuple;

@DataJpaTest
@Import(CartItemService.class)
class CartItemServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartItemService cartItemService;

    @Test
    @DisplayName("사용자가 장바구니 아이템을 모두 조회하면 사용자 장바구니 아이템이 모두 응답된다.")
    void readCartItems() {
        final Member anyMember = getAnyMember();
        final List<Product> products = getProducts();
        saveCartItems(anyMember, products);

        final CartItemResponses response = cartItemService.readCartItems(anyMember.getId());

        assertThat(response.getCartItems().stream()
                .map(CartItemResponse::getProduct)
                .map(ProductResponse::getId)
                .collect(toList()))
                .containsAll(productMapToId(products));
    }

    @Test
    @DisplayName("사용자가 상품 아이디로 장바구니 아이템을 추가할 수 있다.")
    void addNewCartItem() {
        final Member anyMember = getAnyMember();
        final Product anyProduct = getAnyProduct();
        final CartItemAddRequest cartItemAddRequest = new CartItemAddRequest(anyProduct.getId());

        cartItemService.addCartItem(anyMember.getId(), cartItemAddRequest);

        assertThat(cartItemRepository.findAllByMemberId(anyMember.getId()))
                .hasSize(1)
                .extracting("quantity", "member", "product")
                .contains(tuple(1, anyMember, anyProduct));
    }

    @Test
    @DisplayName("사용자가 상품 아이디로 장바구니 아이템을 추가할 때 이미 있으면 수량을 더한다.")
    void addCartItemIncrease() {
        final Member anyMember = getAnyMember();
        final Product anyProduct = getAnyProduct();
        final CartItemAddRequest cartItemAddRequest = new CartItemAddRequest(anyProduct.getId());

        cartItemService.addCartItem(anyMember.getId(), cartItemAddRequest);
        cartItemService.addCartItem(anyMember.getId(), cartItemAddRequest);

        assertThat(cartItemRepository.findAllByMemberId(anyMember.getId()))
                .hasSize(1)
                .extracting("quantity", "member", "product")
                .contains(tuple(2, anyMember, anyProduct));
    }

    @Test
    @DisplayName("사용자가 장바구니 아이템 아이디로 장바구니 아이템 수량을 변경하면 장바구니 아이템 수량이 변경된다.")
    void updateCartItem() {
        final Member anyMember = getAnyMember();
        final Product anyProduct = getAnyProduct();
        saveCartItems(anyMember, anyProduct);
        final CartItem anyCartItemByMember = getAnyCartItemByMember(anyMember);
        final CartItemUpdateRequest cartItemUpdateRequest = new CartItemUpdateRequest(9);

        cartItemService.updateCartItem(anyMember.getId(), anyCartItemByMember.getId(), cartItemUpdateRequest);

        assertThat(cartItemRepository.findAllByMemberId(anyMember.getId()))
                .hasSize(1)
                .extracting("quantity", "member", "product")
                .contains(tuple(9, anyMember, anyProduct));
    }

    @Test
    @DisplayName("다른 사용자가 장바구니 아이템 아이디로 장바구니 아이템 수량 변경을 시도하면 예외가 발생한다.")
    void updateCartItemFail() {
        final Member anyMember = getAnyMember();
        final Member OtherMember = getOtherMember(anyMember);
        final Product anyProduct = getAnyProduct();
        saveCartItems(anyMember, anyProduct);
        final CartItem anyCartItemByMember = getAnyCartItemByMember(anyMember);
        final CartItemUpdateRequest cartItemUpdateRequest = new CartItemUpdateRequest(9);

        assertThatCode(() -> cartItemService.updateCartItem(OtherMember.getId(), anyCartItemByMember.getId(), cartItemUpdateRequest))
                .isInstanceOf(ShoppingException.class)
                .hasMessage("해당 장바구니 아이템을 수정할 권한이 없습니다.");
    }


    @Test
    @DisplayName("사용자가 장바구니 아이템 아이디로 장바구니 아이템을 삭제하면 수량에 관계 없이 삭제된다.")
    void deleteCartItem() {
        final Member anyMember = getAnyMember();
        final Product anyProduct = getAnyProduct();
        saveCartItems(anyMember, anyProduct);
        final CartItem anyCartItemByMember = getAnyCartItemByMember(anyMember);

        cartItemService.deleteCartItem(anyMember.getId(), anyCartItemByMember.getId());

        assertThat(cartItemRepository.findAllByMemberId(anyMember.getId())).isEmpty();
    }

    @Test
    @DisplayName("다른 사용자가 장바구니 아이템 아이디로 장바구니 아이템 삭제를 시도하면 예외가 발생한다.")
    void deleteCartItemFail() {
        final Member anyMember = getAnyMember();
        final Member OtherMember = getOtherMember(anyMember);
        final Product anyProduct = getAnyProduct();
        saveCartItems(anyMember, anyProduct);
        final CartItem anyCartItemByMember = getAnyCartItemByMember(anyMember);

        assertThatCode(() -> cartItemService.deleteCartItem(OtherMember.getId(), anyCartItemByMember.getId()))
                .isInstanceOf(ShoppingException.class)
                .hasMessage("해당 장바구니 아이템을 수정할 권한이 없습니다.");
    }


    private void saveCartItems(final Member anyMember, final Product... products) {
        saveCartItems(anyMember, Arrays.stream(products).collect(toList()));
    }

    private void saveCartItems(final Member anyMember, final List<Product> products) {
        for (Product product : products) {
            final CartItem cartItem = new CartItem(anyMember, product);
            cartItemRepository.save(cartItem);
        }
    }

    private Member getAnyMember() {
        return memberRepository.findAll().stream()
                .findAny()
                .orElseThrow(IllegalStateException::new);
    }

    private Member getOtherMember(Member anyMember) {
        return memberRepository.findAll().stream()
                .filter(member -> !member.equals(anyMember))
                .findAny()
                .orElseThrow(IllegalStateException::new);
    }

    private Product getAnyProduct() {
        return productRepository.findAll().stream()
                .findAny()
                .orElseThrow(IllegalStateException::new);
    }

    private CartItem getAnyCartItemByMember(Member member) {
        return cartItemRepository.findAllByMemberId(member.getId()).stream()
                .findAny()
                .orElseThrow(IllegalStateException::new);
    }

    private List<Product> getProducts() {
        return productRepository.findAll();
    }

    private List<Long> productMapToId(final List<Product> products) {
        return products.stream()
                .map(Product::getId)
                .collect(toList());
    }
}
