package shopping.cart.repository;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shopping.cart.domain.CartItem;
import shopping.cart.dto.ProductCartItemDto;
import shopping.product.domain.vo.Image;
import shopping.common.vo.ImageStoreType;
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
        Member otherMember = new Member("other", "zz");
        Product newProduct = new Product("치킨", new Image(ImageStoreType.NONE, "url"), "10000");
        Product newProduct2 = new Product("피자", new Image(ImageStoreType.NONE, "url"), "100000");

        memberRepository.save(newMember);
        memberRepository.save(otherMember);
        productRepository.save(newProduct);
        productRepository.save(newProduct2);

        CartItem cartItem = new CartItem(newMember.getId(), newProduct.getId(), "치킨", new Money("10000"), 1);
        CartItem othersCartItem = new CartItem(otherMember.getId(), newProduct2.getId(), "피자", new Money("100000"), 1);

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
