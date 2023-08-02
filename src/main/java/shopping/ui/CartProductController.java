package shopping.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import shopping.application.CartProductService;
import shopping.dto.FindCartProductResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/cart/products")
public class CartProductController {

    private final CartProductService cartProductService;

    public CartProductController(CartProductService cartProductService) {
        this.cartProductService = cartProductService;
    }

    @ResponseBody
    @GetMapping
    public List<FindCartProductResponse> findCartProducts(HttpServletRequest request) {
        return cartProductService.findCartProducts((Long) request.getAttribute("memberId"));
    }

    @ResponseBody
    @PostMapping("/{productId}")
    public void addCartProduct(@PathVariable Long productId, HttpServletRequest request) {
        cartProductService.addProduct((Long) request.getAttribute("memberId"), productId);
    }
}
