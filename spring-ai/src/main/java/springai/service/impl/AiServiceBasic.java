package springai.service.impl;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import springai.service.AiService;

@Service("AiServiceBasic")
public class AiServiceBasic implements AiService {
  private final ChatClient chatClient;

  public AiServiceBasic(
          @Qualifier("openAiChatModel") ChatModel openAiChatModel
  ) {
    this.chatClient = ChatClient.builder(openAiChatModel).build();
  }

  @Override
  public String ask(String question) {
    return chatClient.prompt()
            .user(question)
            .call()
            .content();
  }

}
