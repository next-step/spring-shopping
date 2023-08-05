package shopping.mart.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shopping.auth.interceptor.TokenPerRequest;
import shopping.core.util.ErrorTemplate;
import shopping.mart.domain.exception.AlreadyExistProductException;
import shopping.mart.domain.exception.DoesNotExistProductException;
import shopping.mart.domain.exception.NegativeProductCountException;
import shopping.mart.dto.CartAddRequest;
import shopping.mart.dto.CartResponse;
import shopping.mart.dto.CartUpdateRequest;
import shopping.mart.service.CartService;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;
    private final TokenPerRequest tokenPerRequest;

    CartController(CartService cartService, TokenPerRequest tokenPerRequest) {
        this.cartService = cartService;
        this.tokenPerRequest = tokenPerRequest;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@RequestBody CartAddRequest request) {
        cartService.addProduct(Long.parseLong(tokenPerRequest.getDecryptedToken()), request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CartResponse findAllProducts() {
        return cartService.getCart(Long.parseLong(tokenPerRequest.getDecryptedToken()));
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateProductCount(@RequestBody CartUpdateRequest request) {
        cartService.updateProduct(Long.parseLong(tokenPerRequest.getDecryptedToken()), request);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCart(@RequestParam(name = "product-id") Long productId) {
        cartService.deleteProduct(Long.parseLong(tokenPerRequest.getDecryptedToken()), productId);
    }

    @ExceptionHandler({AlreadyExistProductException.class,
        DoesNotExistProductException.class,
        NegativeProductCountException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorTemplate handleBadRequest(RuntimeException runtimeException) {
        return new ErrorTemplate(runtimeException.getMessage());
    }

}
