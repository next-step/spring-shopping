package shopping.order.infra.exchange;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import shopping.order.app.domain.Exchange;
import shopping.order.app.spi.ExchangeVendor;

@Service
@Profile("test")
public class MockExchangeVendor implements ExchangeVendor {

    private static final double DEFAULT_RATE = 1.1D;

    @Override
    public Exchange currentExchange() {
        return new Exchange(DEFAULT_RATE);
    }
}
