package springai.service;

import org.springframework.ai.chat.model.ChatResponse;
import reactor.core.publisher.Flux;

public interface AiService {
  String ask(String question);

  ChatResponse askResponse(String question);

  Flux<String> askStream(String question);
}
