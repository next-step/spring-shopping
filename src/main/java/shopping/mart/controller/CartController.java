package shopping.mart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shopping.mart.dto.CartAddRequest;
import shopping.mart.dto.CartResponse;
import shopping.mart.dto.CartUpdateRequest;
import shopping.mart.service.CartService;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@RequestAttribute(name = "userId") Long userId, @RequestBody CartAddRequest request) {
        cartService.addProduct(userId, request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CartResponse findAllProducts(@RequestAttribute(name = "userId") Long userId) {
        return cartService.findCart(userId);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateProductCount(@RequestAttribute(name = "userId") Long userId,
            @RequestBody CartUpdateRequest request) {
        cartService.updateProduct(userId, request);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCart(@RequestAttribute(name = "userId") Long userId,
            @RequestParam(name = "product-id") Long productId) {
        cartService.deleteProduct(userId, productId);
    }
}
