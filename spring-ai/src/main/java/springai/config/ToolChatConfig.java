package springai.config;

import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springai.service.ChatService;
import springai.service.ToolChatService;

import java.util.Scanner;

@Configuration
public class ToolChatConfig {
  @ConditionalOnProperty(prefix = "app.cli", name = "tool-enabled", havingValue = "true")
  @Bean
  CommandLineRunner commandLineRunner(
          @Value("${spring.application.name}") String applicationName,
          ToolChatService toolChatService
  ) {

    return args -> {
      LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
      loggerContext.getLogger("ROOT").detachAppender("CONSOLE");
      System.out.println("\n " + applicationName + " CLI Chat Bot");

      try (Scanner scanner = new Scanner(System.in)) {
        while (true) {
          System.out.print("\n User >> ");
          String message = scanner.nextLine();
          Prompt prompt = Prompt.builder().content(message).build();
          toolChatService.stream(prompt, "cli")
                  .doFirst(() -> System.out.print("\n Assistant: "))
                  .doOnNext(System.out::print)
                  .doOnComplete(System.out::println)
                  .blockLast();
        }
      }
    };
  }
}
