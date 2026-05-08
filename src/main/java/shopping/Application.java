package shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("shopping.domain")          // Entity 스캔 경로
@EnableJpaRepositories("shopping.config") // Repository 스캔 경로
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
