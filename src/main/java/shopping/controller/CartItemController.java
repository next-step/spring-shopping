package shopping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shopping.dto.request.CartItemAddRequest;
import shopping.dto.request.CartItemUpdateRequest;
import shopping.dto.response.CartItemResponse;
import shopping.dto.response.CartItemResponses;
import shopping.service.CartItemService;

@RestController
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(final CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping("/cart-items")
    public ResponseEntity<CartItemResponses> readCartItems(@RequestAttribute final Long loginMemberId) {
        final CartItemResponses cartItemResponses = cartItemService.readCartItems(loginMemberId);

        return ResponseEntity.ok(cartItemResponses);
    }

    @PostMapping("/cart-items")
    public ResponseEntity<CartItemResponse> addCartItem(
            @RequestAttribute final Long loginMemberId,
            @RequestBody final CartItemAddRequest cartItemAddRequest
    ) {
        final CartItemResponse cartItemResponse = cartItemService.addCartItem(loginMemberId, cartItemAddRequest);

        return ResponseEntity.ok(cartItemResponse);
    }

    @PatchMapping("/cart-items/{cartItemId}")
    public ResponseEntity<CartItemResponse> updateCartItem(
            @RequestAttribute final Long loginMemberId,
            @PathVariable final Long cartItemId,
            @RequestBody final CartItemUpdateRequest cartItemUpdateRequest
    ) {
        cartItemService.updateCartItem(loginMemberId, cartItemId, cartItemUpdateRequest);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/cart-items/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(
            @RequestAttribute final Long loginMemberId,
            @PathVariable final Long cartItemId
    ) {
        cartItemService.deleteCartItem(loginMemberId, cartItemId);

        return ResponseEntity.noContent().build();
    }
}
