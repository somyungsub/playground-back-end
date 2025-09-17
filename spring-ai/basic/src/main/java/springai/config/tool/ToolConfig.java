package springai.config.tool;

import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.tool.execution.DefaultToolExecutionExceptionProcessor;
import org.springframework.ai.tool.execution.ToolExecutionExceptionProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolConfig {

  @Bean
  ToolCallingManager toolCallingManager() {
    return ToolCallingManager.builder().build();
  }

//  @Bean
//  ToolExecutionExceptionProcessor toolExecutionExceptionProcessor() {
//    /*
//      alwaysThrow
//        - true: 예외를 상위(chatClient 사용 서비스)로 전달
//        - false: 기본값. 예외를 AI 모델에 메시지로 전달
//     */
//    return DefaultToolExecutionExceptionProcessor.builder()
//            .alwaysThrow(false)
//            .build();
//  }
}
