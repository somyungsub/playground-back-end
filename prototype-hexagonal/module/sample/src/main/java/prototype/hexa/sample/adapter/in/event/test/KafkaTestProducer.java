package prototype.hexa.sample.adapter.in.event.test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import prototype.hexa.common.annotation.EventProducer;
import prototype.hexa.common.api.ApiResponse;
import prototype.hexa.sample.config.properties.SampleProperties;
import prototype.hexa.sample.domain.spread.event.SpreadEvent;

@EventProducer
@RequestMapping("/v1/kafka/events")
@RequiredArgsConstructor
class KafkaTestProducer {

  private final KafkaTemplate<String, SpreadEvent> kafkaTemplate;
  private final SampleProperties sampleProperties;

  @GetMapping
  public ResponseEntity<ApiResponse<SpreadEvent>> sendTest(@RequestParam String message) {
    SpreadEvent spreadEvent = SpreadEvent.builder()
      .topicName(sampleProperties.topicName())
      .eventName("test-kafka")
      .spread(null)
      .name(message)
      .build();

    kafkaTemplate.send(sampleProperties.topicName(), spreadEvent);
    return ResponseEntity.ok(ApiResponse.ok(spreadEvent));
  }
}
