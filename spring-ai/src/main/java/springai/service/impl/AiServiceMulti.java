package springai.service.impl;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import springai.service.AiService;

@Service("AiServiceMulti")
public class AiServiceMulti implements AiService {

  private final ChatClient openAiClient;
  private final ChatClient ollamaClient;
  public AiServiceMulti(
          @Qualifier("openAiChatModel") ChatModel openAiChatModel,
          @Qualifier("ollamaChatModel") ChatModel ollamaChatModel
  ) {
    this.openAiClient = ChatClient.builder(openAiChatModel).build();
    this.ollamaClient = ChatClient.builder(ollamaChatModel).build();
  }

  @Override
  public String ask(String question) {
    return ollamaClient.prompt()
            .user(question)
            .call()
            .content();
  }
}
