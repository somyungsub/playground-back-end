package prototype.hexa.config.router.core;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import prototype.hexa.config.router.property.RouterDatabaseConfigProperty;

@Component
@ConditionalOnBean(RouterDatabaseConfigProperty.class)
public class DataSourceContextHolder {
  private static final ThreadLocal<String> context = new ThreadLocal<>();
  /**
   * 라우팅 키 설정
   * <p>
   * 기존에 설정된 키가 있다면 제거하고 새로운 키 세팅합니다.
   *
   * @param lookupKey 설정할 LookupKey 객체.
   */
  public void setRoutingKey(String lookupKey) {
    clear();
    context.set(lookupKey);
  }

  /**
   * 현재 스레드의 라우팅 키 반환
   *
   * @return 현재 스레드의 LookupKey
   */
  public String getRoutingKey() {
    return context.get();
  }

  /**
   * 현재 스레드의 라우팅 키 제거
   */
  public void clear() {
    context.remove();
  }
}
