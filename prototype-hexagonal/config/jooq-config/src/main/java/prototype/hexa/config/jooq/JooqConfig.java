package prototype.hexa.config.jooq;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class JooqConfig {
  private final DataSource dataSource;

  @Bean
  public DSLContext dslContext() {
    // 필요한 경우 JOOQ 설정을 추가
//    Settings settings = new Settings();
    return DSL.using(dataSource, SQLDialect.POSTGRES);
  }
//  @Bean
//  public DSLContext dslContext() {
//    Settings settings = new Settings()
//      .withRenderSchema(true); // 스키마를 항상 렌더링
//
//    DefaultConfiguration configuration = new DefaultConfiguration();
//    configuration.setDataSource(dataSource);
//    configuration.set(settings);
//    configuration.setSQLDialect(SQLDialect.POSTGRES);
//
//    return DSL.using(configuration);
//  }
}
