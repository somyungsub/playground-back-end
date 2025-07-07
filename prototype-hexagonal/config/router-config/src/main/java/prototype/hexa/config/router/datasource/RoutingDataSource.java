package prototype.hexa.config.router.datasource;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import prototype.hexa.config.router.core.DataSourceContextHolder;
import prototype.hexa.config.router.property.RouterDatabaseConfigProperty;

@RequiredArgsConstructor
public class RoutingDataSource extends AbstractRoutingDataSource {
  private final DataSourceContextHolder dataSourceContextHolder;
  private final RouterDatabaseConfigProperty routerDatabaseConfigProperty;

  @Override
  protected Object determineCurrentLookupKey() {
    if(StringUtils.isEmpty(dataSourceContextHolder.getRoutingKey())) {
      return routerDatabaseConfigProperty.getDefaultKeyName();
    }

    return dataSourceContextHolder.getRoutingKey().toUpperCase();
  }
}
