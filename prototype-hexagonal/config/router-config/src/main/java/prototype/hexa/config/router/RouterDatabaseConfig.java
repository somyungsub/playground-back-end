package prototype.hexa.config.router;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import prototype.hexa.config.router.core.DataSourceContextHolder;
import prototype.hexa.config.router.datasource.RoutingDataSource;
import prototype.hexa.config.router.property.RouterDatabaseConfigProperty;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnProperty(prefix = "wne.his.routing", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(RouterDatabaseConfigProperty.class)
@RequiredArgsConstructor
@Slf4j
public class RouterDatabaseConfig {
  private final DataSourceContextHolder dataSourceContextHolder;
  private final RouterDatabaseConfigProperty routerDatabaseConfigProperty;


  @PostConstruct
  public void init() {
    log.info("RouterDatabaseConfig loaded");
  }

  @Bean
  @Primary
  public DataSource lazyDataSource(@Qualifier("routingDataSource") DataSource dataSource) {
    return new LazyConnectionDataSourceProxy(dataSource);
  }

  @Bean
  RoutingDataSource routingDataSource(RouterDatabaseConfigProperty props) {
    RoutingDataSource routingDataSource = new RoutingDataSource(dataSourceContextHolder, routerDatabaseConfigProperty);
    Map<Object, Object> targetDataSourceMap = targetDataSourceMap(props);
    routingDataSource.setTargetDataSources(targetDataSourceMap);

    // 기본은 DefaultKeyName 으로 설정하거나 fallback 지정
    routingDataSource.setDefaultTargetDataSource(targetDataSourceMap.get(routerDatabaseConfigProperty.getDefaultKeyName()));
    return routingDataSource;
  }

  @Bean
  public JdbcTemplate jdbcTemplate(@Qualifier("routingDataSource") DataSource routingDataSource) {
    return new JdbcTemplate(routingDataSource);
  }

  private Map<Object, Object> targetDataSourceMap(RouterDatabaseConfigProperty props) {
    Map<Object, Object> targetDataSources = new HashMap<>();

    for (RouterDatabaseConfigProperty.RoutingDataSourceProperty property : props.getDatasources()) {
      DataSource dataSource = createHikariDataSource(property);
      targetDataSources.put(property.getKeyName(), dataSource);
    }
    return targetDataSources;
  }

  private DataSource createHikariDataSource(RouterDatabaseConfigProperty.RoutingDataSourceProperty property) {
    if (StringUtils.isNotEmpty(property.getJndiName())) {
      return new JndiDataSourceLookup().getDataSource(property.getJndiName());
    }

    HikariConfig config = new HikariConfig();
    config.setJdbcUrl(property.getUrl());
    config.setUsername(property.getUsername());
    config.setPassword(property.getPassword());
    config.setDriverClassName(property.getDriverClassName());
    config.setSchema(StringUtils.defaultString(property.getSchema()));
    config.setPoolName(StringUtils.defaultString(property.getPoolName(), property.getKeyName().concat("-pool")));
    config.setMaximumPoolSize(property.getMaximumPoolSize());
    config.setConnectionTimeout(property.getConnectionTimeout());

    return new HikariDataSource(config);
  }
}

