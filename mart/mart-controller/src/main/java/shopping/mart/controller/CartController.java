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
import shopping.auth.service.interceptor.TokenPerRequest;
import shopping.core.util.ErrorTemplate;
import shopping.mart.app.api.cart.CartUseCase;
import shopping.mart.app.api.cart.request.CartAddRequest;
import shopping.mart.app.api.cart.request.CartUpdateRequest;
import shopping.mart.app.api.cart.response.CartResponse;
import shopping.mart.app.domain.exception.AlreadyExistProductException;
import shopping.mart.app.domain.exception.DoesNotExistProductException;
import shopping.mart.app.domain.exception.NegativeProductCountException;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartUseCase cartUseCase;
    private final TokenPerRequest tokenPerRequest;

    public CartController(CartUseCase cartUseCase, TokenPerRequest tokenPerRequest) {
        this.cartUseCase = cartUseCase;
        this.tokenPerRequest = tokenPerRequest;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@RequestBody CartAddRequest request) {
        cartUseCase.addProduct(Long.parseLong(tokenPerRequest.getDecryptedToken()), request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CartResponse findAllProducts() {
        return cartUseCase.getCart(Long.parseLong(tokenPerRequest.getDecryptedToken()));
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateProductCount(@RequestBody CartUpdateRequest request) {
        cartUseCase.updateProduct(Long.parseLong(tokenPerRequest.getDecryptedToken()), request);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCart(@RequestParam(name = "product-id") Long productId) {
        cartUseCase.deleteProduct(Long.parseLong(tokenPerRequest.getDecryptedToken()), productId);
    }

    @ExceptionHandler({AlreadyExistProductException.class,
        DoesNotExistProductException.class,
        NegativeProductCountException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ErrorTemplate handleBadRequest(RuntimeException runtimeException) {
        return new ErrorTemplate(runtimeException.getMessage());
    }

}
