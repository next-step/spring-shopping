package shopping.config

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import java.time.Duration

@Configuration
class RestTemplateConfig {
    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate =
        builder
            .connectTimeout(Duration.ofSeconds(5)) // Connection timeout
            .readTimeout(Duration.ofSeconds(5)) // Read timeout
            .build()
}
