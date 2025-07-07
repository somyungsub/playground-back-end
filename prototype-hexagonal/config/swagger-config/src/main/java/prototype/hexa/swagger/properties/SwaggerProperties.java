package prototype.hexa.swagger.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@AllArgsConstructor
@ConstructorBinding
@Getter
@ConfigurationProperties(prefix = "prototype.hexa.swagger")
public class SwaggerProperties{
  private final String title;
  private final String version;
  private final String description;
}

