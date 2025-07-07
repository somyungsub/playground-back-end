package prototype.hexa.producertest.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@AllArgsConstructor
@ConstructorBinding
@ToString
@Getter
@ConfigurationProperties(prefix = "prototype.hexa.producer-test")
public class ProducerTestProperties {
  private final String queueName;
  private final String queueTestName;
  private final String kafkaTopicName;
  private final String kafkaGroupId;
  private final String kafkaGroupId2;
}
