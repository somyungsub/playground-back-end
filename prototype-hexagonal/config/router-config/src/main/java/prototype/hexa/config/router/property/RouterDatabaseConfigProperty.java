package prototype.hexa.config.router.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Validated
@ConditionalOnProperty(prefix = "wne.his.routing", name = "enabled", havingValue = "true")
@ConfigurationProperties(prefix = "wne.his.routing")
public class RouterDatabaseConfigProperty {
  private boolean enabled; // 사용 여부
  @NotNull(message = "headerKey는 필수값 입니다.")
  private String headerKey; // 모든 DataSource에 공통으로 적용할 Header Key
  @NotNull(message = "defaultKeyName는 필수값 입니다.")
  private String defaultKeyName; // 디폴트로 사용할 keyName
  @NotNull(message = "defaultSchema는 필수값 입니다.")
  private String jpaDefaultSchema; // 디폴트로 사용할 스키마
  @NotEmpty(message = "datasources는 필수값 입니다.")
  private List<RoutingDataSourceProperty> datasources = new ArrayList<>();

  public String getKeyNameByMappingValue(String heaverValue) {
    if (CollectionUtils.isEmpty(datasources) || StringUtils.isEmpty(heaverValue)) {
      return getDefaultKeyName();
    }

    return this.datasources
            .stream()
            .filter(datasourceProp -> datasourceProp.getHeaderMappingValues().contains(heaverValue))
            .findFirst()
            .map(RoutingDataSourceProperty::getKeyName)
            .map(String::toUpperCase)
            .orElseThrow(() -> new RuntimeException("없음"));
  }

  public String getDefaultKeyName() {
    return this.defaultKeyName.toUpperCase();
  }

  public String replaceAllSqlSchema(String routerKey, String sql) {
    String schema = getSchemaByKeyName(routerKey);
    if (isNotEqualsDefaultSchema(schema)) {
      return sql.replaceAll(jpaDefaultSchema + ".", schema + ".");
    }
    return sql;
  }

  private boolean isNotEqualsDefaultSchema(String schema) {
    return !StringUtils.equals(jpaDefaultSchema, schema);
  }

  private String getSchemaByKeyName(String keyName) {
    return this.datasources.stream()
            .filter(datasourceProp -> StringUtils.equals(datasourceProp.getKeyName(), keyName))
            .findFirst()
            .map(RoutingDataSourceProperty::getSchema)
            .orElse(jpaDefaultSchema);
  }

  @Getter
  @ToString
  @Setter
  public static class RoutingDataSourceProperty {
    @NotNull(message = "keyName는 필수값 입니다.")
    private String keyName; // ex: DESIGNER, RUNTIME
    @NotEmpty(message = "headerMappingValues 필수값 입니다.")
    private List<String> headerMappingValues; // 헤더 값 ex: DESIGNER, test1
    @NotNull(message = "url는 필수값 입니다.")
    private String url;
    @NotNull(message = "username는 필수값 입니다.")
    private String username;
    private String password;
    @NotNull(message = "driverClassName는 필수값 입니다.")
    private String driverClassName;
    @NotNull(message = "schema는 필수값 입니다.")
    private String schema;
    private String poolName;
    private Integer maximumPoolSize;
    private Long connectionTimeout;
    private String jndiName;

    public String getKeyName() {
      return keyName.toUpperCase();
    }
  }
}