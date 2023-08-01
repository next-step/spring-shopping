package shopping.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shopping.argumentresolver.annotation.UserId;
import shopping.dto.request.CartItemAddRequest;
import shopping.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);
    private final CartService cartService;

    public CartController(final CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Void> addCartItem(@RequestBody CartItemAddRequest cartItemAddRequest,
        @UserId Long userId) {
        logger.info("userID : {}, productId : {}", userId, cartItemAddRequest.getProductId());
        cartService.addCartItem(cartItemAddRequest, userId);
        return ResponseEntity.ok().build();
    }
}
