package shopping.cart.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import shopping.cart.domain.CartItem;
import shopping.cart.dto.request.CartItemCreationRequest;
import shopping.cart.dto.response.AllCartItemsResponse;
import shopping.cart.repository.CartItemRepository;
import shopping.member.domain.Member;
import shopping.member.repository.MemberRepository;
import shopping.product.domain.Product;
import shopping.product.domain.vo.Money;
import shopping.product.repository.ProductRepository;

@DataJpaTest
@Import(CartService.class)
class CartServiceTest {

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartService cartService;

    @Test
    @DisplayName("장바구니에 아이템을 추가한다")
    void createCartItem() {
        Long memberId = memberRepository.save(new Member("email", "password")).getId();
        Long productId = productRepository.save(new Product("피자", "imageUrl", "10000")).getId();
        CartItemCreationRequest cartItemCreationRequest = new CartItemCreationRequest(productId);

        cartService.addCartItem(memberId, cartItemCreationRequest);

        CartItem cartItem = cartItemRepository.findAll().get(0);
        assertCartItem(1L, memberId, productId, "피자", new Money("10000"), cartItem);
    }

    @Test
    @DisplayName("장바구니의 모든 아이템을 찾는다")
    void findAllCartItem() {
        Long memberId = memberRepository.save(new Member("email", "password")).getId();
        Long productId = productRepository.save(new Product("피자", "imageUrl", "10000")).getId();
        CartItemCreationRequest cartItemCreationRequest = new CartItemCreationRequest(productId);
        cartService.addCartItem(memberId, cartItemCreationRequest);

        AllCartItemsResponse allCartItem = cartService.findAllCartItem(memberId);

        assertThat(allCartItem).extracting(AllCartItemsResponse::isChanged)
            .isEqualTo(false);
        assertThat(allCartItem).extracting(AllCartItemsResponse::getCartItemResponses)
            .extracting(List::size)
            .isEqualTo(1);
        assertThat(allCartItem).extracting(AllCartItemsResponse::getChangedCartItemResponses)
            .extracting(List::size)
            .isEqualTo(0);
    }

    @Test
    @DisplayName("장바구니의 상품 정보가 변경되었을 경우 변경된 상품 정보와 변경 여부를 반환한다.")
    void findAllCartItemWhenItemChanged() {
        Long memberId = memberRepository.save(new Member("email", "password")).getId();
        Product product = new Product("피자", "imageUrl", "10000");
        Long productId =  productRepository.save(product).getId();
        CartItemCreationRequest cartItemCreationRequest = new CartItemCreationRequest(productId);
        cartService.addCartItem(memberId, cartItemCreationRequest);
        product.updateValues("특가 피자", "5000", "changedImage");

        AllCartItemsResponse allCartItem = cartService.findAllCartItem(memberId);

        assertThat(allCartItem).extracting(AllCartItemsResponse::isChanged)
            .isEqualTo(true);
        assertThat(allCartItem).extracting(AllCartItemsResponse::getCartItemResponses)
            .extracting(List::size)
            .isEqualTo(1);
        assertThat(allCartItem).extracting(AllCartItemsResponse::getChangedCartItemResponses)
            .extracting(List::size)
            .isEqualTo(1);
    }



    private static void assertCartItem(Long id, Long memberId, Long productId, String productName, Money price, CartItem cartItem) {
        assertThat(cartItem).extracting("id").isEqualTo(id);
        assertThat(cartItem).extracting("memberId").isEqualTo(memberId);
        assertThat(cartItem).extracting("productId").isEqualTo(productId);
        assertThat(cartItem).extracting("productName").isEqualTo(productName);
        assertThat(cartItem).extracting("productPrice").isEqualTo(price);
        assertThat(cartItem).extracting("quantity").isEqualTo(1);
    }
}
