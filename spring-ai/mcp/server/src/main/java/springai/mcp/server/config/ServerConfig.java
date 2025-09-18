package springai.mcp.server.config;

import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springai.mcp.server.service.tool.Tools;

@Configuration
public class ServerConfig {

  @Bean
  SimpleLoggerAdvisor simpleLoggerAdvisor() {
    return new SimpleLoggerAdvisor();
  }

  @Bean
  ToolCallbackProvider toolCallbackProvider(Tools tools) {
    return MethodToolCallbackProvider.builder().toolObjects(tools).build();
  }
  
}
