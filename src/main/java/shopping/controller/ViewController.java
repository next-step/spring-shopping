package shopping.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import shopping.application.ProductService;
import shopping.dto.web.response.ProductResponse;

@Controller
public class ViewController {

    private final ProductService productService;

    public ViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<ProductResponse> products = productService.findAll();
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

    @GetMapping("/order/{id}/detail")
    public String orderDetailPage(Model model, @PathVariable Long id) {
        model.addAttribute("orderId", id);
        return "order-detail";
    }

    @GetMapping("/order-history")
    public String orderHistoryPage() {
        return "order-history";
    }
}
