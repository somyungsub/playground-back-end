package springai.mcp.client.service;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import reactor.core.publisher.Flux;

public interface McpChatService {
  Flux<String> stream(Prompt prompt, String conversationId);
  ChatResponse call(Prompt prompt, String conversationId);
}
