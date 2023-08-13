package shopping.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import shopping.dto.request.OrderExchangeRequest;
import shopping.exception.ShoppingException;
import shopping.exchange.CurrencyType;

@DisplayName(("OrderExchangeService 통합 테스트"))
@SpringBootTest
class OrderExchangeServiceIntegrationTest {

    @Autowired
    OrderExchangeService orderExchangeService;

    @Test
    @DisplayName("본인의 주문 정보가 아닌 것은 확인할 수 없다.")
    void getOrderFailWithNotOwn() {
        /* given */
        final OrderExchangeRequest request =
            new OrderExchangeRequest(777L, 1L, CurrencyType.USD, CurrencyType.KRW);

        /* when & then */
        assertThatThrownBy(
            () -> orderExchangeService.findOrderDetailWithCurrencyExchangeRate(request))
            .isExactlyInstanceOf(ShoppingException.class)
            .hasMessage("주문 정보를 찾을 수 없습니다.");
    }

}
