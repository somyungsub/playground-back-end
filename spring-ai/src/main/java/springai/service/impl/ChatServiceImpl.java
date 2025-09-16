package springai.service.impl;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import springai.service.ChatService;
import springai.service.data.EmotionEvaluation;


@Service
class ChatServiceImpl implements ChatService {

  private final ChatClient chatClient;
  public ChatServiceImpl(
          @Qualifier("openAiChatModel") ChatModel chatModel,
//          @Qualifier("ollamaChatModel") ChatModel chatModel,
          Advisor[] advisors
  ) {
    this.chatClient = ChatClient
            .builder(chatModel)
            .defaultAdvisors(advisors)
            .build();
  }


  @Override
  public Flux<String> stream(Prompt prompt, String conversationId) {
    return buildChatClientRequestSpec(prompt, conversationId)
            .stream()
            .content();

  }

  @Override
  public ChatResponse call(Prompt prompt, String conversationId) {
    return buildChatClientRequestSpec(prompt,conversationId)
            .call()
            .chatResponse();
  }

  @Override
  public EmotionEvaluation callEmotion(Prompt prompt, String conversationId) {
    return buildChatClientRequestSpec(prompt, conversationId)
            .call()
            .entity(EmotionEvaluation.class);
  }

  private ChatClient.ChatClientRequestSpec buildChatClientRequestSpec(Prompt prompt, String conversationId) {
    return chatClient
            .prompt(prompt)
            .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId));
  }

}
