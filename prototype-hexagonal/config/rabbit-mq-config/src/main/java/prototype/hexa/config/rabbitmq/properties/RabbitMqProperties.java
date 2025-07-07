package prototype.hexa.config.rabbitmq.properties;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@AllArgsConstructor
@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "prototype.hexa.rabbit-mq")
public class RabbitMqProperties {
  private final String sampleQueueName;
  private final String spreadQueueName;
  private final String testQueueName;
  private final String exchangeName;
}
