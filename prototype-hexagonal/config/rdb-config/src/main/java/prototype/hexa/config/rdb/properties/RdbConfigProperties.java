package prototype.hexa.config.rdb.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@AllArgsConstructor
@Getter
@ToString
@ConstructorBinding
@ConfigurationProperties(prefix = "prototype.hexa.data-config")
public class RdbConfigProperties {
  private final String key;
  private final String value;
}
//public record DataConfigProperties(String key, String value){}

//public class DataConfigProperties {
//  private final String key;
//  private final String value;
//}
