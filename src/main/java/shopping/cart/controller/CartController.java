package shopping.cart.controller;

import static shopping.util.ValidUtils.validNotNull;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shopping.auth.annotation.AuthMember;
import shopping.cart.dto.request.CartItemCreationRequest;
import shopping.cart.dto.request.CartItemQuantityUpdateRequest;
import shopping.cart.dto.response.AllCartItemsResponse;
import shopping.cart.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addCartItem(@AuthMember Long memberId,
        CartItemCreationRequest cartItemCreationRequest) {
        validNotNull(cartItemCreationRequest.getProductId());
        cartService.addCartItem(memberId, cartItemCreationRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public AllCartItemsResponse findAllCartItems(@AuthMember Long memberId) {
        return cartService.findAllCartItem(memberId);
    }

    @PatchMapping("/{cartItemId}/quantity")
    @ResponseStatus(HttpStatus.OK)
    public void updateCartItemQuantity(
        @AuthMember Long memberId,
        @PathVariable Long cartItemId,
        CartItemQuantityUpdateRequest cartItemQuantityUpdateRequest
    ) {
        validNotNull(cartItemQuantityUpdateRequest.getQuantity());
        cartService.updateProductQuantity(memberId, cartItemId, cartItemQuantityUpdateRequest);
    }

    @DeleteMapping("/{cartItemId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCartItemQuantity(@AuthMember Long memberId, @PathVariable Long cartItemId) {
        cartService.deleteCartItem(memberId, cartItemId);
    }
}
