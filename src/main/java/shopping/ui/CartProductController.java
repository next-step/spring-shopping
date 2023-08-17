package shopping.ui;

import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import shopping.application.CartProductService;
import shopping.domain.CartProduct;
import shopping.dto.request.UpdateCartProductRequest;
import shopping.dto.response.FindCartProductResponse;
import shopping.ui.argumentresolver.Login;

@Controller
public class CartProductController {

    private final CartProductService cartProductService;

    public CartProductController(CartProductService cartProductService) {
        this.cartProductService = cartProductService;
    }

    @GetMapping("/cart")
    public String findCart() {
        return "cart";
    }

    @ResponseBody
    @PostMapping("/api/cart/{id}")
    public ResponseEntity<Void> addCartProduct(@Login Long memberId, @PathVariable long id) {
        CartProduct cartProduct = cartProductService.addProduct(memberId, id);
        return ResponseEntity.created(URI.create("/api/cart" + cartProduct.getId())).build();
    }

    @ResponseBody
    @GetMapping("/api/cart")
    public List<FindCartProductResponse> findCartProducts(@Login Long memberId) {
        return cartProductService.findCartProducts(memberId);
    }

    @ResponseBody
    @PatchMapping("/api/cart/{id}")
    public ResponseEntity<Void> updateCartProduct(@PathVariable Long id, @RequestBody UpdateCartProductRequest request) {
        cartProductService.updateCartProduct(id, request);
        return ResponseEntity.noContent().build();
    }

    @ResponseBody
    @DeleteMapping("/api/cart/{id}")
    public ResponseEntity<Void> deleteCartProduct(@PathVariable Long id) {
        cartProductService.deleteCartProduct(id);
        return ResponseEntity.noContent().build();
    }
}
