package springai.service.impl;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import springai.service.AiService;

@Service("AiServiceMulti")
class AiServiceMulti implements AiService {

  private final ChatClient chatClient;

  public AiServiceMulti(
          @Qualifier("openAiChatModel") ChatModel chatModel
//          @Qualifier("ollamaChatModel") ChatModel chatModel
  ) {
//    this.openAiClient = ChatClient.builder(openAiChatModel).build();
    this.chatClient = ChatClient.builder(chatModel).build();
  }

  @Override
  public String ask(String question) {
    return chatClient.prompt()
            .user(question)
            .call()
            .content();
  }

  @Override
  public ChatResponse askResponse(String question) {
    return chatClient.prompt()
            .user(question)
            .call()
            .chatResponse();
  }

  @Override
  public Flux<String> askStream(String question) {
    return chatClient.prompt()
            .user(question)
            .stream()
            .content();
  }
}
