package springai.config.rag;

import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springai.service.RAGService;

import java.util.Optional;
import java.util.Scanner;

@Configuration
public class RagChatConfig {

  @ConditionalOnProperty(prefix = "app.cli.rag", name = "enabled", havingValue = "true")
  @Bean
  CommandLineRunner commandLineRunner(
          @Value("${spring.application.name}") String applicationName,
          RAGService chatService,
          @Value("${app.cli.filter-expression:''}") String filterExpression
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
          chatService.stream(prompt, "cli", filterExpression)
                  .doFirst(() -> System.out.print("\n Assistant: "))
                  .doOnNext(System.out::print)
                  .doOnComplete(System.out::println)
                  .blockLast();
        }
      }
    };
  }

}
