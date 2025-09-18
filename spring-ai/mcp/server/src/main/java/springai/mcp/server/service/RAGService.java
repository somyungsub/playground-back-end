package springai.mcp.server.service;

import jakarta.annotation.Nullable;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import reactor.core.publisher.Flux;

public interface RAGService {
  Flux<String> stream(Prompt prompt, String conversationId, @Nullable String filterExpression);
  ChatResponse call(Prompt prompt, String conversationId, @Nullable String filterExpression);
}
