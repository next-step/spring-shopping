package shopping.cart.repository;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shopping.cart.domain.CartItem;
import shopping.cart.dto.ProductCartItemDto;
import shopping.member.domain.Member;
import shopping.member.repository.MemberRepository;
import shopping.product.domain.Product;
import shopping.product.domain.vo.Image;
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
        Member otherMember = new Member("other", "zz");
        Product newProduct = new Product("치킨", Image.from("url"), "10000");
        Product newProduct2 = new Product("피자", Image.from("url"), "100000");

        memberRepository.save(newMember);
        memberRepository.save(otherMember);
        productRepository.save(newProduct);
        productRepository.save(newProduct2);

        CartItem cartItem = new CartItem(newProduct, newMember);
        CartItem othersCartItem = new CartItem(newProduct2, otherMember);

        cartItemRepository.save(cartItem);
        cartItemRepository.save(othersCartItem);

        // when
        List<ProductCartItemDto> productCartItemDtos = cartItemRepository.findAllDtoByMemberId(newMember.getId());

        // then
        Assertions.assertThat(productCartItemDtos).hasSize(1);
        Assertions.assertThat(productCartItemDtos.get(0).getCartItem()).isEqualTo(cartItem);
        Assertions.assertThat(productCartItemDtos.get(0).getProduct()).isEqualTo(newProduct);
    }
}
