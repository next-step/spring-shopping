package shopping.order.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import shopping.auth.service.interceptor.TokenPerRequest;
import shopping.order.app.api.receipt.ReceiptUseCase;
import shopping.order.app.api.receipt.response.ReceiptResponse;

@Controller
public class ReceiptViewController {

    private final ReceiptUseCase receiptUseCase;
    private final TokenPerRequest tokenPerRequest;

    public ReceiptViewController(ReceiptUseCase receiptUseCase, TokenPerRequest tokenPerRequest) {
        this.receiptUseCase = receiptUseCase;
        this.tokenPerRequest = tokenPerRequest;
    }

    @GetMapping("/order-history")
    public String orderHistory(Model model) {
        List<ReceiptResponse> receipts = receiptUseCase.findAllByUserId(
                Long.parseLong(tokenPerRequest.getDecryptedToken()));

        model.addAttribute("receipts", receipts);
        return "order-history.html";
    }
}
