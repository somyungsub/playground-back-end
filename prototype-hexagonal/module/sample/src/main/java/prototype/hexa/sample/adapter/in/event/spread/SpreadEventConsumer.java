package prototype.hexa.sample.adapter.in.event.spread;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import prototype.hexa.common.annotation.EventConsumer;
import prototype.hexa.common.exception.GlobalException;
import prototype.hexa.sample.application.port.in.sample.SampleR2dbcUseCase;
import prototype.hexa.sample.domain.spread.event.SpreadEvent;
import reactor.core.publisher.Mono;

import java.io.IOException;

@EventConsumer
@RequiredArgsConstructor
@Slf4j
class SpreadEventConsumer {
  private final ObjectMapper objectMapper;
  private final SampleR2dbcUseCase sampleR2dbcUseCase;

  @KafkaListener(topics = "${prototype.hexa.sample.event.topic-name}", groupId = "${prototype.hexa.sample.event.group-id2}")
  public void consumeEvent2(byte[] data) throws IOException {
    log.info("event consume2 currentThread name {}", Thread.currentThread().getName());
    SpreadEvent spreadEvent = objectMapper.readValue(data, SpreadEvent.class);
    sampleR2dbcUseCase.
      findByName(spreadEvent.name())
      .log()
      .switchIfEmpty(Mono.error(new GlobalException("WNE-HRS-SAMPLE-0001", spreadEvent.name())))
      .subscribe(
        sample -> log.info("Successfully processed sample : {} ", sample),
        error -> log.error("Processed error: {} ", error.getMessage())
      );
    log.info("event consume2 : {}", spreadEvent);
  }

  @KafkaListener(topics = "${prototype.hexa.sample.event.topic-name}", groupId = "${prototype.hexa.sample.event.group-id}")
  public void consumeEvent(byte[] data) throws IOException {
    log.info("event consume currentThread name {}", Thread.currentThread().getName());
    SpreadEvent spreadEvent = objectMapper.readValue(data, SpreadEvent.class);
    log.info("event consume : {}", spreadEvent);
  }

//  @KafkaListener(topics = "${prototype.hexa.sample.topicName}", groupId = "${spring.kafka.consumer.group-id}")
//  public void consumeEvent(SpreadEvent spreadEvent) {
//    log.info("event consume : {}", spreadEvent);
//  }
}
