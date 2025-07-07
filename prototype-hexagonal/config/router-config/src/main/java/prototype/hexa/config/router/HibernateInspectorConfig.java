package prototype.hexa.config.router;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import prototype.hexa.config.router.core.HibernateInspector;

import javax.annotation.PostConstruct;

@Configuration
@ConditionalOnProperty(prefix = "wne.his.routing", name = "enabled", havingValue = "true")
@RequiredArgsConstructor
@Slf4j
public class HibernateInspectorConfig {

  @PostConstruct
  public void init() {
    log.info("Hibernate Inspector configuration loaded.");
  }

  @Bean
  public HibernatePropertiesCustomizer hibernateInspectorCustomizer(HibernateInspector inspector) {
    return props -> props.put("hibernate.session_factory.statement_inspector", inspector);
  }
}
