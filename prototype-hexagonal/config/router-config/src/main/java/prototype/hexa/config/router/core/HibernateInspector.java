package prototype.hexa.config.router.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import prototype.hexa.config.router.property.RouterDatabaseConfigProperty;

import javax.annotation.PostConstruct;

@Slf4j
@Component
@ConditionalOnBean(RouterDatabaseConfigProperty.class)
@RequiredArgsConstructor
public class HibernateInspector implements StatementInspector {
  private final RouterDatabaseConfigProperty property;
  private final DataSourceContextHolder dataSourceContextHolder;

  @PostConstruct
  public void init() {
    log.info("Hibernate Inspector loaded");
  }

  @Override
  public String inspect(String sql) {
    String routerKey = StringUtils.defaultString(dataSourceContextHolder.getRoutingKey(), property.getDefaultKeyName());
    return property.replaceAllSqlSchema(routerKey, sql);
  }
}
