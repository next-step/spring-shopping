package shopping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.dto.request.CartItemAddRequest;
import shopping.dto.request.CartItemUpdateRequest;
import shopping.dto.response.CartItemResponse;
import shopping.dto.response.CartItemResponses;
import shopping.service.CartItemService;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(final CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public ResponseEntity<CartItemResponses> readCartItems(@RequestAttribute final Long loginMemberId) {
        final CartItemResponses cartItemResponses = cartItemService.readCartItems(loginMemberId);

        return ResponseEntity.ok(cartItemResponses);
    }

    @PostMapping
    public ResponseEntity<CartItemResponse> addCartItem(
            @RequestAttribute final Long loginMemberId,
            @RequestBody final CartItemAddRequest cartItemAddRequest
    ) {
        final CartItemResponse cartItemResponse = cartItemService.addCartItem(loginMemberId, cartItemAddRequest);

        return ResponseEntity.ok(cartItemResponse);
    }

    @PatchMapping("/{cartItemId}")
    public ResponseEntity<CartItemResponse> updateCartItem(
            @RequestAttribute final Long loginMemberId,
            @PathVariable final Long cartItemId,
            @RequestBody final CartItemUpdateRequest cartItemUpdateRequest
    ) {
        final CartItemResponse cartItemResponse = cartItemService.updateCartItem(
                loginMemberId,
                cartItemId,
                cartItemUpdateRequest
        );

        return ResponseEntity.ok(cartItemResponse);
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(
            @RequestAttribute final Long loginMemberId,
            @PathVariable final Long cartItemId
    ) {
        cartItemService.deleteCartItem(loginMemberId, cartItemId);

        return ResponseEntity.ok().build();
    }
}
