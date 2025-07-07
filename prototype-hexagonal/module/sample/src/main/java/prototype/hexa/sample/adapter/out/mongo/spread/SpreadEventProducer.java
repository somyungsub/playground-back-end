package prototype.hexa.sample.adapter.out.mongo.spread;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import prototype.hexa.common.annotation.EventProducer;
import prototype.hexa.sample.application.port.out.spread.SpreadEventOutPort;
import prototype.hexa.sample.config.properties.SampleProperties;
import prototype.hexa.sample.domain.spread.Spread;
import prototype.hexa.sample.domain.spread.event.SpreadEvent;

@EventProducer
@RequiredArgsConstructor
@Slf4j
class SpreadEventProducer implements SpreadEventOutPort {
  private final KafkaTemplate<String, SpreadEvent> kafkaTemplate;
  private final SampleProperties sampleProperties;

  @Override
  public void send(String topicName, Spread spread) {
    SpreadEvent spreadEvent = SpreadEvent.builder()
            .name(spread.getName())
            .topicName(sampleProperties.topicName())
            .eventName("saveTest-" + spread.getName())
            .spread(spread)
            .build();
    kafkaTemplate.send(topicName, spreadEvent);
    log.info("success currentThread name {}", Thread.currentThread().getName());
    log.info("success publish {}", spreadEvent);
  }
}
