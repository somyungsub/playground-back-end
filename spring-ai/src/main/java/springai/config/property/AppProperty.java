package springai.config.property;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
@AllArgsConstructor
@Getter
public class AppProperty {
  private final boolean pipelineInit;
  private final boolean vectorStoreInMemoryEnabled;
  private final boolean printEnabled;
  private final String documentLocation;
  private final CliProperty cli;

  public record CliProperty(
          boolean chatEnabled,
          boolean ragEnabled,
          String filterExpression
  ) { }
}


