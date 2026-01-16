package shopping.client.purgomalum.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class PurgomalumRestClientConfig {
    @Bean
    fun restClient(
        @Value("\${client.purgomalum.base-url}") baseUrl: String,
    ): RestClient {
        return RestClient.builder()
            .baseUrl(baseUrl)
            .build()
    }
}
