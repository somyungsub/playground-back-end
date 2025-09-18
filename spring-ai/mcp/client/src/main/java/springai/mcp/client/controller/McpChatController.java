package springai.mcp.client.controller;

import io.swagger.v3.oas.annotations.media.Schema;
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
import springai.mcp.client.service.McpChatService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@RestController
@RequestMapping("mcp-client")
@RequiredArgsConstructor
public class McpChatController {
  private final McpChatService mcpChatService;

  private record PromptBody(
          @NotEmpty @Schema(description = "대화 식별자") String conversationId,
          @NotEmpty @Schema(description = "사용자 입력 프롬프트") String userPrompt,
          @Nullable @Schema(description = "시스템 프롬프트(선택)") String systemPrompt,
          @Nullable @Schema(description = "채팅 옵션(선택)") DefaultChatOptions options
  ) {}

  @PostMapping("/call")
  ChatResponse call(@RequestBody @Valid PromptBody prompt) {
    return mcpChatService.call(createPrompt(prompt), prompt.conversationId());
  }

  private static Prompt createPrompt(PromptBody prompt) {
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

  @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  Flux<String> stream(@RequestBody @Valid PromptBody prompt) {
    return mcpChatService.stream(createPrompt(prompt), prompt.conversationId());
  }

}
