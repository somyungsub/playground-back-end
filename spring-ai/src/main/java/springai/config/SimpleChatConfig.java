package springai.config;

import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springai.service.ChatService;

import java.util.Scanner;

@Configuration
public class SimpleChatConfig {

  @Bean
  SimpleLoggerAdvisor simpleLoggerAdvisor() {
    return new SimpleLoggerAdvisor();
  }

  @Bean
  ChatMemory chatMemory() {
    return MessageWindowChatMemory
            .builder()
            .maxMessages(10)
//            .chatMemoryRepository()
            .build();
  }

  @Bean
  MessageChatMemoryAdvisor messageChatMemoryAdvisor() {
    return MessageChatMemoryAdvisor
            .builder(chatMemory())
            .build();
  }

  @ConditionalOnProperty(prefix = "spring.application", name = "cli", havingValue = "true")
  @Bean
  CommandLineRunner commandLineRunner(
          @Value("${spring.application.name}") String applicationName,
          ChatService chatService
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
          chatService.stream(prompt, "cli")
                  .doFirst(() -> System.out.print("\n Assistant: "))
                  .doOnNext(System.out::print)
                  .doOnComplete(System.out::println)
                  .blockLast();
        }
      }
    };
  }
}
