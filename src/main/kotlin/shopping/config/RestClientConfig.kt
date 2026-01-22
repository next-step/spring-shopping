package shopping.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.client.RestClient
import java.time.Duration

@Configuration
class RestClientConfig {
    @Bean
    fun restClient(): RestClient {
        val requestFactory = SimpleClientHttpRequestFactory()
        requestFactory.setReadTimeout(Duration.ofSeconds(5))
        requestFactory.setConnectTimeout(Duration.ofSeconds(5))

        return RestClient.builder()
            .baseUrl("http://www.purgomalum.com")
            .requestFactory(requestFactory)
            .build()
    }
}
