package shopping.service;

import static java.util.stream.Collectors.toList;
import static shopping.exception.ShoppingErrorType.NOT_FOUND_CART_ITEM_ID;
import static shopping.exception.ShoppingErrorType.NOT_FOUND_MEMBER_ID;
import static shopping.exception.ShoppingErrorType.NOT_FOUND_PRODUCT_ID;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.domain.cart.CartItem;
import shopping.domain.member.Member;
import shopping.domain.product.Product;
import shopping.dto.request.CartItemAddRequest;
import shopping.dto.request.CartItemUpdateRequest;
import shopping.dto.response.CartItemResponse;
import shopping.dto.response.CartItemResponses;
import shopping.exception.ShoppingException;
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

    @Transactional(readOnly = true)
    public CartItemResponses readCartItems(final Long memberId) {
        List<CartItemResponse> cartItems = cartItemRepository.findAllByMemberId(memberId).stream()
                .map(CartItemResponse::from)
                .collect(toList());

        return CartItemResponses.from(cartItems);
    }

    @Transactional
    public CartItemResponse addCartItem(final Long memberId, final CartItemAddRequest cartItemAddRequest) {
        final Product product = getProductById(cartItemAddRequest.getProductId());
        final Optional<CartItem> existsCartItem = cartItemRepository.findAllByMemberId(memberId).stream()
                .filter(cartItem -> cartItem.getProduct() == product)
                .findAny();

        if (existsCartItem.isPresent()) {
            final CartItem cartItem = existsCartItem.get();
            cartItem.plusQuantity();
            return CartItemResponse.from(cartItem);
        }

        final Member member = getMemberById(memberId);
        return CartItemResponse.from(addNewCartItem(member, product));
    }

    @Transactional
    public CartItemResponse updateCartItem(
            final Long memberId,
            final Long cartItemId,
            final CartItemUpdateRequest cartItemUpdateRequest
    ) {
        final Member member = getMemberById(memberId);
        final CartItem cartItem = getCartItemByIdWithValidMember(cartItemId, member);

        cartItem.updateQuantity(cartItemUpdateRequest.getQuantity());

        return CartItemResponse.from(cartItem);
    }

    @Transactional
    public void deleteCartItem(final Long memberId, final Long cartItemId) {
        final Member member = getMemberById(memberId);
        final CartItem cartItem = getCartItemByIdWithValidMember(cartItemId, member);

        cartItemRepository.delete(cartItem);
    }

    private Member getMemberById(final Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new ShoppingException(NOT_FOUND_MEMBER_ID));
    }

    private Product getProductById(final Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ShoppingException(NOT_FOUND_PRODUCT_ID));
    }

    private CartItem getCartItemByIdWithValidMember(final Long cartItemId, final Member member) {
        return cartItemRepository.findOneWithProductAndMemberById(cartItemId)
                .filter(cartItem -> cartItem.validateMember(member))
                .orElseThrow(() -> new ShoppingException(NOT_FOUND_CART_ITEM_ID));
    }

    private CartItem addNewCartItem(final Member member, final Product product) {
        CartItem cartItem = new CartItem(member, product);
        return cartItemRepository.save(cartItem);
    }
}
