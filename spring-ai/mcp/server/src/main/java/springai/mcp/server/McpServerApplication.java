package springai.mcp.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import springai.mcp.server.config.property.AppProperty;

@SpringBootApplication
@EnableConfigurationProperties({
        AppProperty.class
})
public class McpServerApplication {
  public static void main(String[] args) {
    SpringApplication.run(McpServerApplication.class, args);
  }
}