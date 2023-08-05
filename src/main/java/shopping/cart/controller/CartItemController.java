package shopping.cart.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shopping.auth.argumentresolver.annotation.UserId;
import shopping.cart.dto.request.CartItemAddRequest;
import shopping.cart.dto.request.CartItemUpdateRequest;
import shopping.cart.dto.response.CartItemResponse;
import shopping.cart.service.CartService;

@RestController
@RequestMapping("/cart/items")
public class CartItemController {

    private final CartService cartService;

    public CartItemController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void addCartItem(@RequestBody CartItemAddRequest cartItemAddRequest,
        @UserId Long userId) {
        cartService.addCartItem(cartItemAddRequest, userId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CartItemResponse> getCartItems(@UserId Long userId) {
        return cartService.getCartItems(userId);
    }

    @PutMapping("/{cartItemId}/quantity")
    @ResponseStatus(HttpStatus.OK)
    public void updateCartItemQuantity(@PathVariable Long cartItemId,
        @RequestBody CartItemUpdateRequest cartItemUpdateRequest, @UserId Long userId) {
        cartService.updateCartItemQuantity(cartItemId, cartItemUpdateRequest, userId);
    }

    @DeleteMapping("/{cartItemId}")
    @ResponseBody
    public void removeCartItem(@PathVariable Long cartItemId, @UserId Long userId) {
        cartService.removeCartItem(cartItemId, userId);
    }
}
