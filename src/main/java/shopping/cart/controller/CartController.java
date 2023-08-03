package shopping.cart.controller;

import static shopping.util.ValidUtils.validNotNull;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shopping.auth.annotation.AuthMember;
import shopping.auth.domain.LoggedInMember;
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
    public void addCartItem(@AuthMember LoggedInMember loggedInMember,
        @RequestBody CartItemCreationRequest cartItemCreationRequest) {
        validNotNull(cartItemCreationRequest.getProductId());
        cartService.addCartItem(loggedInMember, cartItemCreationRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public AllCartItemsResponse findAllCartItems(@AuthMember LoggedInMember loggedInMember) {
        return cartService.findAllCartItem(loggedInMember);
    }

    @PatchMapping("/{cartItemId}/quantity")
    @ResponseStatus(HttpStatus.OK)
    public void updateCartItemQuantity(
        @AuthMember LoggedInMember loggedInMember,
        @PathVariable Long cartItemId,
        @RequestBody CartItemQuantityUpdateRequest cartItemQuantityUpdateRequest
    ) {
        validNotNull(cartItemQuantityUpdateRequest.getQuantity());
        cartService.updateProductQuantity(loggedInMember, cartItemId, cartItemQuantityUpdateRequest);
    }

    @DeleteMapping("/{cartItemId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCartItemQuantity(@AuthMember LoggedInMember loggedInMember, @PathVariable Long cartItemId) {
        cartService.deleteCartItem(loggedInMember, cartItemId);
    }
}
