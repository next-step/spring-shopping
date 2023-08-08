package shopping.order.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shopping.auth.service.interceptor.TokenPerRequest;
import shopping.order.app.api.receipt.ReceiptUseCase;
import shopping.order.app.api.receipt.response.ReceiptResponse;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    private final ReceiptUseCase receiptUseCase;
    private final TokenPerRequest tokenPerRequest;

    public ReceiptController(ReceiptUseCase receiptUseCase, TokenPerRequest tokenPerRequest) {
        this.receiptUseCase = receiptUseCase;
        this.tokenPerRequest = tokenPerRequest;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ReceiptResponse> orderHistory() {
        return receiptUseCase.findAllByUserId(Long.parseLong(tokenPerRequest.getDecryptedToken()));
    }
}
