package shopping.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shopping.argumentresolver.annotation.UserId;
import shopping.dto.request.CartItemAddRequest;
import shopping.dto.request.CartItemUpdateRequest;
import shopping.dto.response.CartItemResponse;
import shopping.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);
    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/items")
    @ResponseBody
    public ResponseEntity<Void> addCartItem(@RequestBody CartItemAddRequest cartItemAddRequest,
        @UserId Long userId) {
        logger.info("userID : {}, productId : {}", userId, cartItemAddRequest.getProductId());
        cartService.addCartItem(cartItemAddRequest, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/items")
    @ResponseBody
    public ResponseEntity<List<CartItemResponse>> getCartItems(@UserId Long userId) {
        List<CartItemResponse> cartItems = cartService.getCartItems(userId);
        return ResponseEntity.ok(cartItems);
    }

    @GetMapping
    public String cartPage() {
        return "cart";
    }

    @PutMapping("/items/{cartItemId}/quantity")
    @ResponseBody
    public ResponseEntity<Void> updateCartItemQuantity(@PathVariable Long cartItemId,
        @RequestBody CartItemUpdateRequest cartItemUpdateRequest, @UserId Long userId) {
        cartService.updateCartItemQuantity(cartItemId, cartItemUpdateRequest, userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/items/{cartItemId}")
    @ResponseBody
    public ResponseEntity<Void> removeCartItem(@PathVariable Long cartItemId, @UserId Long userId) {
        cartService.removeCartItem(cartItemId, userId);
        return ResponseEntity.ok().build();
    }
}
