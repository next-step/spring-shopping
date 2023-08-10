package shopping.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import shopping.application.ProductService;
import shopping.dto.request.ShoppingPageRequest;
import shopping.dto.response.ProductResponse;

@Controller
public class PageController {

    private static final String DEFAULT_PAGE = "1";
    private static final String DEFAULT_PAGE_SIZE = "12";

    private final ProductService productService;

    public PageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String index(Model model,
            @RequestParam(defaultValue = DEFAULT_PAGE) Integer page,
            @RequestParam(name = "size", defaultValue = DEFAULT_PAGE_SIZE) Integer pageSize) {

        Page<ProductResponse> products = productService
                .findAllByPage(ShoppingPageRequest.of(page, pageSize));
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/cart")
    public String getCartPage() {
        return "cart";
    }

    @GetMapping("/order-history/{orderId}")
    public String getOrderPage(@PathVariable Long orderId, Model model) {
        model.addAttribute("orderId", orderId);
        return "order-detail";
    }

    @GetMapping("/order-history")
    public String getOrderHistoryPage() {
        return "order-history";
    }
}
