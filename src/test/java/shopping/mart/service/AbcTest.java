package shopping.mart.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import shopping.core.config.RestTemplateConfig;
import shopping.mart.domain.CurrencyType;
import shopping.mart.domain.ExchangeRateProvider;
import shopping.mart.infra.exchange.ExchangeRateProviderImpl;

@SpringBootTest
@ContextConfiguration(classes = {ExchangeRateProviderImpl.class, RestTemplateConfig.class})
class AbcTest {

    @Autowired
    private ExchangeRateProvider exchangeRateProvider;

    @Test
    void abc() {
        System.out.println(
            exchangeRateProvider.getCurrencyRate(CurrencyType.USD, CurrencyType.KRW));
    }
}
