package shopping.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.cart.Cart;
import shopping.dto.response.CartResponse;
import shopping.repository.CartProductRepository;

@Service
public class CartService {

    private final CartProductRepository cartProductRepository;

    public CartService(
        final CartProductRepository cartProductRepository
    ) {
        this.cartProductRepository = cartProductRepository;
    }

    @Transactional(readOnly = true)
    public List<CartResponse> findAllCartProducts(final Long memberId) {
        final Cart cart = new Cart(memberId, cartProductRepository.findAllByMemberId(memberId));

        return cart.getCartProducts().stream()
            .map(CartResponse::from)
            .collect(Collectors.toList());
    }
}
