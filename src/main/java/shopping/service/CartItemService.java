package shopping.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.cart.CartItem;
import shopping.domain.member.Member;
import shopping.domain.product.Product;
import shopping.dto.request.CartItemAddRequest;
import shopping.dto.request.CartItemUpdateRequest;
import shopping.dto.response.CartItemResponse;
import shopping.dto.response.CartItemResponses;
import shopping.exception.ErrorCode;
import shopping.exception.ShoppingException;
import shopping.repository.CartItemRepository;
import shopping.repository.MemberRepository;
import shopping.repository.ProductRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

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

    @Transactional(readOnly = true)
    public CartItemResponses readCartItems(final Long memberId) {
        List<CartItemResponse> cartItems = cartItemRepository.findAllByMemberId(memberId).stream()
                .map(CartItemResponse::from)
                .collect(toList());

        return CartItemResponses.from(cartItems);
    }

    @Transactional
    public CartItemResponse addCartItem(final Long memberId, final CartItemAddRequest cartItemAddRequest) {
        final Member member = getMemberById(memberId);
        final Product product = getProductById(cartItemAddRequest.getProductId());

        if (existsCartItem(member, product)) {
            final CartItem cartItem = increaseCartItemQuantity(member, product);
            return CartItemResponse.from(cartItem);
        }

        final CartItem cartItem = addNewCartItem(member, product);
        return CartItemResponse.from(cartItem);
    }

    @Transactional
    public void updateCartItem(
            final Long memberId,
            final Long cartItemId,
            final CartItemUpdateRequest cartItemUpdateRequest
    ) {
        final Member member = getMemberById(memberId);
        final CartItem cartItem = getCartItemById(cartItemId);
        cartItem.validateMember(member);

        cartItem.updateQuantity(cartItemUpdateRequest.getQuantity());
    }

    @Transactional
    public void deleteCartItem(final Long memberId, final Long cartItemId) {
        final Member member = getMemberById(memberId);
        final CartItem cartItem = getCartItemById(cartItemId);
        cartItem.validateMember(member);

        cartItemRepository.delete(cartItem);
    }

    private Member getMemberById(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ShoppingException(ErrorCode.NOT_FOUND_MEMBER_ID));
    }

    private Product getProductById(final Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ShoppingException(ErrorCode.NOT_FOUND_PRODUCT_ID));
    }

    private CartItem getCartItemById(final Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ShoppingException(ErrorCode.NOT_FOUND_CART_ITEM_ID));
    }

    private boolean existsCartItem(final Member member, final Product product) {
        return cartItemRepository.existsByMemberAndProduct(member, product);
    }

    private CartItem increaseCartItemQuantity(final Member member, final Product product) {
        final CartItem cartItem = cartItemRepository.getByMemberAndProduct(member, product);
        cartItem.plusQuantity();
        return cartItem;
    }

    private CartItem addNewCartItem(final Member member, final Product product) {
        CartItem cartItem = new CartItem(member, product);
        return cartItemRepository.save(cartItem);
    }
}
