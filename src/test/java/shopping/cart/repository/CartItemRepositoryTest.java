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

    @Test
    @DisplayName("findAllDtoByCartItemIds 테스트")
    void findAllDtoByCartItemIds() {
        Member member = new Member("email", "zz");

        Product chicken = new Product("치킨", new Image("url"), "10000");
        Product pizza = new Product("피자", new Image("url"), "15000");
        Product cola = new Product("콜라", new Image("url"), "1000");

        memberRepository.save(member);
        productRepository.save(chicken);
        productRepository.save(pizza);
        productRepository.save(cola);

        CartItem chickenCartItem = new CartItem(member.getId(), chicken.getId(), "치킨", new Money("10000"), 1);
        CartItem pizzaCartItem = new CartItem(member.getId(), pizza.getId(), "피자", new Money("15000"), 1);
        CartItem colaCartItem = new CartItem(member.getId(), cola.getId(), "콜라", new Money("1000"), 1);

        cartItemRepository.save(chickenCartItem);
        cartItemRepository.save(pizzaCartItem);
        cartItemRepository.save(colaCartItem);

        List<ProductCartItemDto> queryResult = cartItemRepository.findAllDtoByCartItemIds(
            List.of(chickenCartItem.getId(), colaCartItem.getId())
        );

        Assertions.assertThat(queryResult).hasSize(2);
        Assertions.assertThat(queryResult.get(0)).extracting("product").isEqualTo(chicken);
        Assertions.assertThat(queryResult.get(0)).extracting("cartItem").isEqualTo(chickenCartItem);
        Assertions.assertThat(queryResult.get(1)).extracting("product").isEqualTo(cola);
        Assertions.assertThat(queryResult.get(1)).extracting("cartItem").isEqualTo(colaCartItem);
    }
}
