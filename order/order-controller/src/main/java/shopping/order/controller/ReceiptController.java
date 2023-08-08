package shopping.order.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shopping.auth.service.interceptor.TokenPerRequest;
import shopping.core.util.ErrorTemplate;
import shopping.order.app.api.receipt.ReceiptUseCase;
import shopping.order.app.api.receipt.response.ReceiptDetailResponse;
import shopping.order.app.api.receipt.response.ReceiptResponse;
import shopping.order.app.domain.exception.DoesNotFindReceiptException;

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

    @GetMapping("/{receiptId}")
    public ReceiptDetailResponse receiptDetail(@PathVariable("receiptId") long receiptId) {
        return receiptUseCase.getByIdAndUserId(receiptId,
                Long.parseLong(tokenPerRequest.getDecryptedToken()));
    }

    @ExceptionHandler(DoesNotFindReceiptException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorTemplate handleDoesNotFindReceiptException(DoesNotFindReceiptException doesNotFindReceiptException) {
        return new ErrorTemplate(doesNotFindReceiptException.getMessage());
    }
}
