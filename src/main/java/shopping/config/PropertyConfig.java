package shopping.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import shopping.infra.ExchangeRateApiProperties;

@Configuration
@EnableConfigurationProperties(ExchangeRateApiProperties.class)
public class PropertyConfig {

}
