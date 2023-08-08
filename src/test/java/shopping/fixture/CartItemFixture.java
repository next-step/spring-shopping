package shopping.fixture;

import org.springframework.test.util.ReflectionTestUtils;
import shopping.domain.cart.CartItem;
import shopping.domain.cart.Quantity;
import shopping.domain.member.Member;
import shopping.domain.product.Product;

public class CartItemFixture {

    public static CartItem createCartItem(final Member member, final Product product, final Long id) {
        return createCartItem(member, product, Quantity.defaultValue(), id);
    }

    public static CartItem createCartItem(
            final Member member,
            final Product product,
            final Quantity quantity,
            final Long id
    ) {
        final CartItem cartItem = new CartItem(member, product, quantity);
        ReflectionTestUtils.setField(cartItem, "id", id);
        return cartItem;
    }
}
