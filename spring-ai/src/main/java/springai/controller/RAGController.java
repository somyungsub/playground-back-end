package springai.controller;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.DefaultChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import springai.service.RAGService;
import springai.service.data.EmotionEvaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@RestController
@RequestMapping("/rag")
@RequiredArgsConstructor
public class RAGController {

  private final RAGService ragService;

  private record PromptBody(
          @NotEmpty String conversationId,
          @NotEmpty String userPrompt,
          @NotEmpty String systemPrompt,
          DefaultChatOptions options,
          @Nullable String filterExpression
  ) {}

  @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  Flux<String> stream(@RequestBody @Valid RAGController.PromptBody prompt) {
    return ragService.stream(createPrompt(prompt), prompt.conversationId(), prompt.filterExpression());
  }


  @PostMapping("/call")
  ChatResponse call(@RequestBody @Valid RAGController.PromptBody prompt) {
    return ragService.call(createPrompt(prompt), prompt.conversationId(), prompt.filterExpression());
  }

  @PostMapping(value = "/emotion", produces = MediaType.APPLICATION_JSON_VALUE)
  EmotionEvaluation callEmotion(@RequestBody @Valid RAGController.PromptBody prompt) {
    return ragService.callEmotion(createPrompt(prompt), prompt.conversationId(), prompt.filterExpression());
  }

  private static Prompt createPrompt(RAGController.PromptBody prompt) {
    List<Message> messages = new ArrayList<>();

    messages.add( UserMessage.builder().text(prompt.userPrompt()).build());

    Optional.ofNullable(prompt.systemPrompt())
            .filter(Predicate.not(String::isBlank))
            .map(SystemMessage.builder()::text)
            .map(SystemMessage.Builder::build)
            .ifPresent(messages::add);

    Prompt.Builder builder = Prompt.builder().messages(messages);

    Optional.ofNullable(prompt.options()).ifPresent(builder::chatOptions);

    return builder.build();
  }

}
