package shopping.mart.persist;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;

@DataJpaTest
@EntityScan(basePackages = "shopping")
@DirtiesContext(methodMode = MethodMode.BEFORE_METHOD)
@EnableJpaRepositories
abstract class JpaTest {
}
