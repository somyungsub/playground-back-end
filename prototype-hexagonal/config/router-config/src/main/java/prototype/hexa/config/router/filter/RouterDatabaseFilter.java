package prototype.hexa.config.router.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import prototype.hexa.config.router.core.DataSourceContextHolder;
import prototype.hexa.config.router.property.RouterDatabaseConfigProperty;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@ConditionalOnBean(RouterDatabaseConfigProperty.class)
@RequiredArgsConstructor
@Slf4j
public class RouterDatabaseFilter extends OncePerRequestFilter {
  private final DataSourceContextHolder dataSourceContextHolder;
  private final RouterDatabaseConfigProperty routerDatabaseConfigProperty;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String heaverValue = request.getHeader(routerDatabaseConfigProperty.getHeaderKey());
    log.debug("header >>> W1ne-Type: {}", heaverValue);

    try {
      String keyName = routerDatabaseConfigProperty.getKeyNameByMappingValue(heaverValue);
      dataSourceContextHolder.setRoutingKey(keyName);
      filterChain.doFilter(request, response);
    } finally {
      dataSourceContextHolder.clear();
    }
  }
}
