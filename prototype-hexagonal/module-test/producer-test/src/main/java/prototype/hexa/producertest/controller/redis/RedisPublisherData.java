package prototype.hexa.producertest.controller.redis;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
class RedisPublisherData implements Serializable {
  private static final long serialVersionUID = 1L;
  private String message;
  private int age;
  private String name;
}
