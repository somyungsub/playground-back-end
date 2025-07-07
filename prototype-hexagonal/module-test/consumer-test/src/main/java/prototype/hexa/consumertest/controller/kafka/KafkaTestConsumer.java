package prototype.hexa.consumertest.controller.kafka;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import prototype.hexa.common.annotation.EventConsumer;
import prototype.hexa.producertest.controller.kafka.KafkaTestData;

import java.io.IOException;

@EventConsumer
@RequiredArgsConstructor
@Slf4j
class KafkaTestConsumer {
  private final ObjectMapper objectMapper;

  @KafkaListener(topics = "${prototype.hexa.producer-test.kafka-topic-name}", groupId = "#{producerTestConfig.producerTestProperties.kafkaGroupId}")
  public void consume(byte[] message) throws IOException {
    log.info("consume message bytes : {}", new String(message));
    KafkaTestData data = objectMapper.readValue(message, KafkaTestData.class);
    log.info("consume message: {}", data);
  }
  @KafkaListener(topics = "${prototype.hexa.producer-test.kafka-topic-name}", groupId = "#{producerTestConfig.producerTestProperties.kafkaGroupId}")
  public void consume1_2(byte[] message) throws IOException {
    log.info("consume1-2 message bytes : {}", new String(message));
    KafkaTestData data = objectMapper.readValue(message, KafkaTestData.class);
    log.info("consume1-2 message: {}", data);
  }
  @KafkaListener(topics = "${prototype.hexa.producer-test.kafka-topic-name}", groupId = "#{producerTestConfig.producerTestProperties.kafkaGroupId2}")
  public void consume2(byte[] message) throws IOException {
    log.info("consume2 message bytes : {}", new String(message));
    KafkaTestData data = objectMapper.readValue(message, KafkaTestData.class);
    log.info("consume2 message: {}", data);
  }
}
