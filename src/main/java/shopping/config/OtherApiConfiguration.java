package shopping.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import shopping.order.service.ExchangeRateService;
import shopping.other.exchange.currencylayer.CurrencylayerExchangeRateServiceImpl;

@Configuration
public class OtherApiConfiguration {

    public static final int DEFAULT_MILLISECONDS = 2000;
    @Value("${currencylayer.apikey}")
    private String currencylayerApikey;
    @Value("${currencylayer.url}")
    private String currencylayerUrl;

    @Bean
    @Profile("!test")
    public ExchangeRateService exchangeRateService() {
        return new CurrencylayerExchangeRateServiceImpl(webClient(), new ObjectMapper(), currencylayerApikey, currencylayerUrl);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector(httpClient()))
            .build();
    }

    @Bean
    public HttpClient httpClient() {
        return  HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, DEFAULT_MILLISECONDS)
            .responseTimeout(Duration.ofMillis(DEFAULT_MILLISECONDS))
            .doOnConnected(conn ->
                conn.addHandlerLast(new ReadTimeoutHandler(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS))
                    .addHandlerLast(new WriteTimeoutHandler(DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS)));
    }
}
