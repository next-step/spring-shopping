package shopping.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

// RestClientConfig.kt
@Configuration
class RestClientConfig {
    @Bean
    fun restClient(): RestClient = RestClient.create()
}