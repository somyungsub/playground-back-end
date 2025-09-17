package springai.service;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import reactor.core.publisher.Flux;
import springai.service.data.EmotionEvaluation;

public interface RAGService {
  Flux<String> stream(Prompt prompt, String conversationId, @Nullable String filterExpression);
  ChatResponse call(Prompt prompt, String conversationId, @Nullable String filterExpression);
  EmotionEvaluation callEmotion(Prompt prompt, String conversationId, @Nullable String filterExpression);
}
