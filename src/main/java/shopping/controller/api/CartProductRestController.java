package shopping.controller.api;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.auth.LoginUser;
import shopping.dto.request.CartProductRequest;
import shopping.dto.response.CartResponse;
import shopping.service.CartProductService;
import shopping.service.CartService;


@RestController
@RequestMapping("/api/cart")
public class CartProductRestController {

    private final CartProductService cartProductService;
    private final CartService cartService;

    public CartProductRestController(
        final CartProductService cartProductService,
        final CartService cartService
    ) {
        this.cartProductService = cartProductService;
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> createCartProduct(
        @LoginUser Long memberId,
        @RequestBody final CartProductRequest request
    ) {
        cartProductService.createCartProduct(memberId, request);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> getAllCartProducts(final @LoginUser Long memberId) {

        return ResponseEntity.ok().body(cartService.findAllCartProducts(memberId));
    }
}
