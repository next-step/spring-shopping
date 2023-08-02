package shopping.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shopping.dto.request.CartItemAddRequest;
import shopping.dto.response.CartItemResponse;
import shopping.service.CartItemService;

@RestController
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(final CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping(value = "/cart-items")
    public ResponseEntity<CartItemResponse> addCartItem(
            @RequestAttribute final Long loginMemberId,
            @RequestBody final CartItemAddRequest cartItemAddRequest
    ) {
        final CartItemResponse cartItemResponse = cartItemService.addCartItem(loginMemberId, cartItemAddRequest);

        return ResponseEntity.ok().body(cartItemResponse);
    }
}
