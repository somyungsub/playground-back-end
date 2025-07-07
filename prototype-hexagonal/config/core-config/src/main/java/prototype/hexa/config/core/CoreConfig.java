package prototype.hexa.config.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import prototype.hexa.config.core.properties.CoreProperties;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(CoreProperties.class)
@Slf4j
@EnableAsync
public class CoreConfig {
  private final CoreProperties coreProperties;

  @PostConstruct
  public void postConstruct() {
    log.info("core-config properties : {} ", coreProperties);
    log.info("core-config availableProcessors : {} ", Runtime.getRuntime().availableProcessors());
  }
  //TODO 정리 ThreadConfig
  @Bean
  public Executor customTaskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(coreProperties.getCorePoolSize());              // 기본적으로 생성되는 스레드 수
    executor.setMaxPoolSize(coreProperties.getMaxPoolSize());              // 최대 생성할 수 있는 스레드 수
    executor.setQueueCapacity(coreProperties.getQueueCapacity());           // 대기 중인 작업 수용을 위한 큐의 크기
    executor.setThreadNamePrefix(coreProperties.getThreadNamePrefix());   // 생성되는 스레드의 이름 접두사
    executor.setKeepAliveSeconds(60);
    executor.initialize();                                                // 초기화
    return executor;
  }
}
