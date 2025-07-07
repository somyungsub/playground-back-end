package prototype.hexa.config.core.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@AllArgsConstructor
@Getter
@ToString
@ConstructorBinding
@ConfigurationProperties(prefix = "prototype.hexa.core")
public class CoreProperties {
  private final int version;
  private final String arch;
  private final int corePoolSize;
  private final int maxPoolSize;
  private final int queueCapacity;
  private final String threadNamePrefix;
}
