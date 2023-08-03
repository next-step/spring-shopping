package shopping.cart.service;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.cart.domain.CartItem;
import shopping.cart.dto.ProductCartItemDto;
import shopping.cart.dto.request.CartItemCreationRequest;
import shopping.cart.dto.request.CartItemQuantityUpdateRequest;
import shopping.cart.dto.response.AllCartItemsResponse;
import shopping.cart.dto.response.CartItemResponse;
import shopping.cart.repository.CartItemRepository;
import shopping.exception.WooWaException;
import shopping.member.domain.Member;
import shopping.member.repository.MemberRepository;
import shopping.product.domain.Product;
import shopping.product.repository.ProductRepository;

@Service
@Transactional(readOnly = true)
public class CartService {

    public static final int DEFAULT_QUANTITY = 1;
    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public CartService(CartItemRepository cartItemRepository, MemberRepository memberRepository,
        ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void addCartItem(Long memberId, CartItemCreationRequest cartItemCreationRequest) {
        Member member = getMemberById(memberId);
        Product product = getProductById(cartItemCreationRequest.getProductId());

        CartItem cartItem = new CartItem(member.getId(), product.getId(), product.getName(), product.getPrice(),
            DEFAULT_QUANTITY);

        cartItemRepository.save(cartItem);
    }

    public AllCartItemsResponse findAllCartItem(Long memberId) {
        List<ProductCartItemDto> productCartItemDtos = cartItemRepository.findAllDtoByMemberId(memberId);
        List<CartItemResponse> changedCartItems = CartItemResponse.listOf(findChangedCartItem(productCartItemDtos));

        return new AllCartItemsResponse(!changedCartItems.isEmpty(), CartItemResponse.listOf(productCartItemDtos), changedCartItems);
    }

    private List<ProductCartItemDto> findChangedCartItem(List<ProductCartItemDto> productCartItemDtos) {
        return productCartItemDtos.stream()
            .filter(productCartItemDto -> productCartItemDto.getCartItem().isProductChanged(productCartItemDto.getProduct()))
            .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public void updateProductQuantity(Long memberId, Long cartItemId, CartItemQuantityUpdateRequest cartItemQuantityUpdateRequest) {
        getMemberById(memberId);
        CartItem cartItem = getCartItem(cartItemId);
        cartItem.validateMyCartItem(memberId);

        cartItem.updateQuantity(cartItemQuantityUpdateRequest.getQuantity());
    }

    @Transactional
    public void deleteCartItem(Long memberId, Long cartItemId) {
        getMemberById(memberId);
        CartItem cartItem = getCartItem(cartItemId);
        cartItem.validateMyCartItem(memberId);

        cartItemRepository.delete(cartItem);
    }

    private CartItem getCartItem(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new WooWaException("존재하지 않은 장바구니 품목입니다 id: '" + cartItemId + "'",
                BAD_REQUEST));
    }

    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new WooWaException("등록되지 않은 사람입니다.", HttpStatus.BAD_REQUEST));
    }

    private Product getProductById(Long productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new WooWaException("존재하지 않은 Product입니다 id: \'" + productId + "\'", BAD_REQUEST));
    }

}
