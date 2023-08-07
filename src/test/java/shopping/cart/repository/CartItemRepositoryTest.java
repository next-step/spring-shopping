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
import shopping.product.domain.vo.Money;
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
        Product newProduct = new Product("치킨", new Image("url"), "10000");

        memberRepository.save(newMember);
        productRepository.save(newProduct);

        CartItem cartItem = new CartItem(newMember.getId(), newProduct.getId(), "치킨", new Money("10000"), 1);

        cartItemRepository.save(cartItem);

        // when
        List<ProductCartItemDto> productCartItemDtos = cartItemRepository.findAllDtoByMemberId(newMember.getId());

        // then
        Assertions.assertThat(productCartItemDtos).hasSize(1);
        Assertions.assertThat(productCartItemDtos.get(0).getCartItem()).isEqualTo(cartItem);
        Assertions.assertThat(productCartItemDtos.get(0).getProduct()).isEqualTo(newProduct);
    }
}
