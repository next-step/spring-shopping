package shopping.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.cart.CartItem;
import shopping.domain.cart.Quantity;
import shopping.domain.member.Member;
import shopping.domain.product.Product;
import shopping.dto.request.CartItemAddRequest;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingApiException;
import shopping.repository.CartItemRepository;
import shopping.repository.MemberRepository;
import shopping.repository.ProductRepository;

@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    public CartItemService(
            final CartItemRepository cartItemRepository,
            final ProductRepository productRepository,
            final MemberRepository memberRepository
    ) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void addCartItem(final Long memberId, final CartItemAddRequest cartItemAddRequest) {
        final Member member = getMemberById(memberId);
        final Product product = getProductById(cartItemAddRequest.getProductId());

        if (existsCartItem(member, product)) {
            increaseCartItemQuantity(member, product);
            return;
        }

        addNewCartItem(member, product);
    }

    private Member getMemberById(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ShoppingApiException(ErrorCode.NOT_FOUND_MEMBER_ID));
    }

    private Product getProductById(final Long cartItemAddRequest) {
        return productRepository.findById(cartItemAddRequest)
                .orElseThrow(() -> new ShoppingApiException(ErrorCode.NOT_FOUND_PRODUCT_ID));
    }

    private boolean existsCartItem(final Member member, final Product product) {
        return cartItemRepository.existsByMemberAndProduct(member, product);
    }

    private void increaseCartItemQuantity(final Member member, final Product product) {
        final CartItem cartItem = cartItemRepository.getByMemberAndProduct(member, product);
        cartItem.plusQuantity();
    }

    private void addNewCartItem(final Member member, final Product product) {
        CartItem cartItem = new CartItem(member, product, Quantity.from(1));
        cartItemRepository.save(cartItem);
    }
}
