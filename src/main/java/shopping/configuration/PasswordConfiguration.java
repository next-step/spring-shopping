package shopping.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import shopping.auth.PBKDF2PasswordEncoder;
import shopping.auth.PasswordEncoder;

@Configuration
public class PasswordConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PBKDF2PasswordEncoder();
    }
}
