package prototype.hexa.config.router;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "prototype.hexa")
@EntityScan(basePackages = "prototype.hexa")
@Configuration
public class JpaConfig {
}
