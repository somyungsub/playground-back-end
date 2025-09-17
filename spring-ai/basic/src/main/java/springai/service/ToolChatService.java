package springai.service;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import reactor.core.publisher.Flux;
import springai.service.data.EmotionEvaluation;

public interface ToolChatService {
  Flux<String> stream(Prompt prompt, String conversationId);
  ChatResponse call(Prompt prompt, String conversationId);
  EmotionEvaluation callEmotion(Prompt prompt, String conversationId);
}
