package prototype.hexa.producertest.controller.rabbitmq;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class RabbitPublisherData {
  private String message;
  private int age;
  private String name;
}
