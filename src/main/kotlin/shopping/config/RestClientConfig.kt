package shopping.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class RestClientConfig {

    @Bean
    fun purgomalumRestClient(): RestClient {
        return RestClient.builder()
            .baseUrl("http://www.purgomalum.com/service")
            .build()
    }
}