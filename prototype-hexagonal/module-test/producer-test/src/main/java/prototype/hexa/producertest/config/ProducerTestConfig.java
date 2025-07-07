package prototype.hexa.producertest.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import prototype.hexa.config.rabbitmq.RabbitMqManager;
import prototype.hexa.producertest.config.properties.ProducerTestProperties;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
@Slf4j
@Getter
@EnableConfigurationProperties(ProducerTestProperties.class)
public class ProducerTestConfig {
  private final ProducerTestProperties producerTestProperties;
  private final RabbitMqManager rabbitMqManager;

  @PostConstruct
  public void init() {
    log.info("ProducerTestProperties.queueName: {}", producerTestProperties);
    rabbitMqManager.bindingQueue(producerTestProperties.getQueueTestName());
  }

//  @EventListener(ApplicationReadyEvent.class)
//  public void logProducerTestProperties() {
//    log.info("ProducerTestProperties.queueName2: {}", producerTestProperties);
//  }
}
