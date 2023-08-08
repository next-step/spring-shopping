package shopping.order.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.auth.dto.LoginUser;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @PostMapping
    public ResponseEntity<Void> createOrder(@LoginUser Long memberId){
        return ResponseEntity.ok().build();
    }
}
