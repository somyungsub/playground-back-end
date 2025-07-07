package prototype.hexa.producertest.controller.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import prototype.hexa.common.annotation.EventProducer;
import prototype.hexa.producertest.config.properties.ProducerTestProperties;

import java.util.List;

@EventProducer
@RequestMapping("/v1/producer-test/kafka")
@RequiredArgsConstructor
@Slf4j
class KafkaTestProducer {
  private final KafkaTemplate<String,KafkaTestData> kafkaTemplate;
  private final ProducerTestProperties producerTestProperties;

  @GetMapping("/producer")
  public void producer(@RequestParam String message) {
    KafkaTestData.InnerData test = KafkaTestData.InnerData.builder().index(0).value("test").build();
    KafkaTestData.InnerData test2 = KafkaTestData.InnerData.builder().index(1).value("test2").build();
    KafkaTestData.InnerData test3 = KafkaTestData.InnerData.builder().index(2).value("test3").build();
    KafkaTestData data = KafkaTestData.builder()
      .age(30)
      .name("hello - kafka")
      .message(message)
      .innerDatas(List.of(test, test2, test3))
      .build();
    kafkaTemplate.send(producerTestProperties.getKafkaTopicName(), data);
    log.info("producer sent message: {}", data);
  }
}
