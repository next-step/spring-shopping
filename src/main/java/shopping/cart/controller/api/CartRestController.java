package shopping.cart.controller.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.auth.dto.LoginUser;
import shopping.cart.dto.request.CartProductCreateRequest;
import shopping.cart.dto.request.CartProductQuantityUpdateRequest;
import shopping.cart.dto.response.CartResponse;
import shopping.cart.service.CartService;


@RestController
@RequestMapping("/api/cartProduct")
public class CartRestController {

    private final CartService cartService;

    public CartRestController(
        final CartService cartService
    ) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Void> createCartProduct(
        @LoginUser Long memberId,
        @RequestBody final CartProductCreateRequest request
    ) {
        cartService.createCartProduct(memberId, request);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CartResponse>> getAllCartProducts(final @LoginUser Long memberId) {
        return ResponseEntity.ok().body(cartService.findAllCartProducts(memberId));
    }

    @PatchMapping("/{cartProductId}")
    public ResponseEntity<Void> updateCartProductQuantity(
        final @LoginUser Long memberId,
        final @PathVariable Long cartProductId,
        final @RequestBody CartProductQuantityUpdateRequest request
    ) {
        cartService.updateCartProductQuantity(cartProductId, memberId, request);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{cartProductId}")
    public ResponseEntity<Void> deleteCartProduct(
        final @PathVariable Long cartProductId,
        final @LoginUser Long memberId
    ) {
        cartService.deleteCartProduct(cartProductId, memberId);

        return ResponseEntity.noContent().build();
    }
}
