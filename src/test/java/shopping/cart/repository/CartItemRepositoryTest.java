package shopping.cart.repository;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shopping.cart.domain.CartItem;
import shopping.cart.dto.ProductCartItemDto;
import shopping.common.vo.Image;
import shopping.member.domain.Member;
import shopping.member.repository.MemberRepository;
import shopping.product.domain.Product;
import shopping.common.vo.Money;
import shopping.product.repository.ProductRepository;

@DataJpaTest
class CartItemRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartItemRepository cartItemRepository;

    @Test
    @DisplayName("findAllDtoByMemberId 테스트")
    void findAllDtoByMemberIdTest() {
        // given
        Member newMember = new Member("email", "zz");
        Member otherMember = new Member("email2", "zz2");
        Product newProduct = new Product("치킨", new Image("url"), "10000");

        memberRepository.save(newMember);
        memberRepository.save(otherMember);
        productRepository.save(newProduct);

        CartItem cartItem = new CartItem(newMember.getId(), newProduct.getId(), "치킨", new Money("10000"), 1);

        cartItemRepository.save(cartItem);

        // when
        List<ProductCartItemDto> newMemberProductCartItemDtos = cartItemRepository.findAllDtoByMemberId(newMember.getId());
        List<ProductCartItemDto> otherMemberProductCartItemDtos = cartItemRepository.findAllDtoByMemberId(otherMember.getId());

        // then
        Assertions.assertThat(newMemberProductCartItemDtos).hasSize(1);
        Assertions.assertThat(newMemberProductCartItemDtos.get(0).getCartItem()).isEqualTo(cartItem);
        Assertions.assertThat(newMemberProductCartItemDtos.get(0).getProduct()).isEqualTo(newProduct);

        Assertions.assertThat(otherMemberProductCartItemDtos).isEmpty();
    }
}
